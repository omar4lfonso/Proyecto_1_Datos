package com.example.proyecto_1_datos.listaEnlazada;

import java.io.Serializable;

/**
 * Clase Nodo: es una forma genérica que define los componentes y métodos básicos de los nodos
 * en una lista doblemente enlazada
 */
public class Nodo <T> implements Serializable {

    private T valor;
    private Nodo<T> siguiente;
    private Nodo<T> anterior;

    /**
     * Constructor de la clase Nodo cuando ya existe una lista
     * @param valor: valor de tipo T (genérico)
     * @param siguiente: nodo siguiente
     * @param anterior: nodo anterior
     */
    public Nodo(T valor, Nodo siguiente, Nodo anterior){
        this.valor = valor;
        this.anterior = anterior;
        this.siguiente = siguiente;
    }

    /**
     * Constructor de la clase Nodo cuando no existe una lista
     * @param valor: valor de tipo T (genérico)
     */
    public Nodo(T valor){
        this.valor = valor;
        this.siguiente = null;
        this.anterior = null;
    }

    /**
     * Método para obtener el valor del nodo actual
     */
    public T getValor(){
        return valor;
    }

    /**
     * Método para cambiar el valor del nodo actual
     */
    public void setValor(T valor){
        this.valor = valor;
    }

    /**
     * Método para obtener el puntero al nodo siguiente
     */
    public Nodo<T> getSiguiente(){
        return siguiente;
    }

    /**
     * Método para cambiar el valor del nodo siguiente
     */
    public void setSiguiente(Nodo newSiguiente){
        this.siguiente = newSiguiente;
    }

    /**
     * Método para obtener el puntero del nodo anterior
     */
    public Nodo<T> getAnterior(){
        return anterior;
    }

    /**
     * Método para cambiar el valor del nodo anterior
     */
    public void setAnterior(Nodo newAnterior){
        this.anterior = newAnterior;
    }

}
