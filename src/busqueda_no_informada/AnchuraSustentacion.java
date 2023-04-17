/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda_no_informada;

import grafo.Nodo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author Stiven Vélez Bedoya
 * @author Cristian David Gómez Becerra
 */
public class AnchuraSustentacion {

    public char[][] busquedaAltura(char[][] mapa, int filaInicio, int columnaInicio, int filaFin, int columnaFin) {

        int filas = mapa.length;
        int columnas = mapa[0].length;
        boolean[][] visitados = new boolean[filas][columnas];
        mapa[filaInicio][columnaInicio] = 'V';
        // Definir movimientos arriba y abajo
        int[] direccionesFilas = {-1, 1, 0, 0};
        int[] direccionesColumnas = {0, 0, -1, 1};
        int count = 0;
        // Crear cola de prioridad para almacenar los nodos abiertos
        PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingInt(Nodo::getCostoTotal));

        Nodo nodoInicio = new Nodo(filaInicio, columnaInicio, null, 0, 0);
        cola.offer(nodoInicio);
        visitados[filaInicio][columnaInicio] = true;

        while (!cola.isEmpty()) {
            List<Nodo> candidatos = new ArrayList<>();

            // Obtener los siguientes nodos a expandir en función del beam width
            int size = Math.min(4, cola.size());
            for (int i = 0; i < size; i++) {
                Nodo nodo = cola.poll();

                int fila = nodo.getFila();
                int columna = nodo.getColumna();

                // Verificar si se llegó a la posición de destino
                if (fila == filaFin && columna == columnaFin) {
                    while (nodo != null) {
                        count++;
                        nodo = nodo.getPadre();
                    }

                    System.out.println(count - 1);
                    return mapa;
                }

                // Explorar los vecinos arriba, abajo, izquierda, derecha
                for (int j = 0; j < 4; j++) {
                    int nuevaFila = fila + direccionesFilas[j];
                    int nuevaColumna = columna + direccionesColumnas[j];

                    // Verificar si el vecino está dentro de los límites del mapa
                    // y si es un camino 'C' y no ha sido visitado
                    if (nuevaFila >= 0 && nuevaFila < filas && nuevaColumna >= 0 && nuevaColumna < columnas
                            && mapa[nuevaFila][nuevaColumna] == 'C' && !visitados[nuevaFila][nuevaColumna]) {

                        Nodo newNodo = new Nodo(nuevaFila, nuevaColumna, nodo, 0, 0);
                        candidatos.add(newNodo);
                        visitados[nuevaFila][nuevaColumna] = true;
                        mapa[nuevaFila][nuevaColumna] = 'V';

                    }
                }
            }

            // Ordenar los nodos candidatos por costo total y agregarlos a la cola de prioridad
            candidatos.sort(Comparator.comparingInt(Nodo::getCostoTotal));
            for (int i = 0; i < candidatos.size() && i < 4; i++) {
                cola.offer(candidatos.get(i));
            }

        }

        return null;
    }
}
