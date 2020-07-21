/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppt_arguello_calderon;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Danip
 */
public class Receptor {
    
    private static ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
    public static ArrayList<InterfazPartida> partidasIniciadas = new ArrayList<>();
    static PantallaInicio P1=new PantallaInicio();
    public static void añadirIPartida(InterfazPartida P){
        partidasIniciadas.add(P);
    }
    
    public static void añadirCliente(Cliente C){
        listaClientes.add(C);
    }
    
    public static void main (String args[]) throws Exception
    {
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
                if (P1.getFlujoLectura() != null){
                  System.out.println("No NULO");
                    do{
                    nb = P1.getFlujoLectura().read(buffer);
                      System.out.println("NB:" + nb);
                    baos.write(buffer, 0, nb);
                  }while (nb>0 && P1.getFlujoLectura().available()>0);
                  evaluaMensaje(new String(baos.toByteArray()));
                  System.out.println("\t\t\tLlegando..." + new String(baos.toByteArray()));
                }
                else{
                  System.out.println("");
                }
                

              }catch (Exception ex){
                System.out.println(ex.toString());
              }

            }
          }
        });
        hiloLectura.start();
    }
   
//    private Socket cliente;
//    private InputStream flujoLectura;
//    private OutputStream flujoEscritura;

//    public Receptor() {
//        try{
//            cliente = new Socket("localhost",9998);
//            this.flujoLectura = cliente.getInputStream();
//            this.flujoEscritura = cliente.getOutputStream();
//            Thread hiloLectura = new Thread((Runnable) this);
//            hiloLectura.start();
//        }
//        catch(Exception ex) {
//        }
//    }
    
    /*Protocolos utilizados:
    reto @ emisor @ receptor @ propuesto/aceptado/denegado/
    partida @ identificadorPartida @ ronda @ puntuacion1 @ puntuacion2 @ emisor
    partida @ identificadorPartida @ piedra/papel/tijera/rendirse*/
    private static void evaluaMensaje(String elmensaje) {
      System.out.println("El Mensaje:"  + elmensaje);
        String[] partes= elmensaje.split("@");
        String R_P = partes[1];
        if (R_P.equals("RETO")) {
            String emisor = partes[2];
            String receptor = partes[3];
            String mensaje = partes[4];
            
            if (mensaje.equals("PROPUESTO")){
                //preguntar por la interfad de cliente al usuario si acepta el reto o no una vez preguntado que nos lo devuelva
                InterfazReto IR=new InterfazReto(emisor);
                
            } else if (mensaje.equals("ACEPTADO")) {
                String id = partes[5];
                PantallaInicio.getC().aceptado(emisor);
                
                //iniciar interfad partida
                InterfazPartida P=new InterfazPartida(Integer.parseInt(id));
                partidasIniciadas.add(P);
            } else if (mensaje.equals("DENEGADO")) {
                PantallaInicio.getC().denegado(emisor);
            } else {
                System.out.println("No se pudo decidir si PROPUESTO, ACEPTADO o DENEGADO");
            }
        } else if (R_P.equals("PARTIDA")) {
            String idPartida = partes[2];
            String ronda = partes[3];
            String miPunt = partes[4];
            String suPunt = partes[5];
            buscarIPartida(Integer.parseInt(idPartida)).mostrar( ronda, miPunt, suPunt);
        } else if (R_P.equals("NICKS")){
            //Mostrar en el panel global
            System.out.println("he entrado en NICKS");
            for (int i=2; i<partes.length; i++){
                PantallaInicio.getC().mostrarPorPanel(partes[i]);
            }
        } else if (R_P.equals("NICK")){
                
                PantallaInicio.getC().setNick(partes[2]);
                //comprueba el servidor si el nick esta repetido y devuelve el nick modificado para que no se repitan
                System.out.println("nick c pantalla inicio"+PantallaInicio.getC().getNick());
        } else {
          //Aqui os falta poner un protocolo para la lista de usuarios.
            System.out.println("No se pudo decidir si RETO o PARTIDA : " + R_P);
            Cliente interfazCliente = Cliente.nCliente(P1.getCliente(), "Pepito");
        }
        
    }
    
    public static InterfazPartida buscarIPartida(int id) {

        InterfazPartida P=null;
        for(InterfazPartida unaPartida : partidasIniciadas){
            if (unaPartida.equals(id)){
                P=unaPartida;
                break;
            }
        }
        return P;
    }
    
    public static Cliente buscarCliente(String nick) {
        
        Cliente C=null;
        try{
            for(Cliente unCliente : listaClientes){
                if (unCliente.equals(nick)){
                    C=unCliente;
                    break;
                }
            }
            if (C==null){
                NotFoundException ex;
                throw ex=new NotFoundException ("Cliente no encontrado");
            }
        } catch(NotFoundException ex){
            C=null;
        }
        return C;
    }
    
    //recepcion de mensajes
    
//    public  String recMensaje(Socket sck)
//    {
//        String mensaje;
//        //CAMBIAR
//        try {
//            byte[] buffer = new byte[1024]; //preguntar a garrido sobre los caracteres bacios
//            //[P,e,p,i,t,o,,,,,,,,,,,,,,,,,,,,,,,,,,,,,]
//            int nb = flujoLectura.read(buffer);
//            ByteArrayOutputStream baos = new  ByteArrayOutputStream();
//            baos.write(buffer, 0, nb);
//            
//            mensaje = new String(buffer,"UTF-8");
//        } catch (IOException ex) {
//            mensaje = null;
//            System.out.println("No se pudo recivir el mensaje del servidor");
//        }
//        return mensaje;
//    }
    
    
    //mensajes al servidor
    
    public static void mensaje (String nick, String receptor, String opcion) {//reto,aceptado,denegado,partida
        String mensaje=("@RETO@"+nick +"@"+ receptor +"@"+ opcion +"@");
        sendMessage(mensaje);
    }
    
//    public static void mensaje (String nick, String receptor, String opcion) {//reto,aceptado,denegado,partida
//        String mensaje=("@RETO@"+nick +"@"+ receptor +"@"+ opcion+"@" +"@");
//        sendMessage(mensaje);
//    }
    
    public static void mensaje (int idPartida,String jugada) {
        String mensaje=("@PARTIDA@"+idPartida +"@"+ jugada + "@" + PantallaInicio.getC().getNick() +"@");
        sendMessage(mensaje);
    }
    
    private static void sendMessage(String mensaje)
    {
        
        try {
            
            PantallaInicio.getC().getFlujoEscritura().writeUTF(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(Receptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
