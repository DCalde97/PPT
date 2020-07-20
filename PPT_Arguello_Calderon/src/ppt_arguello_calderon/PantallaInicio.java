/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.swing.*;

/**
 *
 * @author Danip
 */
public class PantallaInicio 
    extends JFrame
    implements ActionListener, Runnable, KeyListener {
    private JTextField nick;
    private JPanel panel;
    
    private Socket cliente;
    private InputStream flujoLectura;
    private OutputStream flujoEscritura;

    public PantallaInicio() {
        initComponents();
    }

    public JTextField getNick() {
        return nick;
    }

    public Socket getCliente() {
        return cliente;
    }

    public InputStream getFlujoLectura() {
        return flujoLectura;
    }

    public OutputStream getFlujoEscritura() {
        return flujoEscritura;
    }
    
    
    
    private void initComponents() {
        this.setTitle("Piedra papel tijera");
        this.setSize(700, 500);
        nick = new JTextField(20);
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        nick.addKeyListener(this);
        panel.add(nick);
        
        this.getContentPane().add(panel);

        this.pack();
    
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    
    private void initCommunication(String nick) {
        try{
            cliente = new Socket("localhost",9998);
            this.flujoLectura = cliente.getInputStream();
            this.flujoEscritura = cliente.getOutputStream();
            //Thread hiloLectura = new Thread(this);
            //hiloLectura.start();
            DataOutputStream out =new DataOutputStream( cliente.getOutputStream());
            out.writeUTF(nick);
        } catch(Exception ex) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
  public void keyReleased(KeyEvent e)
  {
    if (e.getKeyCode() == 10)
    {
        DataInputStream in=null;
        try 
        {
            String nombre=this.nick.getText();
            
            this.nick.setText("");
            initCommunication(nombre);
            //in = new DataInputStream( cliente.getInputStream());
            //nombre = in.readUTF();
            Cliente c1 = Cliente.nCliente(cliente,nombre);//abrir la interfaz de los retos
            this.setVisible(false);//cerrar la interfaz
        } 
        catch (Exception ee){
          System.out.println(ee.toString());
        }
        
        /*finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(PantallaInicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }
  }
}