/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda_informada;

import grafo.Nodo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author Stiven Vélez Bedoya
 * @author Cristian David Gómez Becerra
 */
public class BeamSearch {

    final int MAXIMO_HIJOS = 4;
    ArrayList<String> coordenadas = new ArrayList<>();

    /**
     *
     * @param mapa
     * @param filaInicio
     * @param columaInicio
     * @param filaFin
     * @param columnaFin
     * @return
     */
    public char[][] beamSearchManhattan(char[][] mapa, int filaInicio, int columnaInicio, int filaFin, int columnaFin) {

        int filas = mapa.length;
        int columnas = mapa[0].length;
        boolean[][] visitados = new boolean[filas][columnas];
        mapa[filaInicio][columnaInicio] = 'V';
        coordenadas.add("(" + filaInicio + "," + columnaInicio + ")");
        // Definir movimientos arriba y abajo
        int[] direccionesFilas = {-1, 1, 0, 0};
        int[] direccionesColumnas = {0, 0, -1, 1};
        // Crear cola de prioridad para almacenar los nodos abiertos
        PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingInt(Nodo::getCostoTotal));

        Nodo nodoInicio = new Nodo(filaInicio, columnaInicio, null, 0, heuristicaManhattan(filaInicio, columnaInicio, filaFin, columnaFin));
        cola.offer(nodoInicio);
        visitados[filaInicio][columnaInicio] = true;

        while (!cola.isEmpty()) {
            List<Nodo> candidatos = new ArrayList<>();

            // Obtener los siguientes nodos a expandir en función del beam width
            int size = Math.min(MAXIMO_HIJOS, cola.size());
            for (int i = 0; i < size; i++) {
                Nodo nodo = cola.poll();

                int fila = nodo.getFila();
                int columna = nodo.getColumna();

                // Verificar si se llegó a la posición de destino
                if (fila == filaFin && columna == columnaFin) {
                    while (nodo != null) {
                        mapa[nodo.getFila()][nodo.getColumna()] = 'P';
                        nodo = nodo.getPadre();
                    }
                    String respuesta = "";
                    for (int k = 0; k < coordenadas.size(); k++) {
                        if (k == coordenadas.size() - 1) {
                            respuesta += coordenadas.get(k);
                        } else {
                            respuesta += coordenadas.get(k) + ",";
                        }

                    }
                    System.out.println("[" + respuesta + "]");
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

                        Nodo newNodo = new Nodo(nuevaFila, nuevaColumna, nodo, nodo.getCosto() + heuristicaManhattan(nuevaFila, nuevaColumna, filaFin, columnaFin), heuristicaManhattan(nuevaFila, nuevaColumna, filaFin, columnaFin));
                        candidatos.add(newNodo);
                        visitados[nuevaFila][nuevaColumna] = true;
                        mapa[nuevaFila][nuevaColumna] = 'V';
                        if (!coordenadas.contains("(" + filaFin + "," + columnaFin + ")")) {
                            coordenadas.add("(" + nuevaFila + "," + nuevaColumna + ")");
                        }

                    }
                }
            }

            // Ordenar los nodos candidatos por costo total y agregarlos a la cola de prioridad
            candidatos.sort(Comparator.comparingInt(Nodo::getCostoTotal));
            for (int i = 0; i < candidatos.size() && i < MAXIMO_HIJOS; i++) {
                cola.offer(candidatos.get(i));
            }

        }

        return null;
    }

    /**
     *
     * @param fila1
     * @param columna1
     * @param fila2
     * @param columna2
     * @return
     */
    private int heuristicaManhattan(int fila1, int columna1, int fila2, int columna2) {
        // Heurística de distancia de Manhattan
        return Math.abs(fila1 - fila2) + Math.abs(columna1 - columna2);
    }

    public char[][] beamSearchEuclidiana(char[][] mapa, int filaInicio, int columnaInicio, int filaFin, int columnaFin) {

        int filas = mapa.length;
        int columnas = mapa[0].length;
        boolean[][] visitados = new boolean[filas][columnas];
        mapa[filaInicio][columnaInicio] = 'V';
        coordenadas.add("(" + filaInicio + "," + columnaInicio + ")");
        // Definir movimientos arriba y abajo
        int[] direccionesFilas = {-1, 1, 0, 0};
        int[] direccionesColumnas = {0, 0, -1, 1};

        // Crear cola de prioridad para almacenar los nodos abiertos
        PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingInt(Nodo::getCostoTotal));

        Nodo nodoInicio = new Nodo(filaInicio, columnaInicio, null, 0, heuristicaEuclidiana(filaInicio, columnaInicio, filaFin, columnaFin));
        cola.offer(nodoInicio);
        visitados[filaInicio][columnaInicio] = true;

        while (!cola.isEmpty()) {
            List<Nodo> candidatos = new ArrayList<>();

            // Obtener los siguientes nodos a expandir en función del beam width
            int size = Math.min(MAXIMO_HIJOS, cola.size());
            for (int i = 0; i < size; i++) {
                Nodo nodo = cola.poll();

                int fila = nodo.getFila();
                int columna = nodo.getColumna();

                // Verificar si se llegó a la posición de destino
                if (fila == filaFin && columna == columnaFin) {
                    while (nodo != null) {
                        mapa[nodo.getFila()][nodo.getColumna()] = 'P';
                        nodo = nodo.getPadre();
                    }
                    String respuesta = "";
                    for (int k = 0; k < coordenadas.size(); k++) {
                        if (k == coordenadas.size() - 1) {
                            respuesta += coordenadas.get(k);
                        } else {
                            respuesta += coordenadas.get(k) + ",";
                        }

                    }
                    System.out.println("[" + respuesta + "]");
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

                        Nodo newNodo = new Nodo(nuevaFila, nuevaColumna, nodo, nodo.getCosto() + heuristicaEuclidiana(nuevaFila, nuevaColumna, filaFin, columnaFin), heuristicaEuclidiana(nuevaFila, nuevaColumna, filaFin, columnaFin));
                        candidatos.add(newNodo);
                        visitados[nuevaFila][nuevaColumna] = true;
                        mapa[nuevaFila][nuevaColumna] = 'V';
                        if (!coordenadas.contains("(" + filaFin + "," + columnaFin + ")")) {
                            coordenadas.add("(" + nuevaFila + "," + nuevaColumna + ")");
                        }

                    }
                }
            }

            // Ordenar los nodos candidatos por costo total y agregarlos a la cola de prioridad
            candidatos.sort(Comparator.comparingInt(Nodo::getCostoTotal));
            for (int i = 0; i < candidatos.size() && i < MAXIMO_HIJOS; i++) {
                cola.offer(candidatos.get(i));
            }

        }

        return mapa;
    }

    private int heuristicaEuclidiana(int fila, int columna, int filaFin, int columnaFin) {
        int distanciaFila = Math.abs(fila - filaFin);
        int distanciaColumna = Math.abs(columna - columnaFin);
        return (int) Math.sqrt(distanciaFila * distanciaColumna + distanciaColumna * distanciaColumna);
    }

}
