package com.example.proyecto_1_datos.controlador;

import com.example.proyecto_1_datos.modelo.Lista_DE_Imagenes;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

public class ControlJuego {

    private Vector<String> numero;
    private Vector<Integer> matrizPosicionImagenes;
    private int filas, columnas;
    private int fichas;
    private Random aleatorio;
    private int random;

    private static ControlJuego controlJuego;

    public static ControlJuego getObjeto_ControlJuego(){
        if(controlJuego == null){
            controlJuego = new ControlJuego();
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

    // Retorna un numero del 1 al 15 contenidos en "numero" y elimina este elemento del vetor.
    private int randomNumero(){
        int retorno;
        random = aleatorio.nextInt(fichas);
        retorno = Integer.parseInt(numero.elementAt(random));
        numero.removeElementAt(random);
        fichas-=1;
        return retorno;
    }

    public Vector<Integer> getMatrizPosicionImagenes(){
        return matrizPosicionImagenes;
    }
}
