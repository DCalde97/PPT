//instanciar una clase cliente desde el main de PPT_... por ejemplo
//Ahora continuaremos con el desarrollo de Cliente desde la rama InterfacesCliente
package ppt_arguello_calderon;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
    private JPanel panel;
    private JTextField txtmensaje;
    private JTextArea txtChatGlobal;
  
    private Socket cliente;
    private InputStream flujoLectura;
    private OutputStream flujoEscritura;
  
  
  public Cliente(){
     initComponents();
     initCommunication();
  }

    private void initComponents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void initCommunication() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  
  
}
