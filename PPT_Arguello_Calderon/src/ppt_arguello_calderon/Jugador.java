
package ppt_arguello_calderon;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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

  private Jugador(Socket sck, String nick)
  {

    try{      
      this.nick = nick;
      this.sckJugador = sck;
      this.in = this.sckJugador.getInputStream();
      this.ou = this.sckJugador.getOutputStream(); 
      this.start();
    }
    
    catch(Exception ex){
        System.out.println("No se han podido asignar los valores del Jugador");
    }
    
  }
  
  public String getNick(){
    return this.nick;
  }
  public void setNick(String nick){
      this.nick=nick;
  } 
  
}
