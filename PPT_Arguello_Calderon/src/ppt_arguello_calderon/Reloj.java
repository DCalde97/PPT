/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppt_arguello_calderon;

import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Clase encargada de la gestión de tiempos durante la ejecución del programa
 * @author ASUS
 * @version 1.0
 * @since 01/07/2020
 */
public class Reloj
    extends Thread
    implements Runnable {

    public Reloj() {
    }
   
    public void run(int tiempo, int identificador)
    {
        temporizador(tiempo);
        Partida P1 = Servidor.buscarPartida(identificador);
        P1.setConfirmacionMensaje(2);
    }
    /**
     * Método que detiene la ejecución de un hilo por el tiempo establecido
     * @param tiempo de tipo int que indica los segundos que se detiene la ejecución
     */
    public static void temporizador(int tiempo){
        try {
            Thread.sleep(tiempo*1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Reloj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
