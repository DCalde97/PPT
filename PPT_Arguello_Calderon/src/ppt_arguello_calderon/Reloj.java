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
 *
 * @author ASUS
 */
public class Reloj
    extends Thread
    implements Runnable {

    public Reloj() {
        this.start();
    }
   
    public void run(int tiempo, int identificador)
    {
        temporizador(tiempo);
        Partida P1 = Servidor.buscarPartida(identificador);
        P1.setConfirmacionMensaje(2);
    }
    //Se recibe el tiempo en segundos, por eso se multiplica por 1000(ms)
    public static void temporizador(int tiempo){
        try {
            Thread.sleep(tiempo*1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Reloj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
