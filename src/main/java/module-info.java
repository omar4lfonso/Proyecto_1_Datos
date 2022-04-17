module com.example.proyecto_1_datos {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proyecto_1_datos to javafx.fxml;
    exports com.example.proyecto_1_datos;

    opens com.example.proyecto_1_datos.controlador to javafx.fxml;
    exports com.example.proyecto_1_datos.controlador;
}