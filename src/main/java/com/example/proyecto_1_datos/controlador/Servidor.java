package com.example.proyecto_1_datos.controlador;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Servidor {

    // Debe crearse un ID único para cada conexión a un nuevo cliente
    private static int idUnico;

    // Debe identificarse el puerto al que se desea escuchar
    private int puerto;

    // Esta variable se usa para apagar el servidor
    private boolean continuarServidor;

    public Servidor(int puerto){
        this.puerto = puerto;
    }


    public void iniciar() {
        continuarServidor = true;

        // Crear un socket servidor y esperar solicitudes de conexion
        try {
            // Socket empleado por el servidor
            ServerSocket serverSocket = new ServerSocket(puerto);

            // Ciclo infinito para esperar por conexiones
            while (continuarServidor) {
                //TODO: imprimir un mensaje que indicque que el servidor está escuchando

                Socket socket = serverSocket.accept(); // aceptar conexión
                if(!continuarServidor){
                    break;
                }

                //TODO: crear clase de hilos para los clientes e instanciar un hilo

                //TODO: agregar el cliente a una lista

                //TODO: iniciar hilo para cliente
            }
            // En caso de que se solicite detener
            try {
                serverSocket.close();

                // TODO: cerrar todas las conexiones a los stream y los hilos de los clientes
            }
            catch(IOException ioE) {
                // TODO: ver si se puede imprimir algún mensaje de error
            }
        }
        // something went bad
        catch (IOException e) {
            String msg = "Excepción en un nuevo ServerSocket: " + e + "\n";
            // TODO: mostrar mensaje en ventana de log
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
            // TODO: ver si se puede agregar algún mensaje al log
        }
    }

    /**
     * Esta función muestra el log de los eventos
     * @param msg : String a mostrar en el event log
     */
    private synchronized void mostrarLog(String msg) {
        // TODO:
        /*String time = sdf.format(new Date()) + " " + msg;
        controladorServidor.agregarEvento(time + "\n");*/
    }

    /**
     * Esta función se encarga de eliminar un cliente cuando este utiliza el botón de SALIR
     * @param id : identificador del cliente
     */
    synchronized void remove(int id){
        // TODO: Buscar el ID del cliente
        /*for(int i = 0; i < clientesConectados.size(); ++i){
            ClientThread ct = clientesConectados.get(i);
            if(ct.id == id){
                controladorServidor.remove(ct.nombreUsuario);
                ct.writeMsg(ct.nombreUsuario + ":REMOVE");
                clientesConectados.remove(i);
                return;
            }
        }*/
    }

    class ClientThread extends Thread {
        // el socket donde se lee/escribe
        Socket socket;
        ObjectInputStream sEntrada;
        ObjectOutputStream sSalida;

        // ID único: facilita la desconexión
        int id;
        // TODO: establecer formato de mensajes
        /*// el nombre de usuario del cliente
        String nombreUsuario;
        // identificador de comandos recibidos
        MensajeChat cm;
        // Fecha
        String fecha;*/

        ClientThread (Socket socket) {
            id = ++idUnico;
            this.socket = socket;

            System.out.println("Hilo tratando de crear los objectos de Stream");
            try{
                sSalida = new ObjectOutputStream(socket.getOutputStream());
                sEntrada = new ObjectInputStream(socket.getInputStream());

                // TODO: establecer formato de mensajes
                /*// leer nombre de usuario
                nombreUsuario = (String) sEntrada.readObject();
                controladorServidor.agregarUsuario(nombreUsuario);*/
            }
            catch(IOException e){
                /* TODO: mostrarLog("Excepcion creando nuevo Stream de Entrada/Salida: " + e)*/;
            }
            /* TODO: fecha = new Date().toString() + "\n";*/
        }

        // Función que correrá continuamente
        public void run(){
            // correr continuamente hasta un LOGOUT
            boolean continuarCorriendo = true;
            while(continuarCorriendo) {
                // leer un String como objeto
                // TODO: debe definirse el procesamiento de los mensajes recibidos por el cliente
                /*try {
                    cm = (MensajeChat) sEntrada.readObject();
                }
                catch (IOException e) {
                    mostrarLog(" Exepción leyendo el Stream: " + e);
                    break;
                }
                catch (ClassNotFoundException e2){
                    break;
                }
                String mensaje = cm.obtenerMensaje();

                // Verificar de acuerdo al tipo de mensaje recibido
                switch (cm.obtenerTipo()){
                    case MensajeChat.MENSAJE:
                        difundirMsg(nombreUsuario + ": " + mensaje);
                        break;
                    case MensajeChat.LOGOUT:
                        mostrarLog(nombreUsuario + " desconectar con un mensaje de LOGOUT.");
                        difundirMsg(nombreUsuario + ":REMOVE");
                        break;
                }*/
            }
            //eliminar de la lista que contiene los clientes conectados
            /*TODO: remove(id);
            close();*/
        }

        // Intentar cerrar todo
        private void close() {
            // intentar cerrar la conexion
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
        private boolean writeMsg(String msg) {
            // TODO: definir cómo se mostraran los mensajes de log
            /*// si el cliente aun esta conectado, enviar un mensaje
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
                mostrarLog("Error sending message to " + nombreUsuario);
                mostrarLog(e.toString());
            }*/
            return true;
        }
    }
}
