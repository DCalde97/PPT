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
 *
 * @author ASUS
 */
public class Servidor {
    
    private static ArrayList<Jugador> jugadoresConectados = new ArrayList<Jugador>();
    public static ArrayList<Partida> partidasIniciadas = new ArrayList<Partida>();
    
    public static void main (String args[]) throws Exception
    {
        ServerSocket server = new ServerSocket(9998);
        System.out.println("Servidor Arrancado");
        
        /*
        do{
            Socket sck =  server.accept();  
            System.out.println("Alguien conectado...");  //quitar eso
            Jugador J1 = Jugador.newJugador(sck);
            jugadoresConectados.add(J1);
            listaNicks(J1);
            String nick=J1.leerNick(J1.sckJugador);
            Jugador J2=buscarJugador(nick);
            J2.sendMessage("Reto");
            byte[] buffer = new byte[1024]; //preguntar a garrido sobre los caracteres vacios
            //recibimos la respuesta de si acepta o no el reto pero la respuesta es un int porque?
            //despues comprobamos si acepta se crea la partida sino
            //implementar algo cuando se cancela la partida
                //String resp=buffer.toString().getBytes("UTF-8");
            //if(){
            creaPartida(J1,J2);
            //}
        }while (true);
        */
        
        do{
            Socket sck =  server.accept();  
            System.out.println("Alguien conectado...");
            
            Jugador J1 = Jugador.newJugador(sck,recNick(sck));
            jugadoresConectados.add(J1);
            Thread.sleep(1000);
            listaNicks(sck);//revisar en cliente

      
        }while (true);

    }
    
    private static String recNick(Socket sck){
        String nick=null;
        String NICK="@NICK@";
        try {
            DataInputStream in = new DataInputStream( sck.getInputStream());
            nick = in.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        int id=generarIdNick(nick);
        if (id!=0) {
            nick=nick.concat(Integer.toString(id));
        }
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
    
    
    
    public static Partida buscarPartida(int id) {

        Partida P=null;
        try{
            for(Partida unaPartida : partidasIniciadas){
                if (unaPartida.getId()==id){
                    P=unaPartida;
                    break;
                }
            }
            if (P==null){
                NotFoundException ex;
                throw ex=new NotFoundException ("Partida no encontrada");
            }
        } catch(NotFoundException ex){
            P=null;
        }
        return P;
    }

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
    
    public static void Retransmitir(String elmensaje) {
        String[] partes= elmensaje.split("@");
        String destino = partes[3];
        System.out.println("sig ins el for destino:  "+destino+"  mensaje:  "+elmensaje);
        for(Jugador unJugador : jugadoresConectados){
            System.out.println("entre en el for");
            if (unJugador.getNick().equals(destino)){
                System.out.println("entre en el if");
                unJugador.sendMessage(elmensaje);
                System.out.println("mande el mensaje");
                break;
            }
        }
    }
    
    public int generarIdPartida(){
        Partida P=null;
            for(Partida unaPartida : partidasIniciadas){
                    P=unaPartida;
            }
        return P.getIdent()+1;
    }
    
    public static int generarIdNick(String nick){
        int codigo=0;
            for(Jugador unJugador : jugadoresConectados){
                System.out.println(unJugador.getNick());
                if (unJugador.getNick().equals(nick)){
                    codigo++;
                    System.out.println(codigo+"coincide");
                } else {
                    System.out.println("falso");
                }
            }
        return codigo;
    }
       
}