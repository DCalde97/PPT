package ppt_arguello_calderon;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author ASUS
 */
public class BusquedaBroadcast {
    public static void main (String args[]){
    
    
    try{
      DatagramSocket socketUDP = new DatagramSocket();
      
      byte mensaje[] = "quienEstaAhi:".getBytes();
      
      DatagramPacket peticion = new DatagramPacket(mensaje, 0, mensaje.length, 
        InetAddress.getByName("192.168.1.255"), 
        10000);
      
      socketUDP.send(peticion);
      byte buffer[] = new byte[128];
      
      DatagramPacket respuesta = new DatagramPacket(buffer, 128);
      socketUDP.receive(respuesta);
      System.out.println("Respuesta: " + new String(respuesta.getData()));
      
      
       
    }catch (Exception ex){
      
    }
    
  }  
}
