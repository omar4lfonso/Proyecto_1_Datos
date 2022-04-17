package com.example.proyecto_1_datos.modelo;

public class NombresUsuarios {

    public enum NumJugador{
        JUGADOR1, JUGADOR2
    }
    public static boolean TURNO_JUGADOR1 = false;
    public static boolean TURNO_JUGADOR2 = true;

    private String jugador1;
    private String jugador2;
    private int puntajeJugador1;
    private int puntajeJugador2;
    private boolean turnoJugador; // False -> Jugador 1, True -> Jugador 2

    public NombresUsuarios(String jugador1, String jugador2){
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
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

    public boolean getTurnoJugador(){
        return turnoJugador;
    }

    public void setTurnoJugador(boolean turnoJugador){
        this.turnoJugador = turnoJugador ? TURNO_JUGADOR1 : TURNO_JUGADOR2;
    }
}
