/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static org.junit.Assert.fail;
import org.junit.Test;
import ppt_arguello_calderon.Jugador;
import ppt_arguello_calderon.Partida;

import ppt_arguello_calderon.Servidor;
/**
 * Clase encargada de testear los metodos publicos del servidor
 * @author Danip
 * @since 01/07/2020
 * @version 1.0
 */
public class TestServidor {
    @Test
    public void generarIdPartidaUT_01(){
        int resultEsperado=0;
        int result=Servidor.generarIdPartida();
        if(result!=resultEsperado){
            fail("Test Fallido");
        }
    }

    //si ya existe otro usuario conectado con el mismo nick

    public void generarIdNickUT_01(){
        String resultEsperado="Pepe";
        String result=Servidor.generarIdNick(resultEsperado);
        if(result.equals(resultEsperado)){
            fail("Test Fallido");
        }
    }

    //si no existe otro usuario con el mismo nick

    public void generarIdNickUT_02(){
        String resultEsperado="Luis";
        String result=Servidor.generarIdNick(resultEsperado);
        if(!(result.equals(resultEsperado))){
            fail("Test Fallido");
        }
    }

    public void RetransmitirUT_01(){
        Servidor.Retransmitir("@NICKS@1@");
    }

    public void buscarJugadorUT_01(){
        String nick="Pepe";
        Jugador J1=Servidor.buscarJugador(nick);
        if(!nick.equals(J1.getNick())){
            fail("Test Fallido");
        }
    }

    public void buscarPartidaUT_01(){
        int id=0;
        Partida J1=Servidor.buscarPartida(id);
        if(id!=J1.getId()){
            fail("Test Fallido");
        }
    }


}