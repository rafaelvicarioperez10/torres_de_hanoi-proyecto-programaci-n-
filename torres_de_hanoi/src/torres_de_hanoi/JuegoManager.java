/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package torres_de_hanoi;

import java.util.Scanner;

/**
 *
 * @author rafae
 */
public class JuegoManager {

    // ATRIBUTOS
    private Scanner sc;
    private JuegoColores juego;
    private GestorArchivos gestor;

    // constructor
    public JuegoManager() {
    }

    public JuegoManager(Scanner sc, JuegoColores juego, GestorArchivos gestor) {
        this.sc = sc;
        this.juego = juego;
        this.gestor = gestor;
    }

    public void iniciarJuego() {
        mostrarBienvenida();
        while (true) {
            juego.mostrarEstado();
            if (juego.juegoCompletado()) {
                System.out.println("Has completado el juego");
                break;
            }
            System.out.print("Selecciona varilla origen(1-3, D = deshacer, S= salir): ");
            String comando = sc.nextLine().trim().toUpperCase();
            gestor.escribir(comando);
            if (comando.equals("S")) {
                gestor.detenerGrabacion();
                sc.close();
                System.exit(0);
            }
            if (comando.equals("D")) {
                juego.deshacer();
                continue;
            }
            try {
                int origen = Integer.parseInt(comando) - 1;
                System.out.print("Selecciona varilla destino (1-3): ");
                int destino = Integer.parseInt(sc.nextLine().trim()) - 1;
                System.out.println("¿Cuántos bloques quieres mover?");
                int cuantos = Integer.parseInt(sc.nextLine().trim());
                juego.mover(origen, destino, cuantos);

            } catch (Exception e) {
                System.out.println("Entrada no válida");
            }
        }
    }

    private void mostrarBienvenida() {
        System.out.println("JUEGO DE TORRES DE COLORES");
        System.out.println("colores: R=Rojo, V=Verde, A=Azul");
    }

}
