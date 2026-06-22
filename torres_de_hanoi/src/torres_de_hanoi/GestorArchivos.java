/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package torres_de_hanoi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.FileWriter;
import java.io.FileReader;

/**
 * Gestiona operaciones basicas de archivo para el juego.
 *
 * Permite iniciar una grabacion de texto, escribir datos de la partida,
 * cerrar la grabacion y cargar un estado inicial desde un archivo.
 *
 * @author Rafael Vicario Pérez
 */
public class GestorArchivos {

    private PrintWriter escritor;

    /**
     * Inicia la grabacion de datos en el archivo indicado.
     *
     * @param ruta ruta del archivo donde se escribira la informacion
     */
    public void iniciarGrabacion(String ruta) {
        try {
            escritor = new PrintWriter(new FileWriter(ruta));
            System.out.println("Grabación iniciada en " + ruta);
        } catch (IOException e) {
            System.out.println("Error al iniciar la grabación");
        }

    }

    /**
     * Escribe una linea en el archivo de grabacion si esta abierto.
     *
     * @param texto texto que se quiere guardar
     */
    public void escribir(String texto) {
        if (escritor != null) {
            escritor.println(texto);
        }
    }

    /**
     * Cierra el archivo de grabacion si estaba abierto.
     */
    public void detenerGrabacion() {
        if (escritor != null) {
            escritor.close();
            System.out.println("Grabación finalizada.");
        }
    }

    /**
     * Carga el estado de las varillas desde un archivo de texto.
     *
     * @param nombre nombre o ruta del archivo que contiene la partida
     * @return matriz con el estado cargado, o null si ocurre un error
     */
    public String[][] cargar(String nombre) {
        String[][] estado = new String[3][];
        try ( BufferedReader br = new BufferedReader(new FileReader(nombre))) {
            for (int i = 0; i < 3; i++) {
                estado[i] = br.readLine().split(" ");
            }
        } catch (Exception e) {
            return null;
        }
        return estado;
    }

}
