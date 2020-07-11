/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppt_arguello_calderon;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class servidor {
    
    private static ArrayList<Jugador> jugadoresConectados = new ArrayList<>();
    public static ArrayList<Partida> partidasIniciadas = new ArrayList<>();
    
    public static void main (String args[]) throws Exception
    {
        ServerSocket server = new ServerSocket(10001);
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
            Jugador J1 = Jugador.newJugador(sck);
            jugadoresConectados.add(J1);
            listaNicks(J1);//revisar en cliente

      
        }while (true);

    }

    private static void listaNicks(Jugador J1) {
        String listaNicks="";
        for(Jugador unJugador : jugadoresConectados){
            listaNicks = listaNicks.concat(unJugador.getNick()+"@");
        }
        J1.sendMessage(listaNicks);
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
        String destino = partes[2];
        for(int i =0;i<jugadoresConectados.size();i++){
            if (jugadoresConectados.get(i).equals(destino)){
                jugadoresConectados.get(i).sendMessage(elmensaje);
                break;
            }
        }
    }
       
}
