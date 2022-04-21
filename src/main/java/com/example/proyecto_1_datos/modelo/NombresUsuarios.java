package com.example.proyecto_1_datos.modelo;

import java.io.Serializable;
import java.util.Random;

public class NombresUsuarios implements Serializable {

    private static NombresUsuarios nombresUsuarios;

    public enum NumJugador{
        JUGADOR1, JUGADOR2
    }

    private FormatoMensajes.FORMATO_MSJ tipo;
    private String jugador1;
    private String jugador2;
    private int puntajeJugador1;
    private int puntajeJugador2;
    public enum TABLERO_SIZE{
        T3X3,
        T3X5,
        T5X5
    }
    private TABLERO_SIZE tamañoTablero;

    private NombresUsuarios(String jugador1, String jugador2,
                           TABLERO_SIZE tamañoTablero){
        this.tipo = FormatoMensajes.FORMATO_MSJ.NOMBRES_USUARIOS;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.tamañoTablero = tamañoTablero;
    }

    public static NombresUsuarios getNombresUsuarios(String jugador1, String jugador2,
                                                      TABLERO_SIZE tamañoTablero){
        if(nombresUsuarios == null){
            nombresUsuarios = new NombresUsuarios(jugador1, jugador2,
            tamañoTablero);
        }
        return nombresUsuarios;
    }

    public void eliminarSingletonNombresUsuarios(){
        nombresUsuarios = null;
    }


    public FormatoMensajes.FORMATO_MSJ obtenerTipo() {
        return tipo;
    }

    public String getNombreJugador(NumJugador numJugador){
        return (numJugador == NumJugador.JUGADOR1) ? jugador1 :
                (numJugador == NumJugador.JUGADOR2) ? jugador2: "";
    }
    public int getPuntajeJugador(NumJugador numJugador){
        return (numJugador == NumJugador.JUGADOR1) ? puntajeJugador1 :
                (numJugador == NumJugador.JUGADOR2) ? puntajeJugador2: 0;
    }
    public boolean setPuntajeJugador(NumJugador numJugador, int puntaje){
        if(numJugador == NumJugador.JUGADOR1){
            puntajeJugador1 = puntaje;
        }
        else if(numJugador == NumJugador.JUGADOR2){
            puntajeJugador2 = puntaje;
        }
        else {
            return false;
        }
        return true;
    }

    public TABLERO_SIZE getTamañoTablero(){
        return tamañoTablero;
    }
}
