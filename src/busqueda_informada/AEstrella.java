/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busqueda_informada;

import grafo.Nodo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author Stiven Vélez Bedoya
 * @author Cristian David Gómez Becerra
 */
public class AEstrella {

    ArrayList<String> coordenadas = new ArrayList<>();

    public char[][] aEstrellaManhattan(char[][] mapa, int filaInicio, int columnaInicio, int filaFin, int columnaFin) {
        int filas = mapa.length;
        int columnas = mapa[0].length;
        boolean[][] visitados = new boolean[filas][columnas];
        mapa[filaInicio][columnaInicio] = 'V';
        // Definir movimientos arriba, abajo, izquierda, derecha
        int[] direccionesFila = {-1, 1, 0, 0};
        int[] direccionesColumna = {0, 0, -1, 1};
        coordenadas.add("(" + filaInicio + "," + columnaInicio + ")");
        // Crear cola de prioridad para almacenar los nodos abiertos
        PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingInt(Nodo::getCostoTotal));

        Nodo nodoInicio = new Nodo(filaInicio, columnaInicio, null, 0, heuristicManhattan(filaInicio, columnaInicio, filaFin, columnaFin));
        cola.offer(nodoInicio);
        visitados[filaInicio][columnaInicio] = true;

        while (!cola.isEmpty()) {
            Nodo nodo = cola.poll();

            int fila = nodo.getFila();
            int columna = nodo.getColumna();

            // Verificar si se llegó a la posición de destino
            if (fila == filaFin && columna == columnaFin) {
                // Marcar la ruta tomada con la letra 'P'
                while (nodo != null) {
                    mapa[nodo.getFila()][nodo.getColumna()] = 'P';
                    nodo = nodo.getPadre();
                }
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

            // Explorar los vecinos arriba, abajo, izquierda, derecha
            for (int j = 0; j < 4; j++) {
                int nuevaFila = fila + direccionesFila[j];
                int nuevaColumna = columna + direccionesColumna[j];

                // Verificar si el vecino está dentro de los límites del mapa
                // y si es un camino 'C' y no ha sido visitado
                if (nuevaFila >= 0 && nuevaFila < filas && nuevaColumna >= 0 && nuevaColumna < columnas
                        && mapa[nuevaFila][nuevaColumna] == 'C' && !visitados[nuevaFila][nuevaColumna]) {
                    int costoMovimiento = mapa[nuevaFila][nuevaColumna] == 'C' ? 1 : 3; // Actualizar costo de movimiento
                    int g = nodo.getCosto() + costoMovimiento; // Costo del movimiento
                    int h = heuristicManhattan(nuevaFila, nuevaColumna, filaFin, columnaFin); // Heurística de Manhattan
                    int f = g + h; // Costo total (g + h)
                    Nodo nuevoNodo = new Nodo(nuevaFila, nuevaColumna, nodo, g, f);
                    cola.offer(nuevoNodo);
                    visitados[nuevaFila][nuevaColumna] = true;
                    mapa[nuevaFila][nuevaColumna] = 'V'; // Marcar como visitado
                    if (!coordenadas.contains("(" + filaFin + "," + columnaFin + ")")) {
                        coordenadas.add("(" + nuevaFila + "," + nuevaColumna + ")");
                    }

                }
            }
        }

        return null;
    }

    public static int heuristicManhattan(int row, int col, int destRow, int destCol) {
        // Heurística de Manhattan: la distancia en líneas rectas (sin considerar diagonales)
        return Math.abs(row - destRow) + Math.abs(col - destCol);
    }

    public char[][] aEstrellaEuclidiana(char[][] mapa, int filaInicio, int columnaInicio, int filaFin, int columnaFin) {
        int filas = mapa.length;
        int columnas = mapa[0].length;
        boolean[][] visitados = new boolean[filas][columnas];
        mapa[filaInicio][columnaInicio] = 'V';
        // Definir movimientos arriba, abajo, izquierda, derecha
        int[] direccionesFila = {-1, 1, 0, 0};
        int[] direccionesColumna = {0, 0, -1, 1};
        coordenadas.add("(" + filaInicio + "," + columnaInicio + ")");

        // Crear cola de prioridad para almacenar los nodos abiertos
        PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingInt(Nodo::getCostoTotal));

        Nodo nodoInicio = new Nodo(filaInicio, columnaInicio, null, 0, heuristicEuclidiana(filaInicio, columnaInicio, filaFin, columnaFin));
        cola.offer(nodoInicio);
        visitados[filaInicio][columnaInicio] = true;

        while (!cola.isEmpty()) {
            Nodo nodo = cola.poll();

            int fila = nodo.getFila();
            int columna = nodo.getColumna();

            // Verificar si se llegó a la posición de destino
            if (fila == filaFin && columna == columnaFin) {
                // Marcar la ruta tomada con la letra 'P'
                while (nodo != null) {
                    mapa[nodo.getFila()][nodo.getColumna()] = 'P';
                    nodo = nodo.getPadre();
                }
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

            // Explorar los vecinos arriba, abajo, izquierda, derecha
            for (int j = 0; j < 4; j++) {
                int nuevaFila = fila + direccionesFila[j];
                int nuevaColumna = columna + direccionesColumna[j];

                // Verificar si el vecino está dentro de los límites del mapa
                // y si es un camino 'C' y no ha sido visitado
                if (nuevaFila >= 0 && nuevaFila < filas && nuevaColumna >= 0 && nuevaColumna < columnas
                        && mapa[nuevaFila][nuevaColumna] == 'C' && !visitados[nuevaFila][nuevaColumna]) {
                    int costoMovimiento = mapa[nuevaFila][nuevaColumna] == 'C' ? 1 : 3; // Actualizar costo de movimiento
                    int g = nodo.getCosto() + costoMovimiento; // Costo del movimiento
                    int h = heuristicEuclidiana(nuevaFila, nuevaColumna, filaFin, columnaFin); // Heurística euclidiana
                    int f = g + h; // Costo total (g + h)
                    Nodo nuevoNodo = new Nodo(nuevaFila, nuevaColumna, nodo, g, f);
                    cola.offer(nuevoNodo);
                    visitados[nuevaFila][nuevaColumna] = true;
                    mapa[nuevaFila][nuevaColumna] = 'V'; // Marcar como visitado
                    if (!coordenadas.contains("(" + filaFin + "," + columnaFin + ")")) {
                        coordenadas.add("(" + nuevaFila + "," + nuevaColumna + ")");
                    }

                }
            }
        }

        return null;
    }

    private int heuristicEuclidiana(int filaActual, int columnaActual, int filaDestino, int columnaDestino) {
        // Distancia euclidiana entre la posición actual y la posición destino
        int distanciaFila = Math.abs(filaActual - filaDestino);
        int distanciaColumna = Math.abs(columnaActual - columnaDestino);
        return (int) Math.sqrt(distanciaFila * distanciaFila + distanciaColumna * distanciaColumna);
    }
}
