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
 * Clase que extiende de JFrame, encargada de mostrar el mensaje de obtención
 * del nick para cuando un jugador quiere conectarse
 * @author Danip
 * @version 1.0
 * @since 01/07/2020
 */
public class PantallaInicio 
    extends JFrame
    implements Runnable, KeyListener {
    private JTextField nick;
    private JPanel panel;
    
    private Socket cliente;
    private InputStream flujoLectura;
    private OutputStream flujoEscritura;
    
    private static Cliente C;

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

    public static Cliente getC() {
        return C;
    }
  
    private void initComponents() {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.setTitle("Piedra papel tijera");
        this.setSize(700, 500);
        nick = new JTextField(20);
        
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
            C.setFlujoEscritura(new DataOutputStream( cliente.getOutputStream()));
            C.setFlujoLectura(new DataInputStream( cliente.getInputStream()));
            C.getFlujoEscritura().writeUTF(nick);
        } catch(Exception ex) {
        }
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
   /**
   * Método que envia el nick introducido en el campo de texto al pulsar enter
   * y además inicia la segunda interfaz de retos
   */
  public void keyReleased(KeyEvent e)
  {
    if (e.getKeyCode() == 10)
    {
        DataInputStream in=null;
        try 
        {
            String nombre=this.nick.getText();
            
            this.nick.setText("");
            C=Cliente.nCliente(cliente,nombre);//abrir la interfaz de los retos
            initCommunication(nombre);
            this.setVisible(false);//cerrar la interfaz
        } 
        catch (Exception ee){
          System.out.println(ee.toString());
        }
      
    }
  }
}