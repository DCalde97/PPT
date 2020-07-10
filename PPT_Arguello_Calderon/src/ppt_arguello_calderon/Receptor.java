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
import java.util.ArrayList;

/**
 *
 * @author Danip
 */
public class Receptor {
    private static ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
    
    public static void añadirCliente(Cliente C){
        listaClientes.add(C);
    }
    
    public static void main (String args[]) throws Exception
    {
        
        PantallaInicio P1=new PantallaInicio();
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
        //CAMBIAR
        try {
            byte[] buffer = new byte[1024]; //preguntar a garrido sobre los caracteres bacios
            //[P,e,p,i,t,o,,,,,,,,,,,,,,,,,,,,,,,,,,,,,]
            int nb = flujoLectura.read(buffer);
            ByteArrayOutputStream baos = new  ByteArrayOutputStream();
            baos.write(buffer, 0, nb);
            
            mensaje = new String(buffer,"UTF-8");
        } catch (IOException ex) {
            mensaje = null;
            System.out.println("No se pudo recivir el mensaje del servidor");
        }
        return mensaje;
    }
    
    
    //mensajes al servidor
    
    public static void mensaje (String nick, String receptor, String opcion, Socket Cliente) {//reto,aceptado,denegado
        String mensaje=null;
        mensaje.concat("RETO"+nick +"@"+ receptor +"@"+ opcion);
        sendMessage(mensaje, Cliente);
    }
    
    public void mensaje (int idPartida,String jugada, Socket Cliente) {
        String mensaje=null;
        mensaje.concat("PARTIDA"+idPartida +"@"+ jugada);
        sendMessage(mensaje, Cliente);
    }
    
    private static void sendMessage(String mensaje, Socket Cliente)
    {
        try{
          Cliente.getOutputStream().write(mensaje.getBytes());
        }
        catch(Exception ex){
            System.out.println("No se pudo leer el nick del Jugador");
        }
    }
}
