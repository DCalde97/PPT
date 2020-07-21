/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppt_arguello_calderon;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Danip
 */
public class InterfazReto extends JFrame
    implements ActionListener {
    private JButton Aceptar;
    private JButton Denegar;
    private JPanel panel;
    private String retador;

    public InterfazReto(String retador) {
        this.retador = retador;
        initComponents();
    }
    
    private void initComponents() {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.setTitle("Reto de "+retador);
        this.setSize(700, 500);
        this.Aceptar = new JButton("Aceptar");
        this.Denegar = new JButton("Denegar");
        
        
        Aceptar.addActionListener(this);
        Denegar.addActionListener(this);
        
        panel.add(Aceptar);
        panel.add(Denegar);
        
        this.getContentPane().add(panel);

        this.pack();
    
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Aceptar){
            Receptor.mensaje (PantallaInicio.getC().getNick(), retador,"ACEPTADO");
            this.setVisible(false);
        } else if(e.getSource() == Denegar){
            Receptor.mensaje (PantallaInicio.getC().getNick(), retador,"DENEGADO");
            this.setVisible(false);
        }
    }
}
