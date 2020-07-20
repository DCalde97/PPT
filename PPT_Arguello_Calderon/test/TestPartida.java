

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.Socket;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ppt_arguello_calderon.Jugador;
import ppt_arguello_calderon.Partida;

/**
 *
 * @author Danip
 */
public class TestPartida {
    
    public TestPartida() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void determinaGanadorUT_01(){
        boolean resultado;
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        int resu=P1.determinaGanador("Piedra","Piedra");
        if (resu==3){
            resultado = true;
        } else {
            resultado = false;
        }
        assert(resultado);
    }
    
    @Test
    public void determinaGanadorUT_02(){
        boolean resultado;
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        int resu=P1.determinaGanador("Piedra","Papel");
        if (resu==2){
            resultado = true;
        } else {
            resultado = false;
        }
        assert(resultado);
    }
    
    @Test
    public void determinaGanadorUT_03(){
        boolean resultado;
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        int resu=P1.determinaGanador("Piedra","Tijera");
        if (resu==1){
            resultado = true;
        } else {
            resultado = false;
        }
        assert(resultado);
    }
    
    @Test
    public void determinaGanadorUT_04(){
        boolean resultado;
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        int resu=P1.determinaGanador("Piedra",null);
        if (resu==1){
            resultado = true;
        } else {
            resultado = false;
        }
        assert(resultado);
    }
    
    @Test
    public void determinaGanadorUT_05(){
        boolean resultado;
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        int resu=P1.determinaGanador("Papel","Piedra");
        if (resu==1){
            resultado = true;
        } else {
            resultado = false;
        }
        assert(resultado);
    }
    
    @Test
    public void determinaGanadorUT_06(){
        boolean resultado;
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        int resu=P1.determinaGanador("Papel","Papel");
        if (resu==3){
            resultado = true;
        } else {
            resultado = false;
        }
        assert(resultado);
    }
    
    @Test
    public void determinaGanadorUT_07(){
        boolean resultado;
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        int resu=P1.determinaGanador("Papel","Tijera");
        if (resu==2){
            resultado = true;
        } else {
            resultado = false;
        }
        assert(resultado);
    }
    
    @Test
    public void determinaGanadorUT_08(){
        boolean resultado;
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        int resu=P1.determinaGanador("Papel",null);
        if (resu==1){
            resultado = true;
        } else {
            resultado = false;
        }
        assert(resultado);
    }
    
    @Test
    public void determinaGanadorUT_09(){
        boolean resultado;
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        int resu=P1.determinaGanador("Tijera","Piedra");
        if (resu==2){
            resultado = true;
        } else {
            resultado = false;
        }
        assert(resultado);
    }
    
    @Test
    public void determinaGanadorUT_10(){
        boolean resultado;
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        int resu=P1.determinaGanador("Tijera","Papel");
        if (resu==1){
            resultado = true;
        } else {
            resultado = false;
        }
        assert(resultado);
    }
    
    @Test
    public void determinaGanadorUT_11(){
        boolean resultado;
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        int resu=P1.determinaGanador("Tijera","Tijera");
        if (resu==3){
            resultado = true;
        } else {
            resultado = false;
        }
        assert(resultado);
    }
    
    @Test
    public void determinaGanadorUT_12(){
        boolean resultado;
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        int resu=P1.determinaGanador("Tijera",null);
        if (resu==1){
            resultado = true;
        } else {
            resultado = false;
        }
        assert(resultado);
    }
    
    @Test
    public void determinaGanadorUT_13(){
        boolean resultado;
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        int resu=P1.determinaGanador(null,"Piedra");
        if (resu==2){
            resultado = true;
        } else {
            resultado = false;
        }
        assert(resultado);
    }
    
    @Test
    public void determinaGanadorUT_14(){
        boolean resultado;
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        int resu=P1.determinaGanador(null,"Papel");
        if (resu==2){
            resultado = true;
        } else {
            resultado = false;
        }
        assert(resultado);
    }
    
    @Test
    public void determinaGanadorUT_15(){
        boolean resultado;
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        int resu=P1.determinaGanador(null,"Tijera");
        if (resu==2){
            resultado = true;
        } else {
            resultado = false;
        }
        assert(resultado);
    }
    
    @Test
    public void determinaGanadorUT_16(){
        boolean resultado;
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        int resu=P1.determinaGanador(null,null);
        if (resu==0){
            resultado = true;
        } else {
            resultado = false;
        }
        assert(resultado);
    }
    
    public void asignaPuntuacionUT_01(){
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        assert(P1.asignaPuntuacion(1));
    }
    
    public void asignaPuntuacionUT_02(){
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        assert(P1.asignaPuntuacion(2));
    }
    
    public void asignaPuntuacionUT_03(){
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        assert(P1.asignaPuntuacion(3));
    }
    
    public void asignaPuntuacionUT_04(){
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        assert(P1.asignaPuntuacion(0));
    }
    
    public void asignaPuntuacionUT_05(){
        Socket S1=null;
        Jugador J1=Jugador.newJugador(S1);
        Jugador J2=Jugador.newJugador(S1);
        Partida P1 =new Partida(J1,J2);
        assert(P1.asignaPuntuacion(4));
    }
}
