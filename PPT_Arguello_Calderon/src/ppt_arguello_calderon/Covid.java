
package ppt_arguello_calderon;
/**
 *Clase que hereda de partida, y que como variante determina dos derrotas 
 * consecutivas para uno de los dos jugadores
 * @author ASUS
 * @version 1.0
 * @since 01/07/2020
 */
public class Covid 
extends Partida{
    
     /**
     * Método para llamar al constructor y añadir una partida covid nueva
     * @param J1 Jugador
     * @param J2 Jugador
     * @param id int con el identificador de la partida
     * @return un objeto de tipo Covid
     */
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
        /**
         * Uno de los dos jugadores (se ha aleatorizado su orden antes) perderá
         * dos rondas consecutivas. Luego se lleva el flujo de rondas de forma
         * normal
         */
        do {            
           
        } while (this.confirmacionMensaje < 2);
        int ganador;
        if (getRondas()>3) {
            ganador=1;
        }else{
            ganador= determinaGanador();
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

     /**
     * Método que interpreta las opciones elegidas de los jugadores
     * @param opcion String con la opcion elegida
     * @return un objeto de tipo Juego con la opción elegida
     */
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
