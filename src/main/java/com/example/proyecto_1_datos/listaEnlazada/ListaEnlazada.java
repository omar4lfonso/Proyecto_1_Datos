package com.example.proyecto_1_datos.listaEnlazada;

/**
 * Clase de lista doblemente enlazada genérica
 * @param <T> T: es el tipo de datos que se usan para el formato de las imágenes
 */
public class ListaEnlazada <T>{

    private Nodo<T> cabeza;
    private Nodo<T> ultimo;
    private int size;

    /**
     * Constructor de la lista doblemente enlazada
     */
    public ListaEnlazada(){
        cabeza = null;
        ultimo = null;
        size = 0;
    }

    /**
     * Método para agregar un nodo al inicio de la lista doblemente enlazada
     * @param valor valor de tipo genérico T
     */
    public void agregarInicio(T valor){
        if(cabeza != null){
            cabeza = new Nodo(valor, cabeza, null);
            cabeza.getSiguiente().setAnterior(cabeza);
        }
        else {
            cabeza = new Nodo(valor);
            ultimo = cabeza;
        }
        size++;
    }

    /**
     * Método para agregar un nodo al final de la lista doblemente enlazada
     * @param valor valor de tipo genérico T
     */
    public void agregarFinal(T valor){
        if (cabeza != null){
            ultimo = new Nodo(valor,null,ultimo);
            ultimo.getAnterior().setSiguiente(ultimo);
            size++;
        }
        else{
            agregarInicio(valor);
        }
    }

    /**
     * Método para agregar un nodo en la posición i de la lista doblemente enlazada
     * @param valor valor de tipo genérico T
     * @param i indica la posición del nodo en la lista enlazada
     */
    public void agregarEnPosición(T valor, int i){
        Nodo nodo = new Nodo(valor);
        if (cabeza == null){
            cabeza = nodo;
        }
        else if(i == 0){
            agregarInicio(valor);
        }
        else if (i > size){
            agregarFinal(valor);
        }
        else{
            Nodo puntero = cabeza;
            int contador = 1;
            while(contador < i && puntero.getSiguiente() != null){

                puntero = puntero.getSiguiente();
                contador++;
            }
            nodo.setSiguiente(puntero.getSiguiente());
            nodo.setAnterior(puntero);
            puntero.getSiguiente().setAnterior(nodo);
            puntero.setSiguiente(nodo);
        }
        size++;
    }

    /**
     * Método para buscar elemento en la lista
     * @param i indica la posición del nodo en la lista enlazada
     */
    public Nodo buscarElemento(int i){
        if(cabeza == null){
            return null;
        }
        else{
            Nodo<T> puntero = cabeza;
            int contador = 0;
            while(contador < i && puntero.getSiguiente() != null){
                puntero = puntero.getSiguiente();
                contador++;
            }
            return puntero;
        }
    }

    /**
     * Método para eliminar un nodo al inicio de la lista doblemente enlazada
     */
    public void eliminarInicio(){
        if (cabeza == ultimo){
            cabeza = ultimo = null;
        }
        else{
            cabeza = cabeza.getSiguiente();
            cabeza.setAnterior(null);
        }
        size--;
    }

    /**
     * Método para agregar un nodo al final de la lista doblemente enlazada
     */
    public void eliminarFinal(){
        if (cabeza == ultimo){
            cabeza = ultimo = null;
        }
        else{
            ultimo = ultimo.getAnterior();
            ultimo.setSiguiente(null);
        }
        size--;
    }

    /**
     * Método para eliminar un nodo en determinada posición
     * @param i indica la posición del nodo en la lista enlazada
     */
    public void eliminarEnPosición(int i){
        if(i == 1){
            eliminarInicio();
        }
        else if (i >= size){
            eliminarFinal();
        }
        else{
            Nodo<T> puntero = cabeza;
            int contador = 1;
            while(contador < i && puntero.getSiguiente() != null){

                puntero = puntero.getSiguiente();
                contador++;
            }
            puntero = puntero.getAnterior();
            puntero.setSiguiente(puntero.getSiguiente().getSiguiente());
            size--;
        }
    }

    /**
     * Método que devuelve el valor contenido en el nodo especificado
     * @param i indica la posición del nodo en la lista enlazada
     */
    public T getValor(int i){
        Nodo<T> puntero = cabeza;
        int contador = 0;
        while(contador < i){
            puntero = puntero.getSiguiente();
            contador++;
        }
        return puntero.getValor();
    }

    /**
     * Método que devuelve el tamaño de la lista
     */
    public int getSize(){
        return size;
    }

    /**
     * Método para eliminar completamente la lista
     */
    public void eliminarLista(){
        cabeza = null;
    }
}
