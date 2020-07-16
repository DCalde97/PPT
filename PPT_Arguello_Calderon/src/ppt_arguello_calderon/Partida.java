package ppt_arguello_calderon;

/**
 *
 * @author ASUS
 */
public class Partida 
extends Thread
{
    private int id;
    public Jugador J1;
    public Jugador J2;
    private int puntuacionJ1;
    private int puntuacionJ2;
    private int rondas;
    private int tiempoRonda;
    public int confirmacionMensaje;

    public static Partida nPartida (Jugador J1, Jugador J2,int id){
        Partida P = new Partida(J1,J2,id);
        Servidor.partidasIniciadas.add(P);
        return P;
    }
    
    public Partida (Jugador J1, Jugador J2,int id) {
        this.id=id;
        this.J1 = J1;
        this.J2 = J2;
        this.puntuacionJ1 = 0;
        this.puntuacionJ2 = 0;
        this.rondas = 5;
        this.tiempoRonda = 10;
        this.confirmacionMensaje = 0;
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
        /*int numEjecuciones = 0;
        while (true)
        {   
            while(this.rondas>0 && this.puntuacionJ1<3 && this.puntuacionJ2<3) {
                ronda(this.J1,this.J2);
                this.rondas--;
            }
        }*/
        
        Reloj R1 = new Reloj();
        
        do {            
           
        } while (this.confirmacionMensaje < 2);
        
        int ganador= determinaGanador(J1.getOpcion(), J2.getOpcion());
        asignaPuntuacion(ganador);
        setRondas(getRondas()-1);
        confirmacionMensaje=0;
    }
    
    /*public void ronda(Jugador J1, Jugador J2) {
        int ganador;
        String opcionJ1;
        String opcionJ2;
        //mandar mensajes a J1
        
        J1.sendMessage("IncioRonda");//partida idPartida ronda puntJ1 puntJ2 
        J2.sendMessage("IncioRonda");//partida idPartida ronda puntJ2 puntJ1
        opcionJ1 = J1.reciveMensage();
        opcionJ2 = J2.reciveMensage();
        ganador=determinaGanador(opcionJ1, opcionJ2);
        asignaPuntuacion(ganador);
}*/ 
    
    
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


