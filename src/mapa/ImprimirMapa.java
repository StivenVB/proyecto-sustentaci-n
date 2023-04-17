/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapa;

/**
 *
 * @author Stiven Vélez Bedoya
 * @author Cristian David Gómez Becerra
 */
public class ImprimirMapa {

    public void imprimirMapa(char[][] mapa) {
        for (char[] fila : mapa) {
            System.out.print("[");
            for (char columna : fila) {
                System.out.print(columna);
            }
            System.out.println("]");
        }
    }

}
