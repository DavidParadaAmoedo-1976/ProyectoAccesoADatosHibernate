package marvel.vista;

import marvel.modelo.entidades.Traje;
import marvel.modelo.enums.EstilosEnum;
import marvel.modelo.enums.MenuEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MarvelVista {
    private final Scanner sc = new Scanner(System.in);

    public void mostrarMenu() {
        MenuEnum[] opciones = MenuEnum.values();
        System.out.println("\n" + "\t".repeat(6) + EstilosEnum.AZUL.getFormato() + EstilosEnum.SUBRAYADO.getFormato() + "*** Menú de opciones ***" + EstilosEnum.RESET.getFormato() + "\n");
        for (int i = 1; i < opciones.length; i++) {
            System.out.println("\t".repeat(2) + i + ".\t" + EstilosEnum.VERDE.getFormato() + opciones[i].getTexto() + EstilosEnum.RESET.getFormato());
        }
        System.out.println("\t".repeat(2) + "0." + EstilosEnum.CYAN.getFormato() + "\tSalir." + EstilosEnum.RESET.getFormato());
    }

    public String solicitarEntrada(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    public void mostrarSalida() {
        System.out.println("Saliendo del programa...");
    }

    public void mensajeError(String mensaje) {
        System.out.println(EstilosEnum.ROJO.getFormato() + mensaje + EstilosEnum.RESET.getFormato());
    }

    public void mensaje(String mensaje) {
        System.out.println(mensaje);
        System.out.println();
    }


    // Personaje

    public String pedirNombrePersonaje() {
        return solicitarEntrada("Nombre del personaje: ");
    }

    public String pedirAlias() {
        return solicitarEntrada("Alias: ");
    }

    public String pedirEspecificacionTraje() {
        return solicitarEntrada("Especificación del traje: ");
    }


    public void mostrarTrajesDisponibles(List<Traje> trajes) {
        System.out.println("Trajes disponibles:");

        if (trajes.isEmpty()) {
            mensajeError("(No hay trajes disponibles)");
        } else {
            for (Traje traje : trajes) {
                System.out.println(traje.getId() + " .- " + traje.getEspecificacion());
            }
        }
        System.out.println("0 .- Crear traje nuevo");
        System.out.println("Deja vacío si no quieres seleccionar traje.");
    }

}
