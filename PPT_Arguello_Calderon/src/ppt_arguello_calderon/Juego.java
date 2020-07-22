/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppt_arguello_calderon;

/**
* Clase que se encarga de determinar el ganador de una ronda
* @author Danip
* @version 1.0
* @since 01/07/2020
*/ 
public enum Juego {
    Piedra,
    Papel,
    Tijera;
   
     /**
     * Método que determina el ganador de una ronda
     * @param yo String del jugador1
     * @param adversario String del jugador2
     * @return int con el numero que indica el ganador
     */
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

