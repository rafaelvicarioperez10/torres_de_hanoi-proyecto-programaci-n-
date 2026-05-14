/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package torres_de_hanoi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.FileWriter;
import java.io.FileReader;

/**
 *
 * @author rafae
 */
public class GestorArchivos {

    private PrintWriter escritor;

    public void iniciarGrabacion(String ruta) {
        try {
            escritor = new PrintWriter(new FileWriter(ruta));
            System.out.println("Grabación iniciada en " + ruta);
        } catch (IOException e) {
            System.out.println("Error al iniciar la grabación");
        }

    }

    public void escribir(String texto) {
        if (escritor != null) {
            escritor.println(texto);
        }
    }

    public void detenerGrabacion() {
        if (escritor != null) {
            escritor.close();
            System.out.println("Grabación finalizada.");
        }
    }

    public String[][] cargar(String nombre) {
        String[][] estado = new String[3][];
        try ( BufferedReader br = new BufferedReader(new FileReader(nombre))) {
            for (int i = 0; i < 3; i++) {
                estado[i] = br.readLine().split(" ");
            }
        } catch (Exception e) {
            return null;
        }
        return estado;
    }

}
