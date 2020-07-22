
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
        P.start();
        return P;
    }
    
    public Covid (Jugador J1, Jugador J2,int id){    
        super(J1,J2,id);
    }
    
    @Override
    public void run()
    {
        this.rondas++;
        do{
            Reloj R1 = new Reloj();
            R1.run(10, id);
            do {
                System.out.println("Sin jugadas");
            /**
            * Uno de los dos jugadores (se ha aleatorizado su orden antes) perderá
            * dos rondas consecutivas. Luego se lleva el flujo de rondas de forma
            * normal
            */
            } while (this.confirmacionMensaje < 2);
            R1.stop();
            System.out.println(opcionJ1+opcionJ2);
            int ganador;
            if (getRondas()>3) {
                ganador=1;
            }else{
                ganador= determinaGanador();
            }
            asignaPuntuacion(ganador);
            opcionJ1="NADA";
            opcionJ2="NADA";
            this.rondas--;
            confirmacionMensaje=0;
            String mensaje1;
            String mensaje2;
            if (rondas!=0) {
                mensaje1=Jugador.mensaje ( id, rondas,puntuacionJ1, puntuacionJ2);
                mensaje2=Jugador.mensaje ( id, rondas,puntuacionJ2, puntuacionJ1);
            } else {
                mensaje1=Jugador.mensaje ( id, puntuacionJ1, puntuacionJ2);
                mensaje2=Jugador.mensaje ( id, puntuacionJ2, puntuacionJ1);
            }
            J1.sendMessage(mensaje1);
            J2.sendMessage(mensaje2);
        }while(rondas>0);
    }
}
