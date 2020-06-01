/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppt_arguello_calderon;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class servidor {
    //El servidor
    private static ArrayList<Jugador> JugadoresConectados = new ArrayList<>();
    
    public static void main (String args[]) throws Exception
  {
    ServerSocket server = new ServerSocket(9669);
    System.out.println("Servidor Arrancado");
    
    do{
      
      
      Socket sck =  server.accept();  
      System.out.println("Alguien conectado...");  //quitar eso
      Jugador cliente = new Jugador(sck);
      JugadoresConectados.add(cliente);
      
    }while (true);
    
  }
}
