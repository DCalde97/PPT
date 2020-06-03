
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
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
 
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

  public static Jugador newJugador(Socket sck)
  {
      Jugador J1 = new Jugador(sck);
      return J1;
  }
  
    private Jugador(Socket sck)
    {
        try{      
          this.nick = leerNick(sck);
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
        try {
            byte[] buffer = new byte[1024]; //preguntar a garrido sobre los caracteres bacios
            in.read(buffer);
            String nick = new String(buffer,"UTF-8"); //en buffer esta el nick
        } catch (IOException ex) {  
            nick = null;
            System.out.println("No se pudo leer el nick del Jugador");
        }
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
        if (this.nick.equals(nick1)){
            return true;
        } else {
            return false;
        }
    }
    
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
              nb = in.read(buffer);
              baos.write(buffer, 0, nb);
            }while (nb>0 && in.available()>0);

            System.out.println("\t\t\tLlegando..." + new String(baos.toByteArray()));
          //Recibe el mensaje, hay que mostrar por pantalla cuando el J2 acepte el reto
          //Cuando J2 acepte el reto se recibe su confirmación
          //mandar confirmacion al servidor
          }catch (Exception ex){
             System.out.println("Error de comunicación");
          }

        }
      }
    });
    
    public String reciveMensage(){
        try {
            byte[] buffer = new byte[1024]; //preguntar a garrido sobre los caracteres bacios
            in.read(buffer);
            String mensage = new String(buffer,"UTF-8"); //en buffer esta el nick
            return mensage;
        } catch (IOException ex) {
          Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
          return null;
      }
    }

    public boolean actualizarFichero() {
        //Función que actualiza los datos de la cuenta en el fichero de cuentas
        boolean hecho;
	try {
            boolean ejecutado=false;
            FileReader entrada = new FileReader("C:\\Ranking.txt");
            /*Vamos a crear un archivo temporal para usarlo de
	    apoyo en la modificacion de Cuentas.txt*/
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
                                
				
				
        } catch (IOException e) {
            System.out.println("No se puede abrir el archivo,"
            + " ruta incorrecta o archivo inexistente");
            hecho = false;
        }
        return hecho;
    }
    

    public String toString(Jugador J){
        String cadena=(J.getNick()+";"+J.getPartidasGanadas()+";"+J.getRondasGanadas());
        return cadena;
    }
      
}
