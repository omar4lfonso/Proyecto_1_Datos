package com.example.proyecto_1_datos.modelo;

import com.example.proyecto_1_datos.listaEnlazada.ListaEnlazada;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Lista_DE_Imagenes implements Serializable {
    private ListaEnlazada listaDeImagenes;
    private NombresUsuarios.TABLERO_SIZE tamañoTablero;
    private int categoriaImagenes;

    private static Lista_DE_Imagenes objeto_Lista_DE_Imagenes;

    public Lista_DE_Imagenes(NombresUsuarios.TABLERO_SIZE tamañoTablero, int categoriaImagenes) throws IOException, InterruptedException {
        this.categoriaImagenes = categoriaImagenes;
        this.tamañoTablero = tamañoTablero;

        listaDeImagenes = new ListaEnlazada<ImageIcon>();
        llenarListaImagenes();
        objeto_Lista_DE_Imagenes = this;
    }

    public static Lista_DE_Imagenes getObjeto_Lista_DE_Imagenes(){
        return objeto_Lista_DE_Imagenes;
    }

    public Lista_DE_Imagenes(NombresUsuarios.TABLERO_SIZE tamañoTablero, int categoriaImagenes, ListaEnlazada<ImageIcon> listaDeImagenesIn) throws InterruptedException {
        this.categoriaImagenes = categoriaImagenes;
        this.tamañoTablero = tamañoTablero;

        listaDeImagenes = new ListaEnlazada<ImageIcon>();
        copiarListaImagenes(listaDeImagenesIn);
        objeto_Lista_DE_Imagenes = this;
    }

    /*public static Lista_DE_Imagenes getObjeto_Lista_DE_ImagenesLista_DE_Imagenes(NombresUsuarios.TABLERO_SIZE tamañoTablero, int categoriaImagenes) throws IOException {

        if(objeto_Lista_DE_Imagenes == null){
            objeto_Lista_DE_Imagenes = new Lista_DE_Imagenes(tamañoTablero, categoriaImagenes);
        }

        return objeto_Lista_DE_Imagenes;
    }*/

    private void copiarListaImagenes(ListaEnlazada<ImageIcon> lista_de_imagenes) throws InterruptedException {
        for(int i = lista_de_imagenes.getSize(); i>0; i--){
            this.listaDeImagenes.agregarInicio(lista_de_imagenes.getValor(i-1));
        }
    }

    /*public static Lista_DE_Imagenes getObjeto_Lista_DE_ImagenesLista_DE_Imagenes(Lista_DE_Imagenes resident) throws IOException {

        if(objeto_Lista_DE_Imagenes == null){
            objeto_Lista_DE_Imagenes = new Lista_DE_Imagenes(resident.getTamañoTablero(), resident.getCategoriaImagenes(), resident.getListaDeImagenes());
        }

        return objeto_Lista_DE_Imagenes;
    }*/

    public ListaEnlazada getListaDeImagenes(){
        return listaDeImagenes;
    }

    private void llenarListaImagenes() throws IOException, InterruptedException {
        switch (tamañoTablero){
            case T3X3:
                File comodin = new File("src/main/resources/com/example/proyecto_1_datos/Comodin.jpg");
                ImageIcon image = new ImageIcon(ImageIO.read(comodin));
                listaDeImagenes.agregarInicio(image);
                //String str = String.valueOf(this.getClass().getResource("Categoria1"));
                File dirImages = new File("src/main/resources/com/example/proyecto_1_datos/Categoria1");
                System.out.println(dirImages.isDirectory());
                FilenameFilter filterJpg = new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith(".jpg");
                    }
                };

                for (final File f : dirImages.listFiles(filterJpg)) {
                    image = new ImageIcon(ImageIO.read(f));
                    listaDeImagenes.agregarFinal(image);
                }

        }
    }

    public NombresUsuarios.TABLERO_SIZE getTamañoTablero(){
        return tamañoTablero;
    }

    public int getCategoriaImagenes(){
        return categoriaImagenes;
    }

    public int getTamañoLista(){
        return listaDeImagenes.getSize();
    }

}
