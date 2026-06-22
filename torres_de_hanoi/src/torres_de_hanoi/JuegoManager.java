/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package torres_de_hanoi;

import java.util.Scanner;

/**
 * Gestiona la version por consola del juego.
 *
 * Lee comandos del usuario, muestra el estado de la partida, permite mover
 * bloques, deshacer movimientos y salir desde la terminal.
 *
 * @author rafae
 */
public class JuegoManager {

    // ATRIBUTOS
    private Scanner sc;
    private JuegoColores juego;
    private GestorArchivos gestor;

    // constructor
    /**
     * Crea un gestor de juego vacio.
     */
    public JuegoManager() {
    }

    /**
     * Crea un gestor de juego por consola con sus dependencias principales.
     *
     * @param sc lector de entrada por consola
     * @param juego objeto con la logica y el estado de la partida
     * @param gestor objeto encargado de guardar datos de la partida
     */
    public JuegoManager(Scanner sc, JuegoColores juego, GestorArchivos gestor) {
        this.sc = sc;
        this.juego = juego;
        this.gestor = gestor;
    }

    /**
     * Inicia el bucle principal del modo consola.
     *
     * Muestra el estado del juego, solicita movimientos, permite deshacer y
     * termina cuando la partida se completa o el usuario decide salir.
     */
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

    /**
     * Muestra el mensaje inicial del modo consola.
     */
    private void mostrarBienvenida() {
        System.out.println("JUEGO DE TORRES DE COLORES");
        System.out.println("colores: R=Rojo, V=Verde, A=Azul");
    }

}
