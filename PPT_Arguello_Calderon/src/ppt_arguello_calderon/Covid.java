
package ppt_arguello_calderon;
/**
 *
 * @author ASUS
 */
public class Covid 
extends Partida{
    
    public static Covid nCovid (Jugador J1, Jugador J2,int id){
        Covid P = new Covid(J1,J2,id);
        Servidor.partidasIniciadas.add(P);
        return P;
    }
    
    public Covid (Jugador J1, Jugador J2,int id){    
        super(J1,J2,id);
        this.start();     
    }
    
    public void run()
    {
  
        Reloj R1 = new Reloj();
        
        do {            
           
        } while (this.confirmacionMensaje < 2);
        int ganador;
        if (getRondas()>3) {
            ganador=1;
        }else{
            ganador= determinaGanador(opcionJ1, opcionJ2);
        }
        setRondas(getRondas()-1);
        asignaPuntuacion(ganador);
        confirmacionMensaje=0;
        String mensaje;
        mensaje=Jugador.mensaje ( id, rondas,puntuacionJ1,puntuacionJ2);
        J1.sendMessage(mensaje);
        mensaje=Jugador.mensaje ( id, rondas,puntuacionJ2,puntuacionJ1);
        J2.sendMessage(mensaje);
    }

    //crear exception personalizada
    private Juego transformar(String opcion){
        Juego op=null;
        if (opcion.equals("Piedra")){
            op=Juego.Piedra;
        } else if(opcion.equals("Papel")){
            op=Juego.Papel;
        } else if(opcion.equals("Tijera")){
            op=Juego.Tijera;
        } else {
            op=null;
        }
        return op;
    }
}
