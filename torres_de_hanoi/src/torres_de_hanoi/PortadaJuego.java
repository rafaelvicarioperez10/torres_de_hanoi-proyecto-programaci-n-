/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package torres_de_hanoi;

import javax.swing.*;
import java.awt.*;

/**
 * @author rafae Ventana inicial del juego.
 *
 * Muestra una portada con el titulo del juego y botones para iniciar una nueva
 * partida, cargar una partida guardada o salir del programa.
 */
public class PortadaJuego extends JFrame {

    private GestorArchivos gestor;

    /**
     * Crea la portada del juego.
     *
     * @param gestor gestor de archivos utilizado para cargar partidas
     */
    public PortadaJuego(GestorArchivos gestor) {
        this.gestor = gestor;
        configurarVentana();
        crearContenido();
    }

    /**
     * Configura las propiedades principales de la ventana.
     */
    private void configurarVentana() {
        setTitle("Torres de Hanoi - Juego de Colores");
        setSize(600, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Crea el contenido visual de la portada.
     */
    private void crearContenido() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel("TORRES DE HANOI", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(new Color(40, 40, 40));

        JLabel subtitulo = new JLabel("Juego de bloques de colores", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitulo.setForeground(new Color(90, 90, 90));

        JPanel panelTitulos = new JPanel(new GridLayout(2, 1));
        panelTitulos.setBackground(new Color(245, 245, 245));
        panelTitulos.setBorder(BorderFactory.createEmptyBorder(50, 20, 30, 20));
        panelTitulos.add(titulo);
        panelTitulos.add(subtitulo);

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 15));
        panelBotones.setBackground(new Color(245, 245, 245));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 180, 60, 180));

        JButton btnNuevaPartida = new JButton("Nueva partida");
        JButton btnCargarPartida = new JButton("Cargar partida");
        JButton btnSalir = new JButton("Salir");

        configurarBoton(btnNuevaPartida);
        configurarBoton(btnCargarPartida);
        configurarBoton(btnSalir);

        btnNuevaPartida.addActionListener(e -> iniciarNuevaPartida());
        btnCargarPartida.addActionListener(e -> cargarPartida());
        btnSalir.addActionListener(e -> System.exit(0));

        panelBotones.add(btnNuevaPartida);
        panelBotones.add(btnCargarPartida);
        panelBotones.add(btnSalir);

        panelPrincipal.add(panelTitulos, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Aplica un estilo comun a los botones de la portada.
     *
     * @param boton boton que se quiere configurar
     */
    private void configurarBoton(JButton boton) {
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(70, 130, 180));
        boton.setForeground(Color.WHITE);
    }

    /**
     * Inicia una partida nueva sin cargar ningun archivo.
     */
    private void iniciarNuevaPartida() {
        JuegoColores juego = new JuegoColores(null);
        abrirJuego(juego);
    }

    /**
     * Solicita el nombre de un archivo y carga una partida guardada.
     */
    private void cargarPartida() {
        String nombre = JOptionPane.showInputDialog(
                this,
                "Introduce el nombre del fichero:",
                "Cargar partida",
                JOptionPane.QUESTION_MESSAGE
        );

        if (nombre == null || nombre.trim().isEmpty()) {
            return;
        }

        String[][] estadoInicial = gestor.cargar(nombre.trim());

        if (estadoInicial == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se ha podido cargar la partida.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JuegoColores juego = new JuegoColores(estadoInicial);
        abrirJuego(juego);
    }

    /**
     * Abre la ventana principal del juego y cierra la portada.
     *
     * @param juego partida que se va a mostrar en la interfaz
     */
    private void abrirJuego(JuegoColores juego) {
        gestor.iniciarGrabacion("partida_guardada.txt");
        VarillasVisualizer.iniciar(juego, gestor);
        dispose();
    }
}
