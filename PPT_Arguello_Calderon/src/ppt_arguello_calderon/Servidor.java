/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppt_arguello_calderon;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase cuya responsabilidad es gestionar los clientes y las partidas, y la
 * comunicación entre estos
 * @author ASUS
 * @version 1.0
 * @since 01/07/2020
 */
public class Servidor {
    
    private static ArrayList<Jugador> jugadoresConectados = new ArrayList<Jugador>();
    public static ArrayList<Partida> partidasIniciadas = new ArrayList<Partida>();
    /**
    * El metodo main del servidor abre la conexión y se mantiene a la espera de
    * clientes
    */
    public static void main (String args[]) throws Exception
    {
        ServerSocket server = new ServerSocket(9998);
        System.out.println("Servidor Arrancado");

        do{
            Socket sck =  server.accept();  
            System.out.println("Alguien conectado...");
            
            Jugador J1 = Jugador.newJugador(sck,recNick(sck));
            jugadoresConectados.add(J1);
            Thread.sleep(100);
            listaNicks(sck);

      
        }while (true);

    }
    /**
    * Método que procesa el nick del cliente para concaternarlo en el mensaje
    * con el protocolo que seguimos: @NICK@"nick", para añadirlo a la lista de 
    * nicks, de la forma: @NICKS@nick1@nick2@nick3...
    * @param sck Socket del cliente
    * @throws Exception si no puede enviar/recibir datos por el canal de comunicación
    * @return una variable de tipo String, el nick del jugador
    */ 
    private static String recNick(Socket sck){
        String nick=null;
        String NICK="@NICK@";
        try {
            DataInputStream in = new DataInputStream( sck.getInputStream());
            nick = in.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        nick=generarIdNick(nick);
        try {
            DataOutputStream out =new DataOutputStream( sck.getOutputStream());
            NICK=NICK.concat(nick);
            System.out.print("nick:"+nick+"\n");
            System.out.print("NICK:"+NICK+"\n");
            out.writeUTF(NICK);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nick;
    }

    /**
    * Método que proporciona la lista de nicks de todos los jugadores conectados 
    * por el canal de comunicación
    * @param sck Socket del cliente
    * @throws Exception cuando no puede enviar la lista de nick por el canal de
    * comunicación.
    */  
    private static void listaNicks(Socket sck) {
        String listaNicks="@NICKS@";
        for(Jugador unJugador : jugadoresConectados){
            listaNicks=listaNicks.concat(unJugador.getNick()+"@");
        }
        System.out.print("NICKS:"+listaNicks);
        try {
            DataOutputStream out =new DataOutputStream( sck.getOutputStream());
            out.writeUTF(listaNicks);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    /**
    * Método que busca una partida concreta en la lista de partidas iniciadas
    * @param id es el identificador de la partida
    * @return un objeto de tipo Partida que tiene por id el recibido. O null si
    * no existe esa partida.
    */   
    public static Partida buscarPartida(int id) {

        Partida P=null;
        for(Partida unaPartida : partidasIniciadas){
            if (unaPartida.equals(id)){
                P=unaPartida;
                break;
            }
        }
        return P;
    }

    /**
    * Método que busca un jugador en la lista de jugadores conectados.
    * @param nick Identificador del jugador que se busca
    * @return un objeto de tipo Jugador que tiene por nick el recibido por param.
    */ 
    public static Jugador buscarJugador(String nick) {
        
        Jugador J2=null;
        try{
            for(Jugador unJugador : jugadoresConectados){
                if (unJugador.equals(nick)){
                    J2=unJugador;
                    break;
                }
            }
            if (J2==null){
                NotFoundException ex;
                throw ex=new NotFoundException ("Jugador no encontrado");
            }
        } catch(NotFoundException ex){
            J2=null;
        }
        return J2;
    }
    
    /**
    * Método que envia un mensaje a los jugadores conectados en el momento.
    * @param elmensaje Es un String que se mostrará a todos los jugadores
    */
    public static void Retransmitir(String elmensaje) {
        String[] partes= elmensaje.split("@");
        String destino = partes[3];
        for(Jugador unJugador : jugadoresConectados){
            if (unJugador.getNick().equals(destino)){
                unJugador.sendMessage(elmensaje);
                break;
            }
        }
    }
    
    /**
    * Método que genera un identificador de partida de forma incremental
    * @return una variable de tipo int, el identificador de la partida
    */ 
    public int generarIdPartida(){
        Partida P=null;
            for(Partida unaPartida : partidasIniciadas){
                    P=unaPartida;
            }
        return P.getIdent()+1;
    }
    
    
    /**
    * Método que comprueba si un nick de jugador a coenctar ya existe en la lista
    * de jugadores. En cuyo caso devuelve un valor entero !=0
    * @param nick String nick del jugador a comprobar.
    * @return una variable de tipo int, que será 0 si el nombre no está conectado,
    * o 1 si existe.
    */ 
    public static String generarIdNick(String nick){
        int codigo;
        boolean coincide=false;
        String n=nick;
            do{
                coincide=false;
                for(Jugador unJugador : jugadoresConectados) {
                    if (unJugador.getNick().equals(n)){
                        codigo=(int)(Math.random()*100+1);
                        n=nick+codigo;
                        coincide=true;
                    }
                }
            }while(coincide==true);
        return n;
    }
       
}