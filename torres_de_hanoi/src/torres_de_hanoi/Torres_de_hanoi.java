/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package torres_de_hanoi;

import javax.swing.JOptionPane;

/**
 *
 * @author rafae
 */
public class Torres_de_hanoi {

    /**
     * @param args the command line arguments
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

        VarillasVisualizer vv = VarillasVisualizer.iniciar(juego);
    }
}
