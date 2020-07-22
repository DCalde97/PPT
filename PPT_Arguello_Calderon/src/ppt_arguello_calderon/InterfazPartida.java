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
import javax.swing.WindowConstants;

/**
 * Clase cuya responsabilidad es mostrar interfaces de partida, con las que podrán
 * interactuar dos jugadores con las opciones piedra, papel, tijera.
 * @author Danip
 * @version 1.0
 * @since 01/07/2020
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
        initComponents();
    }

    public int getId() {
        return id;
    }
    
    
    private void initComponents() {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.setTitle("Piedra-Papel-Tijera");
        this.setSize(700, 500);
        this.piedra = new JButton("Piedra");
        this.papel = new JButton("Papel");
        this.tijera = new JButton("Tijera");
        
        
        piedra.addActionListener(this);
        papel.addActionListener(this);
        tijera.addActionListener(this);
        
        panel.add(piedra);
        panel.add(papel);
        panel.add(tijera);
        
        this.getContentPane().add(panel);

        this.pack();
    
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    
    
    public void run(){
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == piedra){
            Receptor.mensaje (id,"Piedra");
            setVisible(false);
        } else if(e.getSource() == papel){
            Receptor.mensaje (id,"Papel");
            setVisible(false);
        } else if(e.getSource() == tijera){
            Receptor.mensaje (id,"Tijera");
            setVisible(false);
        }
    }
    /**
    * Método que comprueba si un id de partida es igual
    * @param id2 int identificador de la segunda partida
    * @return una variable de tipo boolean que sera true si el id es igual
    */     
    public boolean equals(int id2) {
        boolean t=false;
        if (Integer.toString(this.id).equals(Integer.toString(id2))){
            t=true;
        }
        return t;
    }
    
    /**
    * Método que muestra la puntuación de los jugadores.
    * @param ron String que indica las rondas que faltan
    * @param miP String que indica mi puntuación
    * @param suP String que indica la puntuación del adversario
    */     
    public void mostrar(String ron,String miP,String suP){
        this.setTitle("Ronda: "+ron+" Yo: "+miP+" El: "+suP);
        setVisible(true);
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
