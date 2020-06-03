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
                //paso de mensajes de los jugadores con el server
                //cuando puntuacion jugador =3 fin o nº rondas =5 fin
                //comprar las elecciones y decidir ganador aumentando puntuacion



              //Hay que hacer la lógica de la partida 
              //Enviar petición de nueva ronda (con su opcion)a los Jugadores, enviando puntiaciones
              // y el numero de rondas
              //Recibir la eleccion de los jugadores
              //Interpretar esas elecciones con la logica del juego
              //Volver a empezar una nueva peticion, hasta que se acaben las rondas

              //Registro de puntuaciones en in fichero resultados
              //Fin



             //V 2.0 covid


        }
    }
        //Cambiar Estado de ese atributo.
    
    //refactorizar mu largo
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
        if (opcionJ1.equals(null) || opcionJ2.equals(null)){
            if (opcionJ1.equals(null)) {
                ganador =2;
            } else if (opcionJ2.equals(null)) {
                ganador =1;
            } else {
                ganador =0;
            }
        } else {
            Juego op1 = transformar(opcionJ1);
            Juego op2 = transformar(opcionJ2);
            ganador=Juego.ganador(op1,op2);
        }
        J1.setRondasGanadas(J1.getRondasGanadas()+1);
        setPuntuacionJ1(getPuntuacionJ1()+1);
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
