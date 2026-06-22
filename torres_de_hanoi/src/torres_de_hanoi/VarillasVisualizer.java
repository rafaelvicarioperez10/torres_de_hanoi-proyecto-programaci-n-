/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package torres_de_hanoi;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;

/**
 * Ventana grafica encargada de representar visualmente las varillas del juego.
 *
 * Esta clase dibuja los bloques de colores, ofrece botones para mover bloques
 * entre varillas, permite deshacer el ultimo movimiento y cerrar la partida de
 * forma controlada.
 *
 * @author rafae
 */
public class VarillasVisualizer extends JFrame {

    private JPanel panelVarillas;
    private List<List<String>> varillas = new ArrayList<>();
    private ConsoleCapturer capturer;
    private volatile boolean running = true;
    private JuegoColores juego;
    private GestorArchivos gestor;

    /**
     * Crea la ventana del visualizador y conecta la interfaz con la logica del
     * juego.
     *
     * @param juego objeto que contiene el estado y las reglas de la partida
     * @param gestor objeto encargado de gestionar la grabacion o carga de
     * archivos
     */
    public VarillasVisualizer(JuegoColores juego, GestorArchivos gestor) {
        this.juego = juego;
        this.gestor = gestor;
        configurarVentana();
        configurarPanel();
        JPanel panelBotones = new JPanel();

        JButton btnAtoB = new JButton("A -> B");
        JButton btnAtoC = new JButton("A -> C");
        JButton btnBtoA = new JButton("B -> A");
        JButton btnBtoC = new JButton("B -> C");
        JButton btnCtoA = new JButton("C -> A");
        JButton btnCtoB = new JButton("C -> B");
        JButton btnDeshacer = new JButton("Deshacer");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnAtoB);
        panelBotones.add(btnAtoC);
        panelBotones.add(btnBtoA);
        panelBotones.add(btnBtoC);
        panelBotones.add(btnCtoA);
        panelBotones.add(btnCtoB);
        panelBotones.add(btnDeshacer);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.SOUTH);
        btnAtoB.addActionListener(e -> realizarMovimiento(0, 1));
        btnAtoC.addActionListener(e -> realizarMovimiento(0, 2));
        btnBtoA.addActionListener(e -> realizarMovimiento(1, 0));
        btnBtoC.addActionListener(e -> realizarMovimiento(1, 2));
        btnCtoA.addActionListener(e -> realizarMovimiento(2, 0));
        btnCtoB.addActionListener(e -> realizarMovimiento(2, 1));
        btnDeshacer.addActionListener(e -> deshacerMovimiento());
        btnSalir.addActionListener(e -> salirDelJuego());
    }

    /**
     * Configura las propiedades principales de la ventana.
     */
    private void configurarVentana() {
        setTitle("Visualizador de Varillas");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Crea el panel principal donde se dibujan las varillas y los bloques.
     */
    private void configurarPanel() {
        panelVarillas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                dibujarVarillas(g);
            }
        };
        panelVarillas.setBackground(Color.WHITE);
        add(panelVarillas);
    }

    /**
     * Crea, muestra e inicia el visualizador grafico del juego.
     *
     * @param juego objeto con el estado actual de la partida
     * @param gestor objeto encargado de la gestion de archivos
     * @return instancia del visualizador creado
     */
    public static VarillasVisualizer iniciar(JuegoColores juego, GestorArchivos gestor) {
        VarillasVisualizer visualizador = new VarillasVisualizer(juego, gestor);
        visualizador.setVisible(true);
        visualizador.iniciarCaptura();
        juego.mostrarEstado();
        return visualizador;
    }

    /**
     * Inicia un hilo encargado de leer la salida de consola y actualizar la
     * interfaz.
     */
    private void iniciarCaptura() {
        capturer = new ConsoleCapturer();
        Thread hiloCaptura = new Thread(() -> {
            while (running) {
                String linea = capturer.leerLinea();
                if (linea != null) {
                    procesarLinea(linea);
                }
            }
        }, "VarillasVisualizer-Thread");
        hiloCaptura.setDaemon(true);
        hiloCaptura.start();
    }

    /**
     * Procesa una linea de texto procedente de consola para detectar estados de
     * varillas.
     *
     * @param linea texto capturado de la salida de consola
     */
    private void procesarLinea(String linea) {
        Pattern pattern = Pattern.compile("(?i)Varilla\\s*(\\d+):\\s*\\[([^\\]]*)\\]");
        Matcher matcher = pattern.matcher(linea.trim());
        if (matcher.find()) {
            actualizarVarillas(matcher);
        }
    }

    /**
     * Actualiza la lista interna de varillas a partir del texto detectado.
     *
     * @param matcher resultado de la expresion regular con numero y contenido
     * de varilla
     */
    private void actualizarVarillas(Matcher matcher) {
        int numVarillas = Integer.parseInt(matcher.group(1)) - 1;
        String contenido = matcher.group(2).trim();
        synchronized (varillas) {
            while (varillas.size() < 3) {
                varillas.add(new ArrayList<>());
            }
            varillas.set(numVarillas, parsearColores(contenido));

        }

        SwingUtilities.invokeLater(() -> panelVarillas.repaint());
    }

    /**
     * Convierte el contenido textual de una varilla en una lista de codigos de
     * color.
     *
     * @param contenido texto con los codigos de colores separados por espacios
     * @return lista de colores detectados
     */
    private List<String> parsearColores(String contenido) {
        List<String> colores = new ArrayList<>();
        contenido = contenido.trim(); // elimina espacios al inicio y final
        if (!contenido.isEmpty()) {
            for (String color : contenido.split("\\s+")) {
                if (!color.isEmpty()) {
                    colores.add(color.trim());
                }
            }
        }
        return colores;
    }

    /**
     * Dibuja todas las varillas y sus bloques en el panel principal.
     *
     * @param g contexto grafico utilizado por Swing para pintar
     */
    private void dibujarVarillas(Graphics g) {
        List<List<String>> copiaVarillas;
        synchronized (varillas) {
            if (varillas.isEmpty()) {
                return;
            }
            copiaVarillas = new ArrayList<>(varillas.size());
            for (List<String> v : varillas) {
                copiaVarillas.add(new ArrayList<>(v));
            }
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int numVarillas = copiaVarillas.size();
        int anchoPanel = panelVarillas.getWidth();
        int altoPanel = panelVarillas.getHeight();
        int espacioVarillas = anchoPanel / (numVarillas + 1);
        for (int i = 0; i < numVarillas; i++) {
            int x = espacioVarillas * (i + 1);
            dibujarVarillas(g2d, x, altoPanel, copiaVarillas.get(i), i + 1);

        }
    }

    /**
     * Dibuja una varilla concreta con su base, poste, numero y bloques.
     *
     * @param g contexto grafico 2D
     * @param centroX posicion horizontal del centro de la varilla
     * @param altoPanel altura disponible del panel
     * @param colores lista de bloques que contiene la varilla
     * @param numVarillas numero visible de la varilla
     */
    private void dibujarVarillas(Graphics2D g, int centroX, int altoPanel, List<String> colores, int numVarillas) {
        int anchoBase = 120;
        int altoBase = 10;
        int anchoBloque = 80;
        int altoBloque = 40;
// Base
        g.setColor(new Color(100, 100, 100));
        g.fillRect(centroX - anchoBase / 2, altoPanel - 60, anchoBase, altoBase);
// Poste
        g.fillRect(centroX - 5, altoPanel - 260, 10, 200);
// Número
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Varillas " + numVarillas, centroX - 30, altoPanel - 30);
// Bloques - dibujar desde el índice 0 (fondo) hacia arriba
        int y = altoPanel - 70 - altoBase;
        for (int i = 0; i < colores.size(); i++) {
            dibujarBloque(g, centroX, y, anchoBloque, altoBloque, colores.get(i));
            y -= altoBloque + 5;
        }
    }

    /**
     * Dibuja un bloque individual con su color, borde, sombra y letra
     * identificadora.
     *
     * @param g contexto grafico 2D
     * @param centroX posicion horizontal del centro del bloque
     * @param y posicion vertical de referencia
     * @param ancho ancho del bloque
     * @param alto alto del bloque
     * @param colorCodigo codigo textual del color del bloque
     */
    private void dibujarBloque(Graphics2D g, int centroX, int y, int ancho, int alto, String colorCodigo) {
        Color color = obtenerColor(colorCodigo);
// Sombra
        g.setColor(new Color(0, 0, 0, 50));
        g.fillRoundRect(centroX - ancho / 2 + 3, y - alto + 3, ancho, alto, 10, 10);
// Bloque
        g.setColor(color);
        g.fillRoundRect(centroX - ancho / 2, y - alto, ancho, alto, 10, 10);
// Borde
        g.setColor(color.darker());
        g.setStroke(new BasicStroke(2));
        g.drawRoundRect(centroX - ancho / 2, y - alto, ancho, alto, 10, 10);
// Letra
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        String letra = colorCodigo.substring(0, 1);
        int anchoTexto = fm.stringWidth(letra);
        g.drawString(letra, centroX - anchoTexto / 2, y - alto / 2 + 7);
    }

    /**
     * Traduce un codigo de color a un objeto Color usado para pintar el bloque.
     *
     * @param codigo letra que representa el color
     * @return color asociado al codigo recibido
     */
    private Color obtenerColor(String codigo) {
        switch (codigo.toUpperCase()) {
            case "R":
                return new Color(220, 50, 50);
            case "V":
                return new Color(50, 180, 50);
            case "A":
                return new Color(50, 120, 220);
            case "M":
                return new Color(180, 50, 180);
            case "N":
                return new Color(255, 165, 0);
            case "Y":
                return new Color(220, 220, 50);
            default:
                return Color.GRAY;
        }
    }

    /**
     * Detiene la captura de consola y restaura la salida estandar original.
     */
    public void detener() {
        running = false;
        if (capturer != null) {
            capturer.restaurar();
        }
    }

    /**
     * Solicita la cantidad de bloques y ejecuta un movimiento desde la
     * interfaz.
     *
     * @param origen indice de la varilla origen
     * @param destino indice de la varilla destino
     */
    public void realizarMovimiento(int origen, int destino) {
        String respuesta = JOptionPane.showInputDialog(
                this,
                "¿Cuántos bloques quieres mover?",
                "Mover bloques",
                JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == null) {
            return;
        }

        int cuantos;

        try {
            cuantos = Integer.parseInt(respuesta.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Introduce un número válido");
            return;
        }

        boolean valido = juego.moverDisco(origen, destino, cuantos);

        if (!valido) {
            JOptionPane.showMessageDialog(
                    this,
                    "Movimiento no válido. Solo puedes mover bloques consecutivos del mismo color y si hay espacio suficiente."
            );
            return;
        }

        panelVarillas.repaint();

        if (juego.juegoCompletado()) {
            JOptionPane.showMessageDialog(this, "Has completado el juego");
        }
    }

    /**
     * Deshace el ultimo movimiento realizado y actualiza la representacion
     * grafica.
     */
    public void deshacerMovimiento() {
        juego.deshacer();
        juego.mostrarEstado();
        panelVarillas.repaint();
    }

    /**
     * Solicita confirmacion y cierra el juego de forma controlada.
     */
    public void salirDelJuego() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que quieres salir del juego?",
                "Salir",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            if (gestor != null) {
                gestor.detenerGrabacion();
            }

            detener();
            dispose();
            System.exit(0);
        }
    }
}
