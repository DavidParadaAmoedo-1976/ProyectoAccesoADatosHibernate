package marvel.vista;

import marvel.modelo.entidades.*;
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
        System.out.println("0.-\tVolver al menú anterior.");
    }

    public void mostrarMenuModificarHabilidad() {
        System.out.println("Opcion modificar Habilidad");
        ModificarHabilidadEnum[] opciones = ModificarHabilidadEnum.values();
        for (int i = 1; i < opciones.length; i++) {
            System.out.println(i + ".-\t" + opciones[i].getTexto());
        }
        System.out.println("0.-\tVolver al menú anterior.");
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

        for (Traje traje : trajes) {
            System.out.println(traje.getId() + " .- " + traje.getEspecificacion());
        }
        System.out.println("0.- Crear traje nuevo");
        System.out.println("1.- Seleccionar un traje");
        System.out.println("Deja vacío si no quieres seleccionar traje.");
    }

    public void mostrarPersonajes(List<Personaje> personajes, boolean mostrarId) {
        System.out.println("Personajes existentes:");
        int cont = 1;
        for (Personaje personaje : personajes) {
            System.out.println((mostrarId ? (cont + ".- ") : "") + personaje.getNombre() + " (" + personaje.getAlias() + ")");
            cont++;
        }
    }


    public void mostrarHabilidades(List<Habilidad> habilidades, boolean mostrarId) {
        System.out.println("Lista de Habilidades.");

        int cont = 1;
        for (Habilidad habilidad : habilidades) {
            System.out.println((mostrarId ? (cont + ".- ") : "") + habilidad.getNombre() + " -> " + habilidad.getDescripcion());
            cont++;
        }
    }

    public void mostrarMenuEventos() {
        System.out.println("""
                            Menu de opciones
                1.- Crear evento nuevo.
                2.- Seleccionar un evento.
                0.- Salir al menú principal.
                """);
    }

    public void mostrarEventos(List<Evento> eventos, boolean mostrarId) {
        System.out.println("Lista de eventos\n");
        int cont = 1;
        for (Evento evento : eventos) {
            System.out.println((mostrarId ? (cont + ".- ") : "") + evento.getNombre() + "\t->" + evento.getLugar());
        }
    }

    public void mostrarDatosPersonaje(Personaje personaje) {

        System.out.println();
        System.out.println("=================================");
        System.out.println("   DATOS DEL PERSONAJE");
        System.out.println("=================================");

        System.out.println("Nombre: " + personaje.getNombre());
        System.out.println("Alias: " + personaje.getAlias());

        // Traje
        System.out.print("Traje: ");
        if (personaje.getTraje() != null) {
            System.out.println(personaje.getTraje().getEspecificacion());
        } else {
            System.out.println("Sin traje");
        }

        // Habilidades
        System.out.println("\nHabilidades:");
        if (personaje.getHabilidades() == null || personaje.getHabilidades().isEmpty()) {
            System.out.println("- No tiene habilidades asignadas");
        } else {
            for (Habilidad h : personaje.getHabilidades()) {
                System.out.println("- " + h.getNombre());
            }
        }

        if (personaje.getParticipaciones() != null) {
            System.out.println("\nEventos:");
            if (personaje.getParticipaciones().isEmpty()) {
                System.out.println("- No participa en ningún evento");
            } else {
                for (Participa p : personaje.getParticipaciones()) {
                    System.out.println("- "
                            + p.getEvento().getNombre()
                            + " | Rol: " + p.getRol()
                            + " | Fecha: " + p.getFecha());
                }
            }
        }

        System.out.println("=================================");
        System.out.println();
    }

    public void mostrarPersonajesDeUnEvento(List<Personaje> personajes) {
        System.out.println("En este evento participaron:");
        for (Personaje personaje: personajes){
            System.out.println("- " + personaje.getNombre());
        }
    }
}
