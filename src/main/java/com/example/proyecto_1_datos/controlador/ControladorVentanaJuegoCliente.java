package com.example.proyecto_1_datos.controlador;

import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;

public class ControladorVentanaJuegoCliente {
    @FXML
    private TilePane tilePaneMatrizImagenes;
    @FXML
    private Label labelTiempo;
    @FXML
    private Label labelNombreJugador;
    @FXML
    private Label labelNJugador1Puntaje;
    @FXML
    private Label labelNJugador2Puntaje;
    @FXML
    private Label labelPuntajeJ1;
    @FXML
    private Label labelPuntajeJ2;

    private static final int STARTTIME = 0;
    private Timeline timeline;
    private final IntegerProperty timeSeconds = new SimpleIntegerProperty();

    private void updateTime() {
        // increment seconds
        int seconds = timeSeconds.get();
        timeSeconds.set(seconds+1);
    }

    @FXML
    public void initialize(){
        labelTiempo.textProperty().bind(timeSeconds.asString());
    }
}
