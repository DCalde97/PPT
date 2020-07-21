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

    
    public static int ganador(String yo,String adversario) {
        int ganador=0;
        if (yo.equals(adversario)){
            ganador=3;
        } else if(yo.equals("Piedra")){
            if (adversario.equals("Tijera")){
                ganador = 1;
            } else {
                ganador = 2;
            }
        } else if(yo.equals("Papel")){
            if (adversario.equals("Piedra")){
                ganador = 1;
            } else {
                ganador = 2;
            }
        } else if(yo.equals("Tijera")){
            if (adversario.equals("Papel")){
                ganador = 1;
            } else {
                ganador = 2;
            }
        }
        return ganador;
    }
    
    
}

