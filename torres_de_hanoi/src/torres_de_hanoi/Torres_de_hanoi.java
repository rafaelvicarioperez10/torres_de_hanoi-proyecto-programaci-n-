/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package torres_de_hanoi;

import javax.swing.JOptionPane;

/**
 * Clase principal del proyecto Torres de Hanoi de colores.
 *
 * Se encarga de iniciar el programa, preguntar si se desea cargar una partida,
 * crear los objetos principales del juego y abrir la interfaz grafica.
 *
 * @author rafae
 */
public class Torres_de_hanoi {

    /**
     * Metodo principal de ejecucion del programa.
     *
     * @param args argumentos recibidos por linea de comandos
     */
    public static void main(String[] args) {

        GestorArchivos gestor = new GestorArchivos();

        int respuesta = JOptionPane.showConfirmDialog(
                null,
                "¿Desea cargar la partida?",
                "Cargar partida",
                JOptionPane.YES_NO_OPTION
        );

        String[][] estadoinicial = null;

        if (respuesta == JOptionPane.YES_OPTION) {
            String nombre = JOptionPane.showInputDialog(
                    null,
                    "Introduce el nombre del fichero:",
                    "Cargar partida",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (nombre != null && !nombre.trim().isEmpty()) {
                estadoinicial = gestor.cargar(nombre.trim());
                JOptionPane.showMessageDialog(null, "Estado cargado desde: " + nombre);
            }
        }

        JuegoColores juego = new JuegoColores(estadoinicial);

        gestor.iniciarGrabacion("partida_guardada.txt");

        VarillasVisualizer vv = VarillasVisualizer.iniciar(juego, gestor);
    }
}
