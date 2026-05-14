/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package torres_de_hanoi;

import java.util.Scanner;

/**
 *
 * @author rafae
 */
public class Torres_de_hanoi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Torres de hanoi
       VarillasVisualizer vv = VarillasVisualizer.iniciar();
       /*Esto ultimo es para visualizar las varillas de manera gráfica*/
        Scanner sc = new Scanner(System.in);
        GestorArchivos gestor = new GestorArchivos();

        System.out.println("¿Desea cargar la partida? (S/N)");
        String respuesta = sc.nextLine().trim().toUpperCase();
        String[][] estadoinicial = null;

        if (respuesta.equals("S")) {
            System.out.print("introduce el nombre del fichero: ");
            String nombre = sc.nextLine().trim();
            estadoinicial = gestor.cargar(nombre);
            System.out.println("Estado cargado desde: " + nombre);

        }
        JuegoColores juego = new JuegoColores(estadoinicial);

        gestor.iniciarGrabacion("partida_guardada.txt");
        JuegoManager manager = new JuegoManager(sc, juego, gestor);
        manager.iniciarJuego();
        gestor.detenerGrabacion();
        sc.close();
        vv.detener(); // ES PARA VISUALIZAR LAS VARILLAS DE MANERA GRÁFICA

        
    }

   
}
