//instanciar una clase cliente desde el main de PPT_... por ejemplo.
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
 *
 * @author ASUS
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
    
    public void mostrarPorPanel(String mensaje){
        this.txtChatGlobal.append(mensaje);
        this.txtChatGlobal.append("\n");
    }

    
//    private void initCommunication(Socket cliente) {
//        try{
//            this.flujoLectura = cliente.getDataInputStream();
//            this.flujoEscritura = cliente.getOutputStream();
//            //Thread hiloLectura = new Thread(this);
//            //hiloLectura.start();
//        }
//        catch(Exception ex) {
//        }
//    }
    
    public void aceptado(String adversario) {
        JOptionPane.showMessageDialog(this, adversario+" ha aceptado el reto", "Titulo", JOptionPane.INFORMATION_MESSAGE, null);
    }
    
    public void denegado(String adversario) {
        JOptionPane.showMessageDialog(this, adversario+" ha denegado el reto", "Titulo", JOptionPane.INFORMATION_MESSAGE, null);
    }

    @Override
    public void run()
    {
        DataInputStream in = null;
        try {
            
            in = new DataInputStream( cliente.getInputStream());
            String nicks = in.readUTF();
            //mostrar por interfad
            
            //cuando se inicia el cliente se inicia el metod run que se encargara de
            //la comunicacion para retar y recivir retos es decir siempre tiene que
            //estar disponible para recivir mensajes
            //si el reto mandado o el recivido son aceptados se crea una interfadpartida
            //si es cancerlado muestra un mensaje por pantalla y sigue a la espera de
            //nuevos mensajes y mandar nuevos retos
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
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == 10){
            String adv=this.adversario.getText();
            
            this.adversario.setText("");
            Receptor.mensaje(this.nick, adv,"PROPUESTO");
            
            //InterfazPartida I1 = InterfazPartida;
        }
    }
    
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
