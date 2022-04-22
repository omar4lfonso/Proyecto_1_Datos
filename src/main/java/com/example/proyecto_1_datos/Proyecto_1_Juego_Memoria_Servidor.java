package com.example.proyecto_1_datos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Proyecto_1_Juego_Memoria_Servidor extends Application {
    private Stage stage;

    /*private static Proyecto_1_Juego_Memoria_Servidor miAppCliente;

    public static Proyecto_1_Juego_Memoria_Servidor getMiAppCliente(){
        if(miAppCliente == null){
            miAppCliente = new Proyecto_1_Juego_Memoria_Servidor();
        }
        return miAppCliente;
    }*/

    // Cargar el fmxl que contienen el GUI del Servidor.
    private void crearContenido() throws IOException {
        FXMLLoader loader = new FXMLLoader(Proyecto_1_Juego_Memoria_Servidor.class.getResource("InterfazServidor.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        //HelloController controller = loader.getController();
        //controller.setProgramaPrincipal(this);
        stage.show();
    }

    /*public void mostrarVentanaJuego() {
        try {

            FXMLLoader loader = new FXMLLoader(Proyecto_1_Juego_Memoria_Servidor.class.getResource("hello-view 2.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            *//*stage.toFront();*//*
            stage.show();

        } catch (Exception e) {
        }
    }*/

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        this.stage.setTitle("Ventana de Servidor");

        crearContenido();
    }

    public static void main(String[] args) {
        launch();
    }
}