
package ppt_arguello_calderon;

import static com.sun.org.apache.xerces.internal.util.FeatureState.is;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import static ppt_arguello_calderon.Servidor.partidasIniciadas;
import java.io.*;

/**
 *
 * @author ASUS
 */

public class Jugador 
    extends Thread
{
  private File fichero;
  private String nick;
  Socket sckJugador;
  InputStream in;
  OutputStream ou;
  private int partidasGanadas;
  private int rondasGanadas;
  private String opcion;

  public static Jugador newJugador(Socket sck,String nick)
  {
      Jugador J1 = new Jugador(sck,nick);
      return J1;
  }
  
    private Jugador(Socket sck,String nick)
    {
        try{      
          this.nick = nick;
          this.sckJugador = sck;
          this.in = this.sckJugador.getInputStream();
          this.ou = this.sckJugador.getOutputStream(); 
          this.partidasGanadas=0;
          this.rondasGanadas=0;
          this.start();
        } catch(Exception ex){
            System.out.println("No se han podido asignar los valores del Jugador");
        }
    }
  
    public  String leerNick(Socket sck)
    {
        String nick;
        try {
//            byte[] buffer = new byte[1024]; //preguntar a garrido sobre los caracteres bacios
//            //[P,e,p,i,t,o,,,,,,,,,,,,,,,,,,,,,,,,,,,,,]
//            int nb = in.read(buffer);
//            ByteArrayOutputStream baos = new  ByteArrayOutputStream();
//            baos.write(buffer, 0, nb);
//            String[] partes= new String(baos.toByteArray()).split("@");
//            nick = partes[0];
            DataInputStream in = new DataInputStream( sck.getInputStream());
            nick=in.readUTF();
            
        } catch (IOException ex) {  
            nick = null;
            System.out.println("No se pudo leer el nick del Jugador");
        }
        System.out.println("nick");
        return nick;
    }
  
    public String getNick(){
        return this.nick;
    }
    
    public String getOpcion(){
        return this.opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }
    

    public void setNick(String nick){
        this.nick=nick;
    }
    
    public int getPartidasGanadas(){
        return this.partidasGanadas;
    }
    public void setPartidasGanadas(int partidasGanadas){
        this.partidasGanadas=partidasGanadas;
        actualizarFichero();
    }
    public int getRondasGanadas(){
        return this.rondasGanadas;
    }
    public void setRondasGanadas(int rondasGanadas){
        this.rondasGanadas=rondasGanadas;
        actualizarFichero();
    } 

    public void sendMessage(String mensaje)
    {
        try{
          this.ou.write(mensaje.getBytes());
        }
        catch(Exception ex){
            System.out.println("No se pudo leer el nick del Jugador");
        }
    }

    public boolean equals(String nick1){
        boolean igual;
        if (this.nick.equals(nick1)){
            igual=true;
        } else {
            igual=false;
        }
        return igual;
    }
    
    public void run() {
    while (true)
    {
      try{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int nb = 0;
        do{
          nb = this.in.read(buffer);
          baos.write(buffer, 0, nb);
        }while (nb>0 && this.in.available()>0);
            //Retransmitir a todos los demas usuarios/clientes
            evaluaMensaje(new String(baos.toByteArray()));
        }catch (Exception ex){

        } 
      } 
    }
    
    private static void evaluaMensaje(String elmensaje) {
        String[] partes= elmensaje.split("@");
        String R_P = partes[0];
        if (R_P.equals("RETO")) {
            String emisor = partes[1];
            String receptor = partes[2];
            String mensaje = partes[3];
            
            if (mensaje.equals("ACEPTADO")){
                //crear partida
                
                
                
            } else if (mensaje.equals("DENEGADO")) {
                Servidor.Retransmitir(elmensaje);
            } else if (mensaje.equals("PROPUESTO")) {
                int id=generaId();
                elmensaje=mensaje(emisor,receptor,mensaje,id);
                Servidor.Retransmitir(elmensaje);
            } else if (mensaje.equals("PARTIDA")) {
                creaPartida(Servidor.buscarJugador(receptor),Servidor.buscarJugador(emisor));
            } else {
                System.out.println("No se pudo decidir si PROPUESTO, ACEPTADO o DENEGADO");
            }
        } else if (R_P.equals("PARTIDA")) {
            String idPartida = partes[1];
            String jugada = partes[2];
            String nick=partes[3];
            Partida P1 = Servidor.buscarPartida(Integer.parseInt(idPartida));
            P1.setConfirmacionMensaje(P1.getConfirmacionMensaje()+1);
            Jugador J1 = Servidor.buscarJugador(nick);
            J1.setOpcion(jugada);
           
        } else {
            System.out.println("No se pudo decidir si RETO o PARTIDA");
        }
        
    }
    
    public static String mensaje (String nick, String receptor, String opcion, int id) {//reto,aceptado,denegado,partida
        String mensaje=null;
        mensaje.concat("RETO"+nick +"@"+ receptor +"@"+ opcion +"@"+id+"@");
        return mensaje;
    }
    
    private static void creaPartida(Jugador J1, Jugador J2){
        int rand = (int) (Math.random()*6+1);
        int id=generaId();
        if (rand!=6) {
            Partida P1= Partida.nPartida(J1,J2,id);
            partidasIniciadas.add(P1);
            //return P1;
        }else{
            rand = (int) (Math.random()*2+1);
            Covid C1;
            if (rand==1) {
                C1=new Covid(J1,J2,id);
                //return C1;
            }else{
                C1=new Covid(J2,J1,id);
                //return C1;
            }
            partidasIniciadas.add(C1);
        }
    }
    
    private static int generaId(){
        int id;
        id=partidasIniciadas.get(partidasIniciadas.size()).getIdent()+1;
        return id;
    }
    
    //Función que actualiza los datos del fichero de jugadores/puntuaciones
    public boolean actualizarFichero() {
        boolean hecho;
	try {
            boolean ejecutado=false;
            FileReader entrada = new FileReader("C:\\Ranking.txt");
            FileWriter entradaTemporal = new FileWriter("C:\\RankingTemporal.txt");
            BufferedReader buffer = new BufferedReader(entrada);				
            
            String linea="";				 
            while(linea!=null){
                linea=buffer.readLine();
                if(linea!=null){
                    String[] partes = linea.split(";", 3);
		    String nick = partes[0]; 
		    String rondas = partes[1]; 
		    String partidas = partes[2];
		    if(this.nick.equals(nick)){
                        linea = (this.nick+";"+this.partidasGanadas+";"+this.rondasGanadas);
		        entradaTemporal.write(linea+"\n");
		        ejecutado = true;
		    } else {
		        entradaTemporal.write(linea+"\n");
		    }
		}
            }		                
            entrada.close();
            buffer.close();
            entradaTemporal.close();
            hecho = reemplazarFicheros(ejecutado);           
        } catch (IOException e) {
            System.out.println("No se puede abrir el archivo,"
            + " ruta incorrecta o archivo inexistente");
            hecho = false;
        }
        return hecho;
    }
        
    private boolean reemplazarFicheros(boolean ejecutado){
        boolean hecho;
        if(ejecutado==true){
            File f1 = new File("C:\\Ranking.txt");
            File f2 = new File("C:\\RankingTemporal.txt");
            File f3 = new File("C:\\Temporal.txt");
            f1.renameTo(new File("C:\\Temporal.txt"));
            f2.renameTo(new File("C:\\Ranking.txt"));
            f3.renameTo(new File("C:\\RankingTemporal.txt"));
            hecho = true;
        } else {
            hecho = false;   
        }
        return hecho;
    }
    

    public String toString(Jugador J){
        String cadena=(J.getNick()+";"+J.getPartidasGanadas()+";"+J.getRondasGanadas());
        return cadena;
    }
      
}

