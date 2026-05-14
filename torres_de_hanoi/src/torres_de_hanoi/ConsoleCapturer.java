/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package torres_de_hanoi;

import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author rafae
 */
public class ConsoleCapturer {

    private PrintStream originalOut;
    private BlockingQueue<String> lineQueue;
    private CustomPrintStream customStream;

    public ConsoleCapturer() {
        lineQueue = new LinkedBlockingQueue<>();
        originalOut = System.out;
        customStream = new CustomPrintStream(originalOut, lineQueue);
        System.setOut(customStream);
    }

    public String leerLinea() {
        try {
            return lineQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public void restaurar() {
        System.setOut(originalOut);
    }

    private static class CustomPrintStream extends PrintStream {

        private BlockingQueue<String> queue;
        private StringBuilder buffer = new StringBuilder();

        public CustomPrintStream(PrintStream original, BlockingQueue<String> queue) {
            super(original, true); // autoflush
            this.queue = queue;
        }

        @Override
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
        public void println(String s) {
            print((s != null ? s : "null") + "\n");
        }

        @Override
        public void println(Object obj) {
            print((obj != null ? obj.toString() : "null") + "\n");
        }
    }
}
