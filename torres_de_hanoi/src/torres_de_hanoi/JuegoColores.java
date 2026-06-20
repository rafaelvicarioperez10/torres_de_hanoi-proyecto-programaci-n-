/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package torres_de_hanoi;

import java.util.logging.Logger;

/**
 *
 * @author rafae
 */
public class JuegoColores {
// ATRIBUTOS

    private String[][] varillas;
    private int[] tamañovarillas;
    private final int capacidadMaxima = 4;
    private String[][][] historial = new String[100][3][capacidadMaxima];
    private int[][] historialTamaños = new int[100][3];
    private int contadorHistorial = 0;
    private int maxHistorial = 100;
// CONSTRUCTORES

    public JuegoColores() {
        varillas = new String[3][capacidadMaxima];
        tamañovarillas = new int[3];

    }

    public JuegoColores(String[][] varillas, int[] tamañovarillas) {
        this.varillas = varillas;
        this.tamañovarillas = tamañovarillas;
    }
// GETTERS Y SETTERS

    public String[][] getVarillas() {
        return varillas;
    }

    public int[] getTamañovarillas() {
        return tamañovarillas;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public String[][][] getHistorial() {
        return historial;
    }

    public int[][] getHistorialTamaños() {
        return historialTamaños;
    }

    public int getContadorHistorial() {
        return contadorHistorial;
    }

    public int getMaxHistorial() {
        return maxHistorial;
    }

    public void setVarillas(String[][] varillas) {
        this.varillas = varillas;
    }

    public void setTamañovarillas(int[] tamañovarillas) {
        this.tamañovarillas = tamañovarillas;
    }

    public void setHistorial(String[][][] historial) {
        this.historial = historial;
    }

    public void setHistorialTamaños(int[][] historialTamaños) {
        this.historialTamaños = historialTamaños;
    }

    public void setContadorHistorial(int contadorHistorial) {
        this.contadorHistorial = contadorHistorial;
    }

    public void setMaxHistorial(int maxHistorial) {
        this.maxHistorial = maxHistorial;
    }

// METODOS PRINCIPALES
    public JuegoColores(String[][] estadoinicial) {
        varillas = new String[3][capacidadMaxima];
        tamañovarillas = new int[3];
        if (estadoinicial != null) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < estadoinicial[i].length; j++) {
                    varillas[i][j] = estadoinicial[i][j];
                    tamañovarillas[i]++;
                }

            }
        } else {
            varillas[0] = new String[]{"R", "R", "V", "V"};
            varillas[1] = new String[]{"A", "A", null, null};
            varillas[2] = new String[]{"V", "R", null, null};
            tamañovarillas = new int[]{4, 2, 2};
        }
    }
    // Muestra por consola el estado actual de todas las varillas

    public void mostrarEstado() {
        System.out.println("\n--- Estado Actual---");
        for (int i = 0; i < 3; i++) {
            System.out.print("varilla" + (i + 1) + ": [");
            for (int j = 0; j < tamañovarillas[i]; j++) {
                System.out.print(varillas[i][j] + " ");
            }
            System.out.println("]");
        }

    }

    public void mover(int origen, int destino, int cuantos) {
        // mueve bloques del mismo color de la parte superior de la varilla 'origen'
        if (origen == destino || tamañovarillas[origen] == 0) {
            return;
        }
        guardarEstado(); // guarda el estado antes del movimiento
        String color = varillas[origen][tamañovarillas[origen] - 1];
        int bloques = 0;

        // contar cuántos bloques iguales se pueden mover
        for (int i = tamañovarillas[origen] - 1; i >= 0; i--) {
            if (varillas[origen][i].equals(color)) {
                bloques++;
            } else {
                break;
            }

        }
        int espacio = capacidadMaxima - tamañovarillas[destino];
        int mover = Math.min(cuantos, Math.min(bloques, espacio));
        if (mover == 0) {
            System.out.println("varilla destino llena");
            return;
        }
        // realiza el movimiento
        for (int i = 0; i < mover; i++) {
            varillas[destino][tamañovarillas[destino]++] = color;
            varillas[origen][--tamañovarillas[origen]] = null;
        }
        System.out.println("movidos" + mover + "bloque(s)" + color);

    }
// deshace el ultimo movimiento

    public void deshacer() {
        if (contadorHistorial == 0) {
            return;

        }
        contadorHistorial--;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < capacidadMaxima; j++) {
                varillas[i][j] = historial[contadorHistorial][i][j];
            }
            tamañovarillas[i] = historialTamaños[contadorHistorial][i];
        }
        System.out.println("movimiento deshecho");
    }
// guarda el estado actual en el historial

    public void guardarEstado() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < capacidadMaxima; j++) {
                historial[contadorHistorial][i][j] = varillas[i][j];
            }
            historialTamaños[contadorHistorial][i] = tamañovarillas[i];
        }
        contadorHistorial++;
    }

// comprueba si todos los bloques de una varilla son del mismo color
    private boolean todosiguales(int indiceVarilla) {
// si la varilla no tiene bloques, no cuenta como completa
        if (tamañovarillas[indiceVarilla] == 0) {
            return false;
        }

        String color = varillas[indiceVarilla][0];

        // Recorremos solo los bloques reales (ignorando null)
        for (int i = 1; i < tamañovarillas[indiceVarilla]; i++) {
            if (varillas[indiceVarilla][i] == null || !varillas[indiceVarilla][i].equals(color)) {
                return false;
            }
        }

        return true;
    }

    public boolean juegoCompletado() {
        for (int i = 0; i < 3; i++) {
            if (tamañovarillas[i] > 0 && !todosiguales(i)) {
                return false;
            }
        }
        return true;
    }

   public boolean moverDisco(int origen, int destino, int cuantos) {
    if (origen < 0 || origen > 2 || destino < 0 || destino > 2) {
        return false;
    }

    if (origen == destino) {
        return false;
    }

    if (cuantos <= 0) {
        return false;
    }

    if (tamañovarillas[origen] == 0) {
        return false;
    }

    if (tamañovarillas[destino] >= capacidadMaxima) {
        return false;
    }

    String color = varillas[origen][tamañovarillas[origen] - 1];
    int bloquesMismoColor = 0;

    for (int i = tamañovarillas[origen] - 1; i >= 0; i--) {
        if (varillas[origen][i].equals(color)) {
            bloquesMismoColor++;
        } else {
            break;
        }
    }

    int espacioDestino = capacidadMaxima - tamañovarillas[destino];

    if (cuantos > bloquesMismoColor) {
        return false;
    }

    if (cuantos > espacioDestino) {
        return false;
    }

    mover(origen, destino, cuantos);
    mostrarEstado();

    return true;
}

}
