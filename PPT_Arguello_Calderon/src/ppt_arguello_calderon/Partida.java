package ppt_arguello_calderon;

/**
 *
 * @author ASUS
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
        do{
            //Reloj R1 = new Reloj();

            do {
                System.out.println("No Entro");
            } while (this.confirmacionMensaje < 2);
            System.out.println(opcionJ1+opcionJ2);
            int ganador= determinaGanador(opcionJ1, opcionJ2);
            asignaPuntuacion(ganador);
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
             System.out.println("ganador 0 null");
            }else if (OP1.equals(null)) {
                ganador =2;
            } else if (OP2.equals(null)) {
                ganador =1;
            } else {
                ganador =0;
                System.out.println("ganador 0 no se reconoce respuestas");
            }
        } else if (OP1.equals(OP2)){
            ganador=3; //empate
        }else {
            //Juego op1 = transformar(OP1);
            //Juego op2 = transformar(OP2);
            ganador=Juego.ganador(OP1,OP2);
        }
        return ganador;
    }
    
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
    
    public boolean equals(int id2) {
        boolean t=false;
        if (Integer.toString(this.id).equals(Integer.toString(id2))){
            t=true;
        }
        return t;
    }
}


