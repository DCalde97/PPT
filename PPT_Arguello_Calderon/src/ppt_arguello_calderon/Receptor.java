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
        Thread hiloLectura = new Thread(new Runnable()
        {
          @Override
          public void run()
          {
            while (true){

              try{
                byte buffer[] = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int nb = 0;
                do{
                  nb = P1.getFlujoLectura().read(buffer);
                  baos.write(buffer, 0, nb);
                }while (nb>0 && P1.getFlujoLectura().available()>0);
                evaluaMensaje(new String(baos.toByteArray()));
                System.out.println("\t\t\tLlegando..." + new String(baos.toByteArray()));

              }catch (Exception ex){

              }

            }
          }
        });
        hiloLectura.start();
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
    
    /*Protocolos utilizados:
    reto @ emisor @ destinatario @ propuesto/aceptado/denegado/
    partida @ identificadorPartida @ ronda @ puntuacion1 @ puntuacion2
    partida @ identificadorPartida @ piedra/papel/tijera/rendirse*/
    private static void evaluaMensaje(String elmensaje) {
        String[] partes= elmensaje.split("@");
        String R_P = partes[0];
        if (R_P.equals("RETO")) {
            String emisor = partes[1];
            String receptor = partes[2];
            String mensaje = partes[3];
            if (mensaje.equals("PROPUESTO")){
                
            } else if (mensaje.equals("ACEPTADO")) {
                
            } else if (mensaje.equals("DENEGADO")) {
                
            } else {
                System.out.println("No se pudo decidir si PROPUESTO, ACEPTADO o DENEGADO");
            }
        } else if (R_P.equals("PARTIDA")) {
            String idPartida = partes[1];
            String ronda = partes[2];
            String miPunt = partes[3];
            String suPunt = partes[4];
        } else {
            System.out.println("No se pudo decidir si RETO o PARTIDA");
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
