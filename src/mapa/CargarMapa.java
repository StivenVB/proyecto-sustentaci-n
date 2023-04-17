/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Stiven Vélez Bedoya
 * @author Cristian David Gómez Becerra
 */
public class CargarMapa {

    public char[][] cargarMapaDesdeArchivo(String rutaArchivo) throws IOException {

        BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo));
        String linea;
        int numeroFilas = 0;
        int numeroColumnas = 0;

        while ((linea = lector.readLine()) != null) {
            numeroFilas++;
            numeroColumnas = linea.length();
        }

        char[][] mapa = new char[numeroFilas][numeroColumnas];
        lector.close();

        lector = new BufferedReader(new FileReader(rutaArchivo));
        int fila = 0;

        while ((linea = lector.readLine()) != null) {
            String lineaSinComasEspacios = linea.replaceAll("\\s+", "").replace(",", ""); // Eliminar todos los espacios en blanco
            int columnaSinEspacios = 0; // Contador de columnas en la línea sin espacios en blanco

            for (int columna = 0; columna < numeroColumnas; columna++) {

                if (linea.charAt(columna) != ',') {
                    mapa[fila][columnaSinEspacios] = lineaSinComasEspacios.charAt(columnaSinEspacios);
                    columnaSinEspacios++; // Incrementar el contador de columnas en la línea sin espacios en blanco
                }

            }
            fila++;

        }

        /*for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (mapa[i][j] == 'M') {
                    mapa[i][j] = '#';
                }
                if (mapa[i][j] == 'C') {
                    mapa[i][j] = 'c';
                }
            }

        }*/
        return mapa;
    }

}
