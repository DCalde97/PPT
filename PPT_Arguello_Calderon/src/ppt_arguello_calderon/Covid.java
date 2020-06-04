/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppt_arguello_calderon;
/**
 *
 * @author ASUS
 */
public class Covid 
extends Partida{
    
    public Covid (Jugador J1, Jugador J2){    
        super(J1,J2);
        this.start();     
    }
    
    public void run()
      {
        int numEjecuciones = 0;
        while (true)
        {   
            while(getRondas()>0 && getPuntuacionJ1()<3 && getPuntuacionJ2()<3) {
                
                if (getRondas()>3) {
                    rondaCovid(getJ1(),getJ2());
                }else{
                    ronda(getJ1(),getJ2());
                }
                setRondas(getRondas()-1);
            }
        }
    }
        
    
    private void rondaCovid(Jugador J1, Jugador J2) {
        int ganador;
        Jugador Ganador=null;
        String opcionJ1;
        String opcionJ2;
        //mandar mensajes a J1
        J1.sendMessage("IncioRonda");
        J2.sendMessage("IncioRonda");
        opcionJ1 = J1.reciveMensage();
        opcionJ2 = J2.reciveMensage();
        
        J1.setRondasGanadas(J1.getRondasGanadas()+1);
        setPuntuacionJ1(getPuntuacionJ1()+1);
        //se puede mandar mensajes de info cuando suban puntos y cambien rondas
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
