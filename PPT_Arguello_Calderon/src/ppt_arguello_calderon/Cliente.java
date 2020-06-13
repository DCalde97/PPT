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
    private JTextField nombre;
    private JTextArea txtChatGlobal;
  
    private Socket cliente;
    private InputStream flujoLectura;
    private OutputStream flujoEscritura;
  
  
  public Cliente(){
     initComponents();
     initCommunication();
  }

    private void initComponents() {
        this.setTitle("Piedra papel tijera");
        this.setSize(700, 500);
        jugar = new JButton("Jugar");
        nombre = new JTextField(20);
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        jugar.addActionListener(this);
        nombre.addKeyListener(this);
        
        panel.add(jugar);
        panel.add(nombre);
        
        this.getContentPane().add(panel);

        this.pack();
    
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void initCommunication() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void run()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
