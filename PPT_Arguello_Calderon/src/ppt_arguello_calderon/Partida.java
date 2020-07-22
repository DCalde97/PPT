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
        this.opcionJ1=null;
        this.opcionJ2=null;
        this.start();
        
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
        
        do{
            /**
             * Flujo de rondas de la partida
             */
            do {
                System.out.println("No Entro");
            } while (this.confirmacionMensaje < 2);
            System.out.println(opcionJ1+opcionJ2);
            int ganador= determinaGanador();
            asignaPuntuacion(ganador);
            opcionJ1=null;
            opcionJ2=null;
            System.out.println("J1:"+puntuacionJ1);
            System.out.println("J2:"+puntuacionJ1);
            this.rondas--;
            confirmacionMensaje=0;
            String mensaje;
            mensaje=Jugador.mensaje ( id, rondas,puntuacionJ1,puntuacionJ2);
            J1.sendMessage(mensaje);
            mensaje=Jugador.mensaje ( id, rondas,puntuacionJ2,puntuacionJ1);
            J2.sendMessage(mensaje);
        }while(rondas>0);
        
    }
    
     /**
     * Método para determinar el ganador de una ronda
     * @return un int que identifica quien ha ganado la ronda
     */
    public int determinaGanador(){
        int ganador;
        if (opcionJ1.isEmpty() || opcionJ2.isEmpty()){ 
            
            if (opcionJ1.isEmpty() && opcionJ2.isEmpty()){
             ganador =0; //nadie gana
             System.out.println("ganador 0 null");
            }else if (opcionJ1.isEmpty()) {
                ganador =2;
            } else if (opcionJ2.isEmpty()) {
                ganador =1;
            } else {
                ganador =0;
                System.out.println("ganador 0 no se reconoce respuestas");
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
        }else if(ganador==0){
            System.out.println("ganador 0");
            this.puntuacionJ1=puntuacionJ1+20;
            this.puntuacionJ2=puntuacionJ2+20;
            result=true;
        }
        return result;
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


