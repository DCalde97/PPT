/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppt_arguello_calderon;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Danip
 */
public class Receptor {
    public static void main (String args[]) throws Exception
    {
        Receptor R1=new Receptor();
        do {
            
        }while(true);
    }
    
    private Socket cliente;
    private InputStream flujoLectura;
    private OutputStream flujoEscritura;

    public Receptor() {
        try{
            cliente = new Socket("localhost",9998);
            this.flujoLectura = cliente.getInputStream();
            this.flujoEscritura = cliente.getOutputStream();
            Thread hiloLectura = new Thread((Runnable) this);
            hiloLectura.start();
        }
        catch(Exception ex) {
        }
    }
    
    //recepcion de mensajes
    
    public  String recMensaje(Socket sck)
    {
        String mensaje;
        try {
            byte[] buffer = new byte[1024]; //preguntar a garrido sobre los caracteres bacios
            //[P,e,p,i,t,o,,,,,,,,,,,,,,,,,,,,,,,,,,,,,]
            int nb = flujoLectura.read(buffer);
            ByteArrayOutputStream baos = new  ByteArrayOutputStream();
            baos.write(buffer, 0, nb);
            
            mensaje = new String(buffer,"UTF-8"); //en buffer esta el nick
        } catch (IOException ex) {
            mensaje = null;
            System.out.println("No se pudo recivir el mensaje del servidor");
        }
        return mensaje;
    }
    
    
    //mensajes al servidor
    
    public void mensaje (String nick, String receptor, String opcion) {
        String mensaje=null;
        mensaje.concat("RETO"+nick +"@"+ receptor +"@"+ opcion);
        sendMessage(mensaje);
    }
    
    public void mensaje (int idPartida,String jugada) {
        String mensaje=null;
        mensaje.concat("PARTIDA"+idPartida +"@"+ jugada);
        sendMessage(mensaje);
    }
    
    private void sendMessage(String mensaje)
    {
        try{
          this.flujoEscritura.write(mensaje.getBytes());
        }
        catch(Exception ex){
            System.out.println("No se pudo leer el nick del Jugador");
        }
    }
}
