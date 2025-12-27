package marvel.vista;

import marvel.modelo.entidades.Habilidad;
import marvel.modelo.entidades.Personaje;
import marvel.modelo.entidades.Traje;
import marvel.modelo.enums.EstilosEnum;
import marvel.modelo.enums.MenuEnum;
import marvel.modelo.enums.ModificarHabilidadEnum;
import marvel.modelo.enums.ModificarPersonajeEnum;

import java.util.List;
import java.util.Scanner;

public class MarvelVista {
    private final Scanner sc = new Scanner(System.in);

    public void mostrarMenu() {
        MenuEnum[] opciones = MenuEnum.values();
        System.out.println("\n" + "\t".repeat(6) + EstilosEnum.AZUL.getFormato() + EstilosEnum.SUBRAYADO.getFormato() + "*** Menú de opciones ***" + EstilosEnum.RESET.getFormato() + "\n");
        for (int i = 1; i < opciones.length; i++) {
            System.out.println("\t".repeat(2) + i + ".\t" + EstilosEnum.VERDE.getFormato() + opciones[i].gettexto() + EstilosEnum.RESET.getFormato());
        }
        System.out.println("\t".repeat(2) + "0." + EstilosEnum.CYAN.getFormato() + "\tSalir." + EstilosEnum.RESET.getFormato());
    }

    public void mostrarMenuModificarPersonaje() {
        System.out.println("Opcion modificar Personaje");
        ModificarPersonajeEnum[] opciones = ModificarPersonajeEnum.values();
        for (int i = 1; i < opciones.length; i++) {
            System.out.println(i + ".-\t" + opciones[i].getTexto());
        }
        System.out.println("0.-  Volver al menú anterior.");
    }

    public void mostrarMenuModificarHabilidad() {
        System.out.println("Opcion modificar Habilidad");
        ModificarHabilidadEnum[] opciones = ModificarHabilidadEnum.values();
        for (int i = 1; i < opciones.length; i++) {
            System.out.println(i + ".-\t" + opciones[i].getTexto());
        }
        System.out.println("0.-  Volver al menú anterior.");
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

    public void mostrarPersonajes(List<Personaje> personajes, boolean id) {
        System.out.println("Personajes existentes:");
        int cont = 1;
        for (Personaje personaje : personajes) {
            System.out.println((id ? (cont + ".- ") : "") + personaje.getId() + " .- " + personaje.getNombre() + " (" + personaje.getAlias() + ")");
            cont++;
        }
    }


    public void mostrarHabilidades(List<Habilidad> habilidades, boolean id) {
        System.out.println("Lista de Habilidades.");

        int cont = 1;
        for (Habilidad habilidad : habilidades) {
            System.out.println((id ? (cont + ".- ") : "") + habilidad.getNombre() + " -> " + habilidad.getDescripcion());
            cont++;
        }
    }
}
