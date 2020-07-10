//instanciar una clase cliente desde el main de PPT_... por ejemplo.
package ppt_arguello_calderon;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author ASUS
 */
public class Cliente
    extends JFrame
    implements ActionListener, Runnable, KeyListener {
    
    private JButton piedra;
    private JButton papel;
    private JButton tijera;
    private JButton jugar;    
    private JPanel panel;
    private JTextArea txtChatGlobal;
  
    private Socket cliente;
    private InputStream flujoLectura;
    private OutputStream flujoEscritura;
  
  
    public Cliente(){
        initComponents();
        //initCommunication();
    }

    private void initComponents() {
        this.setTitle("Piedra papel tijera");
        this.setSize(700, 500);
        jugar = new JButton("Jugar");
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        jugar.addActionListener(this);
        
        panel.add(jugar);
        
        this.getContentPane().add(panel);

        this.pack();
    
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    /*
    private void initCommunication() {
        try{
            cliente = new Socket("localhost",9998);  
            this.flujoLectura = cliente.getInputStream();
            this.flujoEscritura = cliente.getOutputStream();
            Thread hiloLectura = new Thread(this);
            hiloLectura.start();
        }
        catch(Exception ex) {
        }
    }
    */

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == jugar){
           //JOptionPane.showMessageDialog(this, "Has pulsado hola Mundo!!", "Titulo", JOptionPane.INFORMATION_MESSAGE, null);
        }
    }

    @Override
    public void run()
    {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      //cuando se inicia el cliente se inicia el metod run que se encargara de 
      //la comunicacion para retar y recivir retos es decir siempre tiene que 
      //estar disponible para recivir mensajes
      //si el reto mandado o el recivido son aceptados se crea una interfadpartida
      //si es cancerlado muestra un mensaje por pantalla y sigue a la espera de 
      //nuevos mensajes y mandar nuevos retos
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  
  
}
