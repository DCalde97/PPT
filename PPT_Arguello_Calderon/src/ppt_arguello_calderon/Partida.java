package ppt_arguello_calderon;

/**
 *
 * @author ASUS
 */
public class Partida 
extends Thread
{
    private Jugador J1;
    private Jugador J2;
    private int puntuacionJ1;
    private int puntuacionJ2;
    private int rondas;
    private int tiempoRonda;

    public Partida(Jugador J1, Jugador J2) {
        this.J1 = J1;
        this.J2 = J2;
        this.puntuacionJ1 = 0;
        this.puntuacionJ2 = 0;
        this.rondas = 5;
        this.tiempoRonda = 10;
        this.start();
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

    public int getPuntuacionJ2() {
        return puntuacionJ2;
    }

    public int getRondas() {
        return rondas;
    }

    public void setRondas(int rondas) {
        this.rondas = rondas;
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
        int numEjecuciones = 0;
        while (true)
        {   
            while(this.rondas>0 && this.puntuacionJ1<3 && this.puntuacionJ2<3) {
                ronda(this.J1,this.J2);
                this.rondas--;
            }
        }
    }
    
    public void ronda(Jugador J1, Jugador J2) {
        int ganador;
        String opcionJ1;
        String opcionJ2;
        //mandar mensajes a J1
        J1.sendMessage("IncioRonda");
        J2.sendMessage("IncioRonda");
        opcionJ1 = J1.reciveMensage();
        opcionJ2 = J2.reciveMensage();
        ganador=determinaGanador(opcionJ1, opcionJ2);
        asignaPuntuacion(ganador);
    }
    
    public int determinaGanador(String OP1, String OP2){
        int ganador;
        if (OP1.equals(null) || OP2.equals(null)){ 
            
            if (OP1.equals(null) && OP2.equals(null)){
             ganador =0; //nadie gana
            }else if (OP1.equals(null)) {
                ganador =2;
            } else if (OP2.equals(null)) {
                ganador =1;
            } else {
                ganador =0;
            }
        } else if (OP1.equals(OP2)){
            ganador=3; //empate
        }else {
            Juego op1 = transformar(OP1);
            Juego op2 = transformar(OP2);
            ganador=Juego.ganador(op1,op2);
        }
        return ganador;
    }
    
    public boolean asignaPuntuacion(int ganador){
        boolean result=false;
        if (ganador==1){
            J1.setRondasGanadas(J1.getRondasGanadas()+1);
            this.puntuacionJ1++;
            result=true;
        } else if(ganador==2){
            J2.setRondasGanadas(J2.getRondasGanadas()+1);
            this.puntuacionJ2++;
            result=true;
        }else if(ganador==3){ //empate
            J1.setRondasGanadas(J1.getRondasGanadas()+1);
            this.puntuacionJ1++;
            J2.setRondasGanadas(J2.getRondasGanadas()+1);
            this.puntuacionJ2++;
            result=true;
        }else if(ganador==0){
            result=true;
        }
        return result;
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


