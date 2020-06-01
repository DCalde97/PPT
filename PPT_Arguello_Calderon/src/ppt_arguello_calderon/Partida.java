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
      try
      {
        //Hay que hacer la lógica de la partida
        //Enviar petición de nueva ronda (con su opcion)a los Jugadores, enviando puntiaciones
        // y el numero de rondas
          //Recibir la eleccion de los jugadores
          //Interpretar esas elecciones con la logica del juego
          //Volver a empezar una nueva peticion, hasta que se acaben las rondas
          
          //Registro de puntuaciones en in fichero resultados
          //Fin 
          
         
      } catch (InterruptedException ex)
      {
      
      }
    }
    //Cambiar Estado de ese atributo.
  }
    
    
    
}


