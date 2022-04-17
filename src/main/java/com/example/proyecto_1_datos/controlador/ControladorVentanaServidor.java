package com.example.proyecto_1_datos.controlador;

import com.example.proyecto_1_datos.modelo.NombresUsuarios;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;

public class ControladorVentanaServidor {
    @FXML
    private Button btnIniciarServidor;
    @FXML
    private Button btnDetenerServidor;
    @FXML
    private TilePane tilePaneMatrizImagenes;
    @FXML
    private Label labelPuntajeJugador1;
    @FXML
    private Label labelPuntajeJugador2;
    @FXML
    private Label labelTime;
    @FXML
    private TextArea txtAreaLogEventos;
    @FXML
    private TextField txtFieldPuntajeJugador1;
    @FXML
    private TextField txtFieldPuntajeJugador2;
    @FXML
    private TextField txtFieldTurnoJugador;

    public Servidor servidor;

    @FXML
    public void iniciarServidor(){
        // crear nuevo Servidor
        servidor = new Servidor(1500, this);
        new ServidorEjecutando().start();
        labelTime.setText("00:00");
        btnIniciarServidor.setDisable(true);
        btnDetenerServidor.setDisable(false);
    }

    /**
     * Esta funcion se encarga de detener el servidor
     */
    @FXML
    public void detenerServidor(){
        if (servidor != null){
            servidor.detener();
            btnDetenerServidor.setDisable(true);
            btnIniciarServidor.setDisable(false);
            servidor = null;
            return;
        }
    }

    /**
     * Un hilo para correr el servidor
     */
    class ServidorEjecutando extends Thread {
        public void run() {
            servidor.iniciar();         // should execute until if fails
            // the server failed
            agregarEvento("El servidor se ha detenido\n");
            servidor = null;
        }
    }

    /**
     * Esta funcion se encarga de agregar los mensajes de historial de acciones al GUI del servidor
     * @param string : mensaje a mostrar
     */
    public void agregarEvento(String string) {
        txtAreaLogEventos.appendText(string);
    }

}
