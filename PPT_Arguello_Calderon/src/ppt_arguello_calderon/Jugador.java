
package ppt_arguello_calderon;

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
import java.io.*;

/**
 * Clase que extiende de Thread, encargada de la gestión de jugadores y su información
 * @author ASUS
 * @version 1.0
 * @since 01/07/2020
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
  
  /**
   * Método que llama al constructor de Jugador
   * @param sck Socket del cliente
   * @param nick String con el nick del jugador
   * @return un ohjeto de tipo Jugador
   */
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
  
     /**
     * Método que lee el nick de un jugador
     * @param sck Socket del cliente
     * @return un String con el nick del jugador
     */
    public  String leerNick(Socket sck)
    {
        String nick;
        try {
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
    
    public void setNick(String nick){
        this.nick=nick;
    }
    
    public int getPartidasGanadas(){
        return this.partidasGanadas;
    }
    public void setPartidasGanadas(int partidasGanadas){
        System.out.println(partidasGanadas);
        this.partidasGanadas=partidasGanadas;
        //actualizarFichero();
    }
    public int getRondasGanadas(){
        return this.rondasGanadas;
    }
    public void setRondasGanadas(int rondasGanadas){
        this.rondasGanadas=rondasGanadas;
        System.out.println(rondasGanadas);
        //actualizarFichero();
    } 

    /**
     * Metodo para enviar un mensaje por el socket
     * @param mensaje String con el mensaje a enviar
     */
    public void sendMessage(String mensaje)
    {
        try{
          this.ou.write(mensaje.getBytes());
        }
        catch(Exception ex){
            System.out.println("No se pudo leer el nick del Jugador");
        }
    }

     /**
     * Método que comprueba si un nick es igual al actual
     * @param nick1 String con el nick a comparar
     * @return una variable boolean que será true si los nicks son iguales
     */
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
        while (true){

          try{
            byte buffer[] = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int nb = 0;
            if (in != null){
                System.out.println("No NULO");
                do{
                    nb = in.read(buffer);
                    System.out.println("NB:" + nb);
                    baos.write(buffer, 0, nb);
                }while (nb>0 && in.available()>0);
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
    
     /**
     * Método que evalua los mensajes del jugador
     * @param elmensaje 
     */
    private static void evaluaMensaje(String elmensaje) {
        
        String[] partes= elmensaje.split("@");
        System.out.println(elmensaje);
        String R_P = partes[1];
        if (R_P.equals("RETO")) {
            String emisor = partes[2];
            String receptor = partes[3];
            String mensaje = partes[4];
            System.out.println(elmensaje);
            if (mensaje.equals("ACEPTADO")){
                //crear partida
                int id=generaId();
                creaPartida(Servidor.buscarJugador(emisor),Servidor.buscarJugador(receptor),id);
                elmensaje=mensaje(emisor,receptor,mensaje,id);
                Servidor.Retransmitir(elmensaje);
                elmensaje=mensaje(receptor,emisor,mensaje,id);
                Servidor.Retransmitir(elmensaje);
            } else if (mensaje.equals("DENEGADO")) {
                Servidor.Retransmitir(elmensaje);
            } else if (mensaje.equals("PROPUESTO")) {
                elmensaje=mensaje(emisor,receptor,mensaje);
                Servidor.Retransmitir(elmensaje);
            } else {
                System.out.println("No se pudo decidir si PROPUESTO, ACEPTADO o DENEGADO");
            }
        } else if (R_P.equals("PARTIDA")) {
            String idPartida = partes[2];
            String jugada = partes[3];
            String nick=partes[4];
            Partida P1 = Servidor.buscarPartida(Integer.parseInt(idPartida));
            System.out.println(P1.getId());
            int conf=P1.confirmacionMensaje;
            P1.confirmacionMensaje=conf+1;
            Jugador J1 = Servidor.buscarJugador(nick);
            if (P1.getJ1().equals(J1)){
                P1.opcionJ1=jugada;
            } else {
                P1.opcionJ2=jugada; 
            }
            System.out.println("jugada asignada");
           
        } else {
            System.out.println("No se pudo decidir si RETO o PARTIDA");
        }
        
    }
    /**
    * Método que genera un mensaje para el servidor, que dependiendo de los parámetros
    * aportados será de un tipo o de otro
    * @param nick String con el nick del jugador que envia el reto
    * @param receptor String con el nick del adversario
    * @param opcion String que puede ser aceptado denegado o partida
    * @return String con el mensaje
    */
    public static String mensaje (String nick, String receptor, String opcion) {//reto,aceptado,denegado
        String m=("@RETO@"+nick +"@"+ receptor +"@"+ opcion +"@");
        return m;
    }
    /**
    * Método que genera un mensaje para el servidor, que dependiendo de los parámetros
    * aportados será de un tipo o de otro
    * @param nick String con el nick del jugador que envia el reto
    * @param receptor String con el nick del adversario
    * @param opcion String que puede ser aceptado denegado o partida
    * @param id int con el identificador de la partida
    * @return String con el mensaje
    */
    public static String mensaje (String nick, String receptor, String opcion,int id) {//reto,aceptado,denegado,partida
        String m=("@RETO@"+nick +"@"+ receptor +"@"+ opcion +"@"+id+"@");
        return m;
    }
    /**
    * Método que genera un mensaje para el servidor, que dependiendo de los parámetros
    * aportados será de un tipo o de otro
    * @param id int que identifica la partida
    * @param ronda int que muestra cuantas rondas quedan
    * @param miPunt int que indica mi puntuacion
    * @param suPunt int que indica la puntuacion del adversario
    * @return String con el mensaje
    */
    public static String mensaje (int id,int ronda,int miPunt,int suPunt) {//reto,aceptado,denegado,partida
        String m=("@PARTIDA@"+id +"@"+ ronda +"@"+ miPunt +"@"+suPunt+"@");
        return m;
    }
    /**
    * Método que genera un mensaje para el servidor, que dependiendo de los parámetros
    * aportados será de un tipo o de otro
    * @param id int que identifica la partida
    * @param miPunt int que indica mi puntuacion
    * @param suPunt int que indica la puntuacion del adversario
    * @return String con el mensaje
    */
    public static String mensaje (int id,int miPunt,int suPunt) {//reto,aceptado,denegado,partida
        String m=("@GANADOR@"+ id +"@"+ miPunt +"@"+suPunt+"@");
        return m;
    }
    
    
    /**
     * Método que determina si una partida tiene covid o no con una posibilidad
     * entre 6 de que ésto ocurra
     * @param J1 Jugador
     * @param J2 Jugador
     * @param id int que identifica la partida
     */
    private static void creaPartida(Jugador J1, Jugador J2,int id){
        int rand = (int) (Math.random()*6+1);
        if (rand!=6) {
            Partida P1= Partida.nPartida(J1,J2,id);
            Servidor.partidasIniciadas.add(P1);
            
        }else{
            rand = (int) (Math.random()*2+1);
            Covid C1;
            if (rand==1) {
                C1=Covid.nCovid(J1,J2,id);
                
            }else{
                C1=Covid.nCovid(J2,J1,id);
                
            }
            Servidor.partidasIniciadas.add(C1);
        }
        
    }
    
     /**
     * Método que genera el ID de una partida de forma incremental
     * @return variable int que identifica la partida
     */
    private static int generaId(){
        int id;
        if (Servidor.partidasIniciadas.isEmpty()){
            id=0;
        } else {
            id=(Servidor.partidasIniciadas.get(Servidor.partidasIniciadas.size()-1).getIdent());
            id++;
        }
        return id;
    }
    
    /**
     * Método que actualiza los datos del fichero de jugadores/puntuaciones
     * @return variable boolean que indica si se ha actualizado el fichero
     */    
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
     /**
     * Método que escribe los cambios del fichero nuevo
     * @param ejecutado Boolean que determina si se escribe el fichero o no
     * @return variable boolean que indica si se ha escrito correctamente
     */    
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

