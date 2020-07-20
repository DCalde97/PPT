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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Danip
 */
public class InterfazPartida 
    extends JFrame
    implements ActionListener, Runnable, KeyListener {
    private JButton piedra;
    private JButton papel;
    private JButton tijera;
    private JPanel panel;
    private int id;

    public InterfazPartida(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public static void interfazPartida(){
        
    }

    public InterfazPartida() {
        
        initComponents();
        
    }
    
    private void initComponents() {
        this.setTitle("Piedra-Papel-Tijera");
        this.setSize(700, 500);
        this.piedra = new JButton("Piedra");
        this.papel = new JButton("Papel");
        this.tijera = new JButton("Tijera");
        this.panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        
        piedra.addActionListener(this);
        papel.addActionListener(this);
        tijera.addActionListener(this);
        
        piedra.add(piedra);
        papel.add(papel);
        tijera.add(tijera);
    }
    
    
    
    public void run(){
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == piedra){
           
        } else if(e.getSource() == papel){
            
        } else if(e.getSource() == tijera){
            
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
