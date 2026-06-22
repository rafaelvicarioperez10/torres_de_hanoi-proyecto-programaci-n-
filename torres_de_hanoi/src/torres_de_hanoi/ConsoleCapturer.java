/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package torres_de_hanoi;

import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Captura la salida de consola del programa para que pueda ser leida por la interfaz.
 *
 * Redirige temporalmente System.out a un flujo personalizado que guarda cada
 * linea escrita en una cola. El visualizador usa esas lineas para actualizar
 * el dibujo de las varillas.
 *
 * @author Rafael Vicario Pérez
 */
public class ConsoleCapturer {

    private PrintStream originalOut;
    private BlockingQueue<String> lineQueue;
    private CustomPrintStream customStream;

    /**
     * Crea el capturador y sustituye temporalmente la salida estandar.
     */
    public ConsoleCapturer() {
        lineQueue = new LinkedBlockingQueue<>();
        originalOut = System.out;
        customStream = new CustomPrintStream(originalOut, lineQueue);
        System.setOut(customStream);
    }

    /**
     * Lee la siguiente linea capturada de la consola.
     *
     * @return linea capturada, o null si el hilo es interrumpido
     */
    public String leerLinea() {
        try {
            return lineQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    /**
     * Restaura la salida estandar original del programa.
     */
    public void restaurar() {
        System.setOut(originalOut);
    }

    private static class CustomPrintStream extends PrintStream {

        private BlockingQueue<String> queue;
        private StringBuilder buffer = new StringBuilder();

        /**
         * Crea un flujo personalizado que escribe en consola y guarda las lineas.
         *
         * @param original flujo de salida original
         * @param queue cola donde se almacenan las lineas capturadas
         */
        public CustomPrintStream(PrintStream original, BlockingQueue<String> queue) {
            super(original, true); // autoflush
            this.queue = queue;
        }

        @Override
        /**
         * Escribe texto y extrae lineas completas para enviarlas a la cola.
         *
         * @param s texto que se quiere imprimir
         */
        public void print(String s) {
            super.print(s);
            if (s == null) {
                return;
            }
            buffer.append(s);
            int index;
            while ((index = buffer.indexOf("\n")) >= 0) {
                String line = buffer.substring(0, index).trim();
                buffer.delete(0, index + 1);
                if (!line.isEmpty()) {
                    try {
                        queue.put(line);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        @Override
        /**
         * Imprime una cadena terminada en salto de linea.
         *
         * @param s texto que se quiere imprimir
         */
        public void println(String s) {
            print((s != null ? s : "null") + "\n");
        }

        @Override
        /**
         * Imprime un objeto terminado en salto de linea.
         *
         * @param obj objeto que se quiere imprimir
         */
        public void println(Object obj) {
            print((obj != null ? obj.toString() : "null") + "\n");
        }
    }
}
