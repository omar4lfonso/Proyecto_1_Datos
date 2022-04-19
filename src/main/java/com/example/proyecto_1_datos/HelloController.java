package com.example.proyecto_1_datos;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {

    private Proyecto_1_Juego_Memoria_Servidor mainVentana;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        //mainVentana.mostrarMenuAdministrador();
    }

    public void setProgramaPrincipal(Proyecto_1_Juego_Memoria_Servidor ProgramaPrincipal) {
        this.mainVentana = ProgramaPrincipal;
    }
}