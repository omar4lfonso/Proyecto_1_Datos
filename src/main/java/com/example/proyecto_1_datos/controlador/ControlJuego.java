package com.example.proyecto_1_datos.controlador;

import com.example.proyecto_1_datos.modelo.Lista_DE_Imagenes;
import com.example.proyecto_1_datos.modelo.NombresUsuarios;

import java.io.Serializable;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class ControlJuego implements Serializable {
    // Vector de interés: contiene la posición aleatoria de las cartas.
    private Vector<Integer> matrizPosicionImagenes;
    private boolean turnoJugador; // False -> Jugador 1, True -> Jugador 2
    private int puntajeJugador1;
    private int puntajeJugador2;

    // Variables secundarias para uso interno
    private Vector<String> numero;
    private int filas, columnas;
    private int fichas;
    private Random aleatorio;

    public static boolean TURNO_JUGADOR1 = false;
    public static boolean TURNO_JUGADOR2 = true;

    // Mantenga este como un objeto único
    private static ControlJuego controlJuego;

    public static ControlJuego getObjeto_ControlJuego(){
        if(controlJuego == null){
            controlJuego = new ControlJuego();
        }
        return controlJuego;
    }

    public static ControlJuego getObjeto_ControlJuego(ControlJuego controlJuegoIn){
        if(controlJuego == null){
            controlJuego = new ControlJuego(controlJuegoIn);
        }
        return controlJuego;
    }

    // Se inicializan los botones y algunas variables
    private ControlJuego(){
        Lista_DE_Imagenes lista_de_imagenes = Lista_DE_Imagenes.getObjeto_Lista_DE_Imagenes();
        /*botones = new Botones[FILAS*COLUMNAS];*/
        aleatorio = new Random();
        switch (lista_de_imagenes.getTamañoTablero()){
            case T3X5:
                filas = 3;
                columnas = 5;
                break;
            case T5X5:
                filas = 5;
                columnas = 5;
                break;
            default:
                filas = 3;
                columnas = 3;
        }

        fichas = filas*columnas;
        numero = new Vector<String>();
        matrizPosicionImagenes = new Vector<Integer>();
        prepararRandom();
        crearMatrizImagenes();
        this.turnoJugador = getRandomBoolean();
        this.puntajeJugador1 = 0;
        this.puntajeJugador2 = 0;
    }

    private ControlJuego(ControlJuego controlJuegoIn){
        this.matrizPosicionImagenes = new Vector<Integer>(controlJuegoIn.getMatrizPosicionImagenes().size());
        //Collections.copy(this.matrizPosicionImagenes, controlJuegoIn.getMatrizPosicionImagenes());
        this.matrizPosicionImagenes = controlJuegoIn.getMatrizPosicionImagenes();
        this.turnoJugador = controlJuegoIn.getTurnoJugador();
        this.puntajeJugador1 = controlJuegoIn.getPuntajeJugador(NombresUsuarios.NumJugador.JUGADOR1);
        this.puntajeJugador2 = controlJuegoIn.getPuntajeJugador(NombresUsuarios.NumJugador.JUGADOR2);
    }

    // LLena el vector "numero" con parejas del 1 a 15
    private void prepararRandom(){
        int i;
        for(i=0;i<((filas*columnas-1)/2);i++){
            numero.addElement (String.valueOf(i+2));
            numero.addElement (String.valueOf(i+2));
        }
        numero.addElement (String.valueOf(1));
    }

    private void crearMatrizImagenes(){
        for(int i=0;i<filas*columnas;i++){
            matrizPosicionImagenes.add(randomNumero());
        }
    }

    // Retorna un número del 1 al 15 contenidos en "número" y elimina este elemento del vector.
    private int randomNumero(){
        int retorno;
        int random = aleatorio.nextInt(fichas);
        retorno = Integer.parseInt(numero.elementAt(random));
        numero.removeElementAt(random);
        fichas-=1;
        return retorno;
    }

    public Vector<Integer> getMatrizPosicionImagenes(){
        return matrizPosicionImagenes;
    }

    // *******************Métodos para la selección del turno del jugador desde el Servidor.***************************
    private boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }

    /**
     * @return turnoJugador
     */
    public boolean getTurnoJugador(){
        return turnoJugador;
    }

    /**
     * Invertir el turno de Jugador
     * @param turnoJugador
     */
    public void toggleTurnoJugador(boolean turnoJugador){
        this.turnoJugador = turnoJugador ? TURNO_JUGADOR2 : TURNO_JUGADOR1;
    }

    // *******************Métodos para la selección del turno del jugador desde el Cliente.***************************
    public void setTurnoJugador(boolean turnoJugador){
        this.turnoJugador = turnoJugador;
    }

    // *******************Métodos que operan sobre puntajes desde el Servidor:**********************

    /**
     * Este método asigna el puntaje ganado en la partida
     * @param puntajeGanado
     */
    public void addPuntajeJugador(int puntajeGanado){
        if(turnoJugador){
            puntajeJugador2 += puntajeGanado;
        }
        else{
            puntajeJugador1 += puntajeGanado;
        }
    }

    // *******************Métodos que operan sobre puntajes desde el Cliente:**********************

    public void setPuntajeJugador(NombresUsuarios.NumJugador jugador, int puntaje){
        if(jugador == NombresUsuarios.NumJugador.JUGADOR1){
            puntajeJugador1 = puntaje;
        }
        else{
            puntajeJugador2 = puntaje;
        }
    }

    public int getPuntajeJugador(NombresUsuarios.NumJugador jugador){
        return jugador == NombresUsuarios.NumJugador.JUGADOR1 ? this.puntajeJugador1 : this.puntajeJugador2;
    }
}
