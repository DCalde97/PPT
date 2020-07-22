package ppt_arguello_calderon;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Clase cuya responsabilidad es mostrar una interfaz para el cliente en la que
 * el jugador pueda ver el panel de mensajes y tenga la opción de retar a jugadores
 * @author ASUS
 * @version 1.0
 * @since 01/07/2020
 */
public class Cliente
    extends JFrame
    implements Runnable, KeyListener {
    
    private JTextField adversario;    
    private JPanel panel;
    private String nick;
    private Socket cliente;
    private DataInputStream flujoLectura;
    private DataOutputStream flujoEscritura;
    private JTextArea txtChatGlobal;
    private static int cant;
    
    private Cliente(Socket cliente,String nick){
        this.nick=nick;
        initComponents();
        //initCommunication(cliente);
    }

    public Socket getCliente() {
        return cliente;
    }
    
    /**
    * Método constructor de un nuevo cliente
    * @param cliente Socket del cliente
    * @param nick String con el nick del cliente
    * @return un objeto de tipo Cliente
    */
    public static Cliente nCliente(Socket cliente,String nick) {
        Cliente C=null;
        if(cant==0){
            C = new Cliente(cliente,nick);
            Receptor.añadirCliente(C);
            cant++;
        }
        return C;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public DataInputStream getFlujoLectura() {
        return flujoLectura;
    }

    public void setFlujoLectura(DataInputStream flujoLectura) {
        this.flujoLectura = flujoLectura;
    }

    public DataOutputStream getFlujoEscritura() {
        return flujoEscritura;
    }

    public void setFlujoEscritura(DataOutputStream flujoEscritura) {
        this.flujoEscritura = flujoEscritura;
    }
    
    

    private void initComponents() {
        this.setTitle("Piedra papel tijera");
        this.setSize(700, 500);
        adversario = new JTextField(20);
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        txtChatGlobal  = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(txtChatGlobal);
        
        adversario.addKeyListener(this);
        
        txtChatGlobal.setEditable(false);
        panel.add(txtChatGlobal);
        panel.add(adversario);
        
        this.getContentPane().add(panel);

        this.pack();
    
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    
    
    /**
    * Método que actualiza el contenido de los paneles de texto
    * @param mensaje String del mensaje a mostrar en el panel de texto
    */ 
    public void mostrarPorPanel(String mensaje){
        this.txtChatGlobal.append(mensaje);
        this.txtChatGlobal.append("\n");
    }
    
    /**
    * Método que muestra un mensaje al jugador en un panel.
    * @param adversario String del jugador al que se enviará el mensaje en el panel
    */ 
    public void aceptado(String adversario) {
        JOptionPane.showMessageDialog(this, adversario+" ha aceptado el reto", "Titulo", JOptionPane.INFORMATION_MESSAGE, null);
    }
    
    public void denegado(String adversario) {
        JOptionPane.showMessageDialog(this, adversario+" ha denegado el reto", "Titulo", JOptionPane.INFORMATION_MESSAGE, null);
    }
    
    /**
    * Los siguientes 3 métodos muestran el mensaje correspondiente por el panel 
    * a cada jugador
    * @param id String del identificador partida
    * @param miPunt String
    * @param suPunt String 
    */ 
    public void ganador(String id,String miPunt,String suPunt) {
        JOptionPane.showMessageDialog(this, "ENHORABUENA!!! Has ganado la partida "+id+" con un total de: "+miPunt+"-"+suPunt, "Titulo", JOptionPane.INFORMATION_MESSAGE, null);
    }
    
    public void perdedor(String id,String miPunt,String suPunt) {
        JOptionPane.showMessageDialog(this, "Lo siento has perdido la partida "+id+" con un total de: "+miPunt+"-"+suPunt, "Titulo", JOptionPane.INFORMATION_MESSAGE, null);
    }
    
    public void empate(String id,String miPunt,String suPunt) {
        JOptionPane.showMessageDialog(this, "Has empatado la partida "+id+" con un total de: "+miPunt+"-"+suPunt, "Titulo", JOptionPane.INFORMATION_MESSAGE, null);
    }

    @Override
    /**
    * Método run que genera un hilo por cliente
    */
    public void run()
    {
        DataInputStream in = null;
        try {
            
            in = new DataInputStream( cliente.getInputStream());
            String nicks = in.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
      //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
      //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    /**
    * Método keyreleased envia una proposición de reto al adversario cuando se suelta
    * la tecla enter
    */ 
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == 10){
            String adv=this.adversario.getText();
            
            this.adversario.setText("");
            Receptor.mensaje(this.nick, adv,"PROPUESTO");
            
        }
    }
    /**
    * Método que compara un nick dado con el actual.
    * @param nick1 String el nick a comparar con el actual
    * @return una variable de tipo boolean que indica si el el nick es igual o no
    */    
    public boolean equals(String nick1){
        boolean igual;
        if (this.nick.equals(nick1)){
            igual=true;
        } else {
            igual=false;
        }
        return igual;
    }
}
