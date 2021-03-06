package ppt_arguello_calderon;

/**
 * Clase encargada de gestionar el flujo y la lógica de una partida entre jugadores
 * @author ASUS
 * @version 1.0
 * @since 01/07/2020
 */
public class Partida 
extends Thread
{
    public int id;
    public Jugador J1;
    public Jugador J2;
    public int puntuacionJ1;
    public int puntuacionJ2;
    public String opcionJ1;
    public String opcionJ2;
    public int rondas;
    public int tiempoRonda;
    public int confirmacionMensaje;

     /**
     * Método para llamar al constructor de partirda y añadir una partida nueva
     * a la lista
     * @param J1 Jugador
     * @param J2 Jugador
     * @param id int con el identificador de la partida
     * @return un objeto de tipo Partida
     */
    public static Partida nPartida (Jugador J1, Jugador J2,int id){
        Partida P = new Partida(J1,J2,id);
        Servidor.partidasIniciadas.add(P);
        P.start();
        return P;
    }
    
     /**
     * El contructor de Partida inicia un hilo
     */
    public Partida (Jugador J1, Jugador J2,int id) {
        this.id=id;
        this.J1 = J1;
        this.J2 = J2;
        this.puntuacionJ1 = 0;
        this.puntuacionJ2 = 0;
        this.rondas = 5;
        this.tiempoRonda = 10;
        this.confirmacionMensaje = 0;
        this.opcionJ1="NADA";
        this.opcionJ2="NADA";
    }

    public int getIdent() {
        return id;
    }
    
    public Jugador getJ1() {
        return J1;
    }

    public Jugador getJ2() {
        return J2;
    }

    public int getPuntuacionJ1() {
        return puntuacionJ1;
    }

    public void setJ1(Jugador J1) {
        this.J1 = J1;
    }

    public void setJ2(Jugador J2) {
        this.J2 = J2;
    }

    public int getPuntuacionJ2() {
        return puntuacionJ2;
    }

    public int getRondas() {
        return rondas;
    }

    public void setRondas(int rondas) {
        this.rondas = rondas;
    }
    
    public void setConfirmacionMensaje(int confirmacionMensaje) {
        this.confirmacionMensaje = confirmacionMensaje;
    }

    public int getConfirmacionMensaje() {
        return confirmacionMensaje;
    }
    
    
    public int getTiempoRonda() {
        return tiempoRonda;
    }


    public void setPuntuacionJ1(int puntuacionJ1) {
        this.puntuacionJ1 = puntuacionJ1;
    }

    public void setPuntuacionJ2(int puntuacionJ2) {
        this.puntuacionJ2 = puntuacionJ2;
    }

    public void run()
    {
        /**
        * Flujo de rondas de la partida
        */
        this.rondas++;
        do{
            Reloj R1 = new Reloj();
            R1.run(10, id);
            do {
                System.out.println("Sin jugadas");
            } while (this.confirmacionMensaje < 2);
            R1.stop();
            int ganador= determinaGanador();
            asignaPuntuacion(ganador);
            opcionJ1="NADA";
            opcionJ2="NADA";
            this.rondas--;
            confirmacionMensaje=0;
            String mensaje1;
            String mensaje2;
            if (rondas!=1) {
                mensaje1=Jugador.mensaje ( id, rondas,puntuacionJ1, puntuacionJ2);
                mensaje2=Jugador.mensaje ( id, rondas,puntuacionJ2, puntuacionJ1);
                int punt;
                if(puntuacionJ1>puntuacionJ2) {
                    punt=J1.getPartidasGanadas()+1;
                    J1.setPartidasGanadas(punt);
                } else if (puntuacionJ1<puntuacionJ2) {
                    punt=J2.getPartidasGanadas()+1;
                    J2.setPartidasGanadas(punt);
                }
            } else {
                mensaje1=Jugador.mensaje ( id, puntuacionJ1, puntuacionJ2);
                mensaje2=Jugador.mensaje ( id, puntuacionJ2, puntuacionJ1);
            }
            J1.sendMessage(mensaje1);
            J2.sendMessage(mensaje2);
        }while(rondas>0);
        
    }
     /**
     * Método para determinar el ganador de una ronda
     * @return un int que identifica quien ha ganado la ronda
     */
    public int determinaGanador(){
        int ganador;
        if (opcionJ1.equals("NADA") || opcionJ2.equals("NADA")){ 
            
            if (opcionJ1.equals("NADA") && opcionJ2.equals("NADA")){
             ganador =0; //nadie gana
            }else if (opcionJ1.equals("NADA")) {
                ganador =2;
            } else if (opcionJ2.equals("NADA")) {
                ganador =1;
            } else {
                ganador =0;
            }
        } else if (opcionJ1.equals(opcionJ2)){
            ganador=3; //empate
        }else {
            ganador=Juego.ganador(opcionJ1,opcionJ2);
        }
        return ganador;
    }
     /**
     * Método para asignar la puntuación a los jugadores
     * @param ganador int que indica quien ganó la ronda
     * @return una variable boolean que indica si se han asignado puntuaciones
     */
    public boolean asignaPuntuacion(int ganador){
        boolean result=false;
        if (ganador==1){
            J1.setRondasGanadas(J1.getRondasGanadas()+1);
            this.puntuacionJ1=puntuacionJ1+1;
            result=true;
        } else if(ganador==2){
            J2.setRondasGanadas(J2.getRondasGanadas()+1);
            this.puntuacionJ2=puntuacionJ2+1;
            result=true;
        }else if(ganador==3){ //empate
            J1.setRondasGanadas(J1.getRondasGanadas()+1);
            this.puntuacionJ1=puntuacionJ1+1;
            J2.setRondasGanadas(J2.getRondasGanadas()+1);
            this.puntuacionJ2=puntuacionJ2+1;
            result=true;
        }else if(ganador==0){ //empate pero sin respuestas
            result=true;
        }
        return result;
    }
    
     /**
     * Método que compara un identificador de partida
     * @param id2 int con el identificador de la partida
     * @return una variable booleana que india si es igual o no
     */
    public boolean equals(int id2) {
        boolean t=false;
        if (Integer.toString(this.id).equals(Integer.toString(id2))){
            t=true;
        }
        return t;
    }
}


