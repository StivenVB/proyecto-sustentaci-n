/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda_no_informada;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * @author Stiven Vélez Bedoya
 * @author Cristian David Gómez Becerra
 */
public class CostoUniforme {

    private static final int[][] DIRECCIONES = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public char[][] busquedaCostoUniforme(char[][] mapa, int[] coordenadaInicial, int[] coordenadaFinal) {
        // Cola de prioridad para elegir el nodo con menor costo
        PriorityQueue<int[]> colaPrioridad = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        ArrayList<String> coordenadas = new ArrayList<>();
        // Mapa para almacenar los nodos visitados y sus costos
        Map<int[], Integer> visitados = new HashMap<>();

        int filas = mapa.length;
        int columnas = mapa[0].length;

        // Añadir el nodo inicial a la cola de prioridad
        colaPrioridad.offer(new int[]{coordenadaInicial[0], coordenadaInicial[1], 0});
        // Mientras haya nodos en la cola de prioridad
        while (!colaPrioridad.isEmpty()) {
            // Sacar el nodo con menor costo de la cola
            int[] actual = colaPrioridad.poll();
            int fila = actual[0];
            int columna = actual[1];
            int costo = actual[2];

            // Marcar el nodo actual como visitado
            visitados.put(new int[]{fila, columna}, costo);
            mapa[fila][columna] = 'V';
            if (!coordenadas.contains("(" + coordenadaFinal[0] + "," + coordenadaFinal[1] + ")")) {
                coordenadas.add("(" + fila + "," + columna + ")");
            }
            // Si el nodo actual es el final, construir y devolver el camino
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
                return mapa;
            }

            // Explorar los nodos vecinos del nodo actual
            for (int[] direccion : DIRECCIONES) {
                int nuevaFila = fila + direccion[0];
                int nuevaColumna = columna + direccion[1];
                // Si el nodo vecino no está fuera del mapa, no es un obstáculo y no ha sido visitado
                if (nuevaFila >= 0 && nuevaFila < filas && nuevaColumna >= 0 && nuevaColumna < columnas
                        && mapa[nuevaFila][nuevaColumna] != 'R' && mapa[nuevaFila][nuevaColumna] != 'M' && mapa[nuevaFila][nuevaColumna] != 'V') {
                    int nuevoCosto = costo + 1; // Costo de llegar al nodo vecino desde el nodo actual
                    int[] nuevaCoordenada = new int[]{nuevaFila, nuevaColumna};
                    // Si el nodo vecino no ha sido visitado o el nuevo costo es menor que el costo anterior
                    if (!visitados.containsKey(nuevaCoordenada) || nuevoCosto < visitados.get(nuevaCoordenada)) {
                        int[] nuevoNodo = {nuevaFila, nuevaColumna, nuevoCosto};
                        colaPrioridad.offer(nuevoNodo); // Añadir el nodo vecino a la cola de prioridad
                    }

                }
            }
        }

        // Si no se encontró un camino al nodo final, devolver una lista vacía
        return new char[0][0];
    }
}
