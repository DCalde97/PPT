
package ppt_arguello_calderon;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class Jugador 
    extends Thread
{
  private String nick;
  Socket sckJugador;
  InputStream in;
  OutputStream ou;

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
          this.start();
        } catch(Exception ex){
            System.out.println("No se han podido asignar los valores del Jugador");
        }
    }
  
    public  String leerNick(Socket sck)
    {
        try {
            byte[] buffer = new byte[1024]; //preguntar a garrido sobre los caracteres bacios
            in=sck.getInputStream();
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
}
