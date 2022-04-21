package com.example.proyecto_1_datos.controlador;

import com.example.proyecto_1_datos.Proyecto_1_Juego_Memoria_Cliente;
import com.example.proyecto_1_datos.modelo.Lista_DE_Imagenes;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class ControladorVentanaJuegoCliente {
    @FXML
    private TilePane tilePane;
    @FXML
    private AnchorPane myAnchorPane;
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

    private static boolean listaImagenesRecibida = false;
    private Lista_DE_Imagenes lista_de_imagenes;

    private Proyecto_1_Juego_Memoria_Cliente mainVentana;

    private static final int STARTTIME = 0;
    private Timeline timeline;
    private final IntegerProperty timeSeconds = new SimpleIntegerProperty();

    private static final double ELEMENT_SIZE = 100;
    private static final double GAP = ELEMENT_SIZE / 10;

    int nCols, nRows;

    public void setProgramaPrincipal(Proyecto_1_Juego_Memoria_Cliente ProgramaPrincipal) {
        this.mainVentana = ProgramaPrincipal;
    }

    private void updateTime() {
        // increment seconds
        int seconds = timeSeconds.get();
        timeSeconds.set(seconds+1);
    }

    @FXML
    public void initialize(){
        labelTiempo.textProperty().bind(timeSeconds.asString());

        lista_de_imagenes = Lista_DE_Imagenes.getObjeto_Lista_DE_Imagenes();
        while(lista_de_imagenes == null){
            lista_de_imagenes = Lista_DE_Imagenes.getObjeto_Lista_DE_Imagenes();
        }
        switch (lista_de_imagenes.getTamañoTablero()){
            case T3X5:
                nCols = 3;
                nRows = 5;
                break;
            case T5X5:
                nCols = 5;
                nRows = 5;
                break;
            default:
                nCols = 3;
                nRows = 3;
        }
        tilePane.setPrefColumns(nCols);
        tilePane.setPrefRows(nRows);

        //myAnchorPane.setStyle("-fx-background-image: url('../resources/com/example/proyecto_1_datos/Backgound.png');");
        tilePane.setHgap(GAP);
        tilePane.setVgap(GAP);

        //now set image in tiles
        createElements();
    }

    private void createElements() {
        int count = 0;
        tilePane.getChildren().clear();

        for (int i = 0; i < nCols; i++) {
            for (int j = 0; j < nRows; j++) {
                tilePane.getChildren().add(createPage(count));
                count++;
            }
        }
    }

    private static javafx.scene.image.Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }

    public VBox createPage(int index) {

        ImageView imageView = new ImageView();
        ImageIcon imageIcon = (ImageIcon) lista_de_imagenes.getListaDeImagenes().buscarElemento(index).getValor();

        //Image image = imageIcon.getImage();
        BufferedImage bi = new BufferedImage(
                imageIcon.getIconWidth(),
                imageIcon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB);
        javafx.scene.image.Image image = convertToFxImage(bi);
        //BufferedImage bufferedImage = ImageIO.read(file);
        //Image image = Image.fromPlatformImage(((ImageIcon)lista_de_imagenes.getListaDeImagenes().buscarElemento(index).getValor()).getImage());//new Image(file.toURI().toString());
        imageView.setImage(image);
        imageView.setFitWidth(ELEMENT_SIZE);
        imageView.setFitHeight(ELEMENT_SIZE);
        // imageView.setPreserveRatio(true);

        imageView.setSmooth(true);
        imageView.setCache(true);

        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("Tile pressed ");
                event.consume();
            }
        });

        VBox pageBox = new VBox();
        pageBox.getChildren().add(imageView);
        pageBox.setStyle("-fx-border-color: orange;");

        imageView = null;
        return pageBox;
    }

}
