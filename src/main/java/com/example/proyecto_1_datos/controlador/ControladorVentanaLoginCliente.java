package com.example.proyecto_1_datos.controlador;

import com.example.proyecto_1_datos.modelo.NombresUsuarios;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ControladorVentanaLoginCliente {
    // Implementación de Java FX
    @FXML
    private TextField txtNombreJugador1;
    @FXML
    private TextField txtNombreJugador2;
    @FXML
    private Button btnInicioLogin;
    @FXML
    private Label labelError;
    @FXML
    private ChoiceBox choiceBoxTTablero;

    // Configuración del servidor
    private boolean conectado;
    private String servidor;
    private int puerto;
    private NombresUsuarios objNombresUsuarios;

    // Para I/O
    private ObjectInputStream sEntrada;		// para lectura del socket
    private ObjectOutputStream sSalida;		// para escritura del socket
    private Socket socket;

    @FXML
    public void initialize(){
        choiceBoxTTablero.getItems().addAll("3x3", "3x5", "5x5");
        choiceBoxTTablero.setValue("3x3");
    }

    /**
     * Metodo utilizado por el boton de login
     */
    public void login(){
        puerto = 1500;
        servidor = "localhost";
        System.out.println(servidor);
        String stringJugador1, stringJugador2;
        stringJugador1 = txtNombreJugador1.getText();
        stringJugador2 = txtNombreJugador2.getText();
        if(!stringJugador1.isEmpty() && !stringJugador2.isEmpty()){
            objNombresUsuarios = new NombresUsuarios(stringJugador1, stringJugador2, 0, 0);
        }
        else{
            labelError.setText("Error: Debe llenar ambos nombres de los jugadores!");
            return;
        }
        // Probar si se puede iniciar la conexión al servidor
        // si falla no se hace nada
        if(iniciar()){
            conectado = true;
            return;
        }
    }

    /**
     * Inicial la ventana de dialogo
     * @return : envia un bool indicando si la conexion funciono o no
     */
    public boolean iniciar(){
        // Intente conectarse al servidor
        try{
            socket = new Socket(servidor, puerto);
        }
        catch (Exception e){
            labelError.setText("Error en la conexión al servidor: " + e);
        }

        // Creando los dos Data Streams
        try{
            sEntrada = new ObjectInputStream(socket.getInputStream());
            sSalida = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException eIO){
            labelError.setText("Excepción creando streams de Entrada/Salida" + eIO);
            return false;
        }

        // Crear el hilo que escucha al servidor
        new EscucharServidor().start();

        // Enviar el nombre de usuario al servidor. Este es el unico mensaje que se envia
        // como String. Todos los otros mensajes son de chat.
        try{
            sSalida.writeObject(objNombresUsuarios);
        }
        catch (IOException eIO){
            labelError.setText("Excepción de login: " + eIO);
            desconectar();
            return false;
        }

        System.out.println("Se conecto correctamente, conectado = ");
        // Exito: se le informa a la llamada que funciono.
        return true;
    }

    /**
     * Cuando algo sale mal
     * Cerrar los streams de Entrada/Salida y desconectar
     */
    private void desconectar() {
        try {
            if(sEntrada != null) sEntrada.close();
        }catch (IOException e) {} // sin acción correspondiente

        try{
            if(sSalida != null) sSalida.close();
        }catch (IOException e) {} // sin acción correspondiente

        try{
            if(socket != null) socket.close();
        }catch (IOException e) {} // sin acción correspondiente

    }

    /**
     * Esta clase permite esperar por los mensajes que provienen del servidor y los adjunta al area de texto
     */
    class EscucharServidor extends Thread {
        public void correr(){
            while (true) {
                try {
                    Object msg = sEntrada.readObject();
                    //String[] msgSeparado = msg.split(":");


                    /*if(msgSeparado[1].equals("QUIENESTA")){
                        Platform.runLater(() -> {
                            usuarios.add(msgSeparado[0]);
                        });
                    } else if(msgSeparado[1].equals("REMOVER")) {
                        Platform.runLater(() -> {
                            usuarios.remove(msgSeparado[0]);
                        });
                    } else {
                        txtAreaMensajesServidor.appendText(msg);
                    }*/
                }
                // no sucede con un objeto String pero aun asi debe realizarse el catch
                catch (IOException e) {
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
