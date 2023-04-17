/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda_informada;

import java.util.ArrayList;

/**
 *
 * @author Stiven Vélez Bedoya
 * @author Cristian David Gómez Becerra
 */
public class HillClimbing {

    //Movimientos ortogonales: derecha, izquierda, abajo y arriba
    private static final int[][] DIRECCIONES = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    ArrayList<String> coordenadas = new ArrayList<>();

    public char[][] busquedaManhattan(char[][] mapa, int[] coordenadaInicial, int[] coordenadaFinal) {
        int filas = mapa.length;
        int columnas = mapa[0].length;

        int fila = coordenadaInicial[0];
        int columna = coordenadaInicial[1];
        mapa[fila][columna] = 'V';

        coordenadas.add("(" + coordenadaInicial[0] + "," + coordenadaInicial[1] + ")");

        while (true) {
            int mejorFila = -1;
            int mejorColumna = -1;
            int mejorDistancia = Integer.MAX_VALUE;

            for (int[] direccion : DIRECCIONES) {
                int nuevaFila = fila + direccion[0];
                int nuevaColumna = columna + direccion[1];

                // Si el nodo vecino no está fuera del mapa, no es un obstáculo y no ha sido visitado
                if (nuevaFila < 0 || nuevaFila >= filas || nuevaColumna < 0 || nuevaColumna >= columnas
                        || mapa[nuevaFila][nuevaColumna] == 'R' || mapa[nuevaFila][nuevaColumna] == 'M' || mapa[nuevaFila][nuevaColumna] == 'V') {
                    continue;
                }

                //Se calcula la distancia del nodo actual hacia la meta usando la formula de la heuristica de Manhattan
                int distancia = Math.abs(nuevaFila - coordenadaFinal[0]) + Math.abs(nuevaColumna - coordenadaFinal[1]);

                // Si la distancia calculada es menor a la mejor distancia actual
                if (distancia < mejorDistancia) {
                    mejorFila = nuevaFila;
                    mejorColumna = nuevaColumna;
                    mejorDistancia = distancia;
                }
            }

            if (mejorDistancia == Integer.MAX_VALUE) {
                return null;
            }

            fila = mejorFila;
            columna = mejorColumna;
            mapa[fila][columna] = 'V';
            if (!coordenadas.contains("(" + coordenadaFinal[0] + "," + coordenadaFinal[1] + ")")) {
                coordenadas.add("(" + fila + "," + columna + ")");
            }
            // Si se ha llegado a la meta se rompe el bucle
            if (fila == coordenadaFinal[0] && columna == coordenadaFinal[1]) {
                String respuesta = "";
                for (int i = 0; i < coordenadas.size(); i++) {
                    if (i == coordenadas.size() - 1) {
                        respuesta += coordenadas.get(i);
                    } else {
                        respuesta += coordenadas.get(i) + ",";
                    }

                }
                System.out.println("[" + respuesta + "]");
                break;
            }
        }

        return mapa;
    }

    public char[][] busquedaEuclidiana(char[][] mapa, int[] coordenadaInicial, int[] coordenadaFinal) {
        int filas = mapa.length;
        int columnas = mapa[0].length;

        int fila = coordenadaInicial[0];
        int columna = coordenadaInicial[1];
        mapa[fila][columna] = 'V';
        coordenadas.add("(" + coordenadaInicial[0] + "," + coordenadaInicial[1] + ")");

        while (true) {
            int mejorFila = -1;
            int mejorColumna = -1;
            double mejorDistancia = Double.MAX_VALUE;

            for (int[] direccion : DIRECCIONES) {
                int nuevaFila = fila + direccion[0];
                int nuevaColumna = columna + direccion[1];

                // Si el nodo vecino no está fuera del mapa, no es un obstáculo y no ha sido visitado
                if (nuevaFila < 0 || nuevaFila >= filas || nuevaColumna < 0 || nuevaColumna >= columnas
                        || mapa[nuevaFila][nuevaColumna] == 'R' || mapa[nuevaFila][nuevaColumna] == 'M' || mapa[nuevaFila][nuevaColumna] == 'V') {
                    continue;
                }

                //Se calcula la distancia del nodo actual hacia la meta usando la formula de la heuristica Euclidiana
                double distancia = Math.sqrt(Math.pow(nuevaFila - coordenadaFinal[0], 2) + Math.pow(nuevaColumna - coordenadaFinal[1], 2));

                if (distancia < mejorDistancia) {
                    mejorFila = nuevaFila;
                    mejorColumna = nuevaColumna;
                    mejorDistancia = distancia;
                }
            }

            if (mejorDistancia == Double.MAX_VALUE) {
                return null;
            }

            fila = mejorFila;
            columna = mejorColumna;
            mapa[fila][columna] = 'V';
            if (!coordenadas.contains("(" + coordenadaFinal[0] + "," + coordenadaFinal[1] + ")")) {
                coordenadas.add("(" + fila + "," + columna + ")");
            }

            // Si se ha llegado a la meta se rompe el bucle
            if (fila == coordenadaFinal[0] && columna == coordenadaFinal[1]) {
                String respuesta = "";
                for (int i = 0; i < coordenadas.size(); i++) {
                    if (i == coordenadas.size() - 1) {
                        respuesta += coordenadas.get(i);
                    } else {
                        respuesta += coordenadas.get(i) + ",";
                    }

                }
                System.out.println("[" + respuesta + "]");
                break;
            }
        }

        return mapa;
    }
}
