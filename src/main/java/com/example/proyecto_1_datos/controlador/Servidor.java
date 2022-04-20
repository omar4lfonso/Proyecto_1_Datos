package com.example.proyecto_1_datos.controlador;

import com.example.proyecto_1_datos.modelo.Lista_DE_Imagenes;
import com.example.proyecto_1_datos.modelo.NombresUsuarios;
import com.example.proyecto_1_datos.modelo.SwitchClases;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Servidor {

    // Debe crearse un ID único para cada conexión a un nuevo cliente
    private static int idUnico;

    // un ArrayList para guardar la lista de clientes conectados
    private ClientThread clienteConectado;

    // Traer objeto del controlador servidor
    private ControladorVentanaServidor controladorVentanaServidor;

    // para mostrar fecha y hora
    private SimpleDateFormat sdf;

    // Debe identificarse el puerto al que se desea escuchar
    private int puerto;

    // Esta variable se usa para apagar el servidor
    private boolean continuarServidor;

    private static NombresUsuarios nombresUsuarios;

    private static Lista_DE_Imagenes servidorListaImgns;

    public Servidor(int puerto, ControladorVentanaServidor controladorVentanaServidor){
        this.puerto = puerto;
        this.controladorVentanaServidor = controladorVentanaServidor;

        // para mostrar la fecha
        sdf = new SimpleDateFormat("HH:mm:ss");
    }


    public void iniciar() {
        continuarServidor = true;

        // Crear un socket servidor y esperar solicitudes de conexion
        try {
            // Socket empleado por el servidor
            ServerSocket serverSocket = new ServerSocket(puerto);

            // Ciclo infinito para esperar por conexiones
            while (continuarServidor) {
                // Escribir mensaje explicando que se está esperando
                mostrarLog("Servidor esperando por mensajes en el puerto " + puerto + ".");

                Socket socket = serverSocket.accept(); // aceptar conexión
                if(!continuarServidor){
                    break;
                }

                clienteConectado = new ClientThread(socket); // crear un hilo del cliente

                clienteConectado.start();
            }
            // En caso de que se solicite detener
            try {
                serverSocket.close();

                clienteConectado.sEntrada.close();
                clienteConectado.sSalida.close();
                clienteConectado.socket.close();
            }
            catch(IOException ioE) {
                mostrarLog("Error cerrando las conexiones del cliente: " + ioE + "\n");
            }
        }
        // something went bad
        catch (IOException e) {
            String msg = "Excepción en un nuevo ServerSocket: " + e + "\n";
            mostrarLog(msg);
        }
    }

    /**
     * Función para el GUI para detener el servidor
     */
    public void detener(){
        continuarServidor = false;
        // conectarse a sÍ mismo como cliente para salir

        try {
            new Socket("localhost", puerto);
        }
        catch (Exception e){
            String msg = "Excepción en la detención del servidor: " + e + "\n";
            mostrarLog(msg);
        }
    }

    /**
     * Esta función muestra el log de los eventos
     * @param msg : String a mostrar en el event log
     */
    private synchronized void mostrarLog(String msg) {
        String time = sdf.format(new Date()) + " " + msg;
        controladorVentanaServidor.agregarEvento(time + "\n");
    }

    class ClientThread extends Thread {
        // el socket donde se lee/escribe
        Socket socket;
        ObjectInputStream sEntrada;
        ObjectOutputStream sSalida;

        Object msjEntrada;

        ClientThread (Socket socket) {
            this.socket = socket;

            try{
                sSalida = new ObjectOutputStream(socket.getOutputStream());
                sEntrada = new ObjectInputStream(socket.getInputStream());
            }
            catch(IOException e){
                 mostrarLog("Excepción creando nuevo Stream de Entrada/Salida: " + e);
            }
        }

        // Función que correrá continuamente
        public void run(){
            // correr continuamente hasta un LOGOUT
            boolean continuarCorriendo = true;
            while(continuarCorriendo) {
                // Leer un Objeto del input Stream
                try{
                    msjEntrada = sEntrada.readObject();

                    switch (SwitchClases.Clazz.valueOf(msjEntrada.getClass().getSimpleName())){
                        case NombresUsuarios:
                            Platform.runLater(() -> {
                                nombresUsuarios = (NombresUsuarios) msjEntrada;
                                nombresUsuarios = NombresUsuarios.getNombresUsuarios(nombresUsuarios.getNombreJugador(NombresUsuarios.NumJugador.JUGADOR1),
                                        nombresUsuarios.getNombreJugador(NombresUsuarios.NumJugador.JUGADOR2), nombresUsuarios.getPuntajeJugador(NombresUsuarios.NumJugador.JUGADOR1),
                                        nombresUsuarios.getPuntajeJugador(NombresUsuarios.NumJugador.JUGADOR2), nombresUsuarios.getTamañoTablero());
                                try {
                                    servidorListaImgns = new Lista_DE_Imagenes(nombresUsuarios.getTamañoTablero(), 0);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if(servidorListaImgns != null){
                                    writeMsg(servidorListaImgns);
                                }

                                // Enviar la matriz de posiciones random de las imagenes
                                //writeMsg(ControlJuego.getObjeto_ControlJuego().getMatrizPosicionImagenes());
                            });
                            break;
                        default:
                            System.out.println("No Class!!");
                    }

                } catch (IOException e) {
                    mostrarLog(" Excepción leyendo el Stream: " + e);
                    break;
                } catch (ClassNotFoundException e) {
                    break;
                }
            }
            //eliminar de la lista que contiene los clientes conectados
            close();
        }

        // Intentar cerrar los streams
        private void close() {
            // intentar cerrar la conexión
            try {
                if(sSalida != null) sSalida.close();
            }
            catch(Exception e) {}
            try {
                if(sEntrada != null) sEntrada.close();
            }
            catch(Exception e) {};
            try {
                if(socket != null) socket.close();
            }
            catch (Exception e) {}
        }

        /**
         * Escribir mensaje de salida al cliente
         */
        private boolean writeMsg(Object msg) {
            // si el cliente aun esta conectado, enviar un mensaje
            if(!socket.isConnected()) {
                close();
                return false;
            }
            // write the message to the stream
            try {
                sSalida.writeObject(msg);
            }
            // si ocurre un error, no abortar solo informar al usuario
            catch(IOException e) {
                mostrarLog("Error Enviando mensaje " + e.toString());
            }
            return true;
        }
    }
}
