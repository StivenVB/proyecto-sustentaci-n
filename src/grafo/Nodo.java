/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo;

/**
 *
 * @author CSS
 */
public class Nodo {

    private final int fila;
    private final int columna;
    private final Nodo padre;
    private final int costo;
    private final int heuristica;

    public Nodo(int fila, int columna, Nodo padre, int costo, int heuristica) {
        this.fila = fila;
        this.columna = columna;
        this.padre = padre;
        this.costo = costo;
        this.heuristica = heuristica;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public Nodo getPadre() {
        return padre;
    }

    public int getCosto() {
        return costo;
    }

    public int getHeuristica() {
        return heuristica;
    }

    public int getCostoTotal() {
        return costo + heuristica;
    }
}
