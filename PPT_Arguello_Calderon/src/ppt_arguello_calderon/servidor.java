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
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class servidor {
    //El servidor
    private static ArrayList<Jugador> jugadoresConectados = new ArrayList<>();
    
    public static void main (String args[]) throws Exception
    {
        ServerSocket server = new ServerSocket(9669);
        System.out.println("Servidor Arrancado");

        do{
            Socket sck =  server.accept();  
            System.out.println("Alguien conectado...");  //quitar eso
            Jugador J1 = Jugador.newJugador(sck);
            jugadoresConectados.add(J1);
            listaNicks(J1);
            String nick=J1.leerNick(J1.sckJugador);
            Jugador J2=buscarJugador(nick);
            J2.sendMessage("Reto");
            byte[] buffer = new byte[1024]; //preguntar a garrido sobre los caracteres bacios
            J2.in.read(buffer);
            
            int rand;
            rand = (int) (Math.random()*6+1);
            
            if (rand!=6) {
                
                Partida P1=new Partida(J1,J2);

            }else{
                rand = (int) (Math.random()*2+1);
                
                if (rand==1) {
                    Covid C1=new Covid(J1,J2);
                }else{
                    Covid C1=new Covid(J2,J1);

                }
                

            }
            
            //recibir
            //establecer partida entre jugadores
        }while (true);

    }

    private static void listaNicks(Jugador J1) {
        String listaNicks="";
        for(Jugador unJugador : jugadoresConectados){
            listaNicks = listaNicks.concat(unJugador.getNick()+";");
        }
        J1.sendMessage(listaNicks);
    }

    private static Jugador buscarJugador(String nick) {
        
        Jugador J2=null;
        try{
            for(Jugador unJugador : jugadoresConectados){
                if (unJugador.equals(nick)){
                    J2=unJugador;
                    break;
                } else {
                    J2=null;
                }
            }
            if (J2==null){
                throw new NotFoundException ("Jugador no encontrado");
            }
        } catch(NotFoundException ex){
            J2=null;
        }
        return J2;
    }
       
}
