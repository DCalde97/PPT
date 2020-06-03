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
    public void ronda(Jugador J1, Jugador J2) {
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
        if (ganador==1){
            J1.setRondasGanadas(J1.getRondasGanadas()+1);
            this.puntuacionJ1++;
        } else if(ganador==2){
            J2.setRondasGanadas(J2.getRondasGanadas()+1);
            this.puntuacionJ1++;
        }else{
            Ganador = null;
        }
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


