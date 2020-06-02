/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppt_arguello_calderon;

/**
 *
 * @author Danip
 */
public enum Juego {
    Piedra,
    Papel,
    Tijera;

    
    public static int ganador(Juego yo,Juego adversario) {
        int ganador=0;
        if (yo.equals(adversario)){
            ganador=0;
        } else if(yo.equals(Juego.Piedra)){
            if (yo.equals(Juego.Tijera)){
                ganador = 1;
            } else {
                ganador = 2;
            }
        } else if(yo.equals(Juego.Papel)){
            if (yo.equals(Juego.Piedra)){
                ganador = 1;
            } else {
                ganador = 2;
            }
        } else if(yo.equals(Juego.Tijera)){
            if (yo.equals(Juego.Papel)){
                ganador = 1;
            } else {
                ganador = 2;
            }
        }
        return ganador;
    }
    
    
}

