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
 * Clase cuya responsabilidad es gestionar el canal de comunicación entre el servidor
 * y los clientes 
 * @author Danip
 * @version 1.0
 * @since 01/07/2020
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
    /**
    * Método main se encarga de crar un nuevo hilo por cliente, e iniciarlo.
    */ 
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
   
    /**
    * Método que evalua los mensajes de comunicación entre clientes y servidor
    * y realiza unas u otras acciones dependiendo de éstos
    * Los protocolos seguidos son: (separados por arrobas, en vez de |)
    * reto | emisor | receptor | propuesto/aceptado/denegado/
    * partida | identificadorPartida | ronda | puntuacion1 | puntuacion2 | emisor
    * partida | identificadorPartida | piedra/papel/tijera/rendirse
    * @param elmensaje String es el mensaje bajo alguno de los protocolos usados
    * el cual será separado en partes y evaluado
    * @throws Exception si no puede evaluar el primer parámetro entre PROPUESTO,
    * ACEPTADO O DENEGADO
    */
    private static void evaluaMensaje(String elmensaje) {
      System.out.println("El Mensaje:"  + elmensaje);
        String[] partes= elmensaje.split("@");
        String R_P = partes[1];
        if (R_P.equals("RETO")) {
            String emisor = partes[2];
            String receptor = partes[3];
            String mensaje = partes[4];
            
            if (mensaje.equals("PROPUESTO")){

                /**
                 * Proponer un reto mediante la interfaz de usuario
                 */
                InterfazReto IR=new InterfazReto(emisor);
                
            } else if (mensaje.equals("ACEPTADO")) {
                String id = partes[5];
                PantallaInicio.getC().aceptado(emisor);
                /**
                 * Iniciar la interfaz de una partida nueva
                 */
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
            /**
             * Mostrar los nicks por el panel global
             */
            for (int i=2; i<partes.length; i++){
                PantallaInicio.getC().mostrarPorPanel(partes[i]);
            }
        } else if (R_P.equals("NICK")){
                
                PantallaInicio.getC().setNick(partes[2]);
        } else if (R_P.equals("GANADOR")){
            String idPartida = partes[2];
            String miPunt = partes[3];
            String suPunt = partes[4];
            if(Integer.parseInt(miPunt)>Integer.parseInt(suPunt)){
                PantallaInicio.getC().ganador(idPartida, miPunt, suPunt);
            } else if(Integer.parseInt(miPunt)<Integer.parseInt(suPunt)) {
                PantallaInicio.getC().perdedor(idPartida, miPunt, suPunt);
            } else {
                PantallaInicio.getC().empate(idPartida, miPunt, suPunt);
            }
        
        } else {
            System.out.println("No se pudo decidir si RETO o PARTIDA : " + R_P);
            Cliente interfazCliente = Cliente.nCliente(P1.getCliente(), "Pepito");
        }
        
    }
    /**
    * Método que busca una partida en funcióin de un identificador
    * @param id de tipo int es el identificador de la partida que se busca
    * @return un objeto de tipo InterfazPartida que será nulo en caso de que no
    * haya ninguna partida con ese identificador
    */
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
    
    /**
    * Método que busca un cliente en función de un nick (identificador)
    * @param nick de tipo String es el identificador del jugador que se busca
    * @return un objeto de tipo Cliente que será nulo en caso de que no
    * haya ninguno con ese nick
    */
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
    
    /**
    * Método que genera un mensaje para el servidor, que dependiendo de los parámetros
    * aportado será de un tipo o de otro
    * @param nick String con el nick del jugador que envia el reto
    * @param receptor String con el nick del adversario
    * @param opcion String que puede ser aceptado denegado o partida
    */
    public static void mensaje (String nick, String receptor, String opcion) {//reto,aceptado,denegado,partida
        String mensaje=("@RETO@"+nick +"@"+ receptor +"@"+ opcion +"@");
        sendMessage(mensaje);
    }
    
    /**
    * Método que genera un mensaje para el servidor, que dependiendo de los parámetros
    * aportado será de un tipo o de otro
    * @param idPartida Int que identifica la partida
    * @param jugada String que determina la opcion elegda por el jugador
    */
    public static void mensaje (int idPartida,String jugada) {
        String mensaje=("@PARTIDA@"+idPartida +"@"+ jugada + "@" + PantallaInicio.getC().getNick() +"@");
        sendMessage(mensaje);
    }
    /**
    * Método que envía un mensaje al servidor
    * @param mensaje String del mensaje que se envía
    * @exception si no se puede enviar datos por el flujo de escritura
    */
    private static void sendMessage(String mensaje)
    {
        
        try {
            
            PantallaInicio.getC().getFlujoEscritura().writeUTF(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(Receptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
