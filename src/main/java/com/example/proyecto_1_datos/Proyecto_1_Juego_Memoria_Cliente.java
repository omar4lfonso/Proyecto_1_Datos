package com.example.proyecto_1_datos;

import com.example.proyecto_1_datos.controlador.ControladorVentanaJuegoCliente;
import com.example.proyecto_1_datos.controlador.ControladorVentanaLoginCliente;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Proyecto_1_Juego_Memoria_Cliente extends Application {

    /*private static Proyecto_1_Juego_Memoria_Cliente miAppCliente;

    public static Proyecto_1_Juego_Memoria_Cliente getMiAppCliente(){
        if(miAppCliente == null){
            miAppCliente = new Proyecto_1_Juego_Memoria_Cliente();
        }
        return miAppCliente;
    }*/

    private Stage stage, stage2;

    // Cargar el fmxl que contienen el GUI del Servidor.
    private void crearContenido() throws IOException {
        FXMLLoader loader = new FXMLLoader(Proyecto_1_Juego_Memoria_Cliente.class.getResource("Ventana_Login.fxml"));
        //String image = Proyecto_1_Juego_Memoria_Cliente.class.getResource("Backgound.png").toExternalForm();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        ControladorVentanaLoginCliente controller = loader.getController();
        controller.setProgramaPrincipal(this);
        stage.show();
    }

    public void mostrarVentanaJuego() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Proyecto_1_Juego_Memoria_Cliente.class.getResource("Ventana_Juego.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Juego de Memoria");
            stage.show();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        this.stage.setTitle("Juego de Memoria");

        crearContenido();
    }

    public static void main(String[] args) {
        launch();
    }
}