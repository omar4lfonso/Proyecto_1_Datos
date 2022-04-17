package com.example.proyecto_1_datos.modelo;

public class FormatoMensajes {
    // TODO: definir los tipos de mensajes que se esperan tanto para el servidor como para el cliente
    public enum FORMATO_MSJ{
    // Formato de mensajes enviados del Cliente al Servidor
    NOMBRES_USUARIOS, // Este tipo se maneja con el instanceof y NombresUsuarios.java
    SOLICITAR_REGLAS_JUEGO,
    TAMAÑO_TABLERO,
    CATEGORIA_IMAGENES,
    EVENTO_CLICK_IMAGEN,
    // Formato de mensajes enviados del Servidor al Cliente
    REGLAS_JUEGO,
    LISTA_C_CATEGORIA_IMAGENES,
    LISTA_DE_IMAGENES,
    PUNTAJES_JUGADORES
    }
    private int tipo;
    private String mensaje;

    // TODO: constructor
    public FormatoMensajes(int tipo, String mensaje){
        this.tipo = tipo;
        this.mensaje = mensaje;
    }

    // getters
    public int obtenerTipo() {
        return tipo;
    }

    public String obtenerMensaje(){
        return mensaje;
    }
}
