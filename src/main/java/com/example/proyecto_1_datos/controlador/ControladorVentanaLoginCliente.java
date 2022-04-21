package com.example.proyecto_1_datos.controlador;

import com.example.proyecto_1_datos.Proyecto_1_Juego_Memoria_Cliente;
import com.example.proyecto_1_datos.modelo.Lista_DE_Imagenes;
import com.example.proyecto_1_datos.modelo.NombresUsuarios;
import com.example.proyecto_1_datos.modelo.SwitchClases;
import javafx.application.Platform;
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

    private Proyecto_1_Juego_Memoria_Cliente mainVentana;
    private static boolean imagenesRecibidas = false;

    private Lista_DE_Imagenes clienteListaImgns;

    public void setProgramaPrincipal(Proyecto_1_Juego_Memoria_Cliente ProgramaPrincipal) {
        this.mainVentana = ProgramaPrincipal;
    }

    @FXML
    public void initialize(){
        choiceBoxTTablero.getItems().addAll("T3X3", "T3X5", "T5X5");
        choiceBoxTTablero.setValue("T3X3");
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
        NombresUsuarios.TABLERO_SIZE tamañoTablero = NombresUsuarios.TABLERO_SIZE.valueOf(choiceBoxTTablero.getValue().toString());
        if(!stringJugador1.isEmpty() && !stringJugador2.isEmpty()){
            objNombresUsuarios = NombresUsuarios.getNombresUsuarios(stringJugador1, stringJugador2, tamañoTablero);
        }
        else{
            labelError.setText("Error: Debe llenar ambos nombres de los jugadores!");
            return;
        }
        // Probar si se puede iniciar la conexión al servidor
        // si falla no se hace nada
        if(iniciar()){
            conectado = true;

            /*while(imagenesRecibidas == false){
                mainVentana.mostrarVentanaJuego();
            }*/

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
        public void run(){
            while (true) {
                try {
                    Object msjEntrada = sEntrada.readObject();
                    String msjClazz = msjEntrada.getClass().getSimpleName();

                    switch (SwitchClases.Clazz.valueOf(msjClazz)) {
                        case Lista_DE_Imagenes:
                            Platform.runLater(() -> {
                                try {
                                    clienteListaImgns = new Lista_DE_Imagenes(((Lista_DE_Imagenes) msjEntrada).getTamañoTablero(), ((Lista_DE_Imagenes) msjEntrada).getCategoriaImagenes(), ((Lista_DE_Imagenes) msjEntrada).getListaDeImagenes());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    mainVentana.mostrarVentanaJuego();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            break;
                        case ControlJuego:
                            // Recibir la información del servidor acerca de los movimientos:
                            ControlJuego controlJuego = (ControlJuego) msjEntrada;

                            ControlJuego controlJuegoCliente =
                                    ControlJuego.getObjeto_ControlJuego(controlJuego);

                            controlJuegoCliente.setTurnoJugador(controlJuego.getTurnoJugador());

                            controlJuegoCliente.setPuntajeJugador(NombresUsuarios.NumJugador.JUGADOR1,
                                    controlJuego.getPuntajeJugador(NombresUsuarios.NumJugador.JUGADOR1));
                            controlJuegoCliente.setPuntajeJugador(NombresUsuarios.NumJugador.JUGADOR2,
                                    controlJuego.getPuntajeJugador(NombresUsuarios.NumJugador.JUGADOR2));


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
