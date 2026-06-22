/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package torres_de_hanoi;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Clase principal del proyecto Torres de Hanoi de colores.
 *
 * Se encarga de iniciar el programa, preguntar si se desea cargar una partida,
 * crear los objetos principales del juego y abrir la interfaz grafica.
 *
 * @author Rafael Vicario Pérez
 */
public class Torres_de_hanoi {

    /**
     * Metodo principal de ejecucion del programa.
     *
     * @param args argumentos recibidos por linea de comandos
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestorArchivos gestor = new GestorArchivos();
            PortadaJuego portada = new PortadaJuego(gestor);
            portada.setVisible(true);
        });
    }
}
