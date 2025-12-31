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
    }

    public void menuSeleccionarTraje() {
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
            cont++;
        }
    }

    public void mostrarDatosPersonaje(Personaje personaje) {

        System.out.print("\t".repeat(5) + " \u250C" + "\u2500".repeat(79) + "\u2510");
        System.out.printf("\n" + "\t".repeat(5) + " \u2502" + EstilosEnum.AMARILLO.getFormato() + " %-77s " + EstilosEnum.RESET.getFormato() + "\u2502", "                             DATOS DEL PERSONAJE");
        System.out.print("\n" + "\t".repeat(5) + " \u251C" + "\u2500".repeat(15)+ "\u252C" + "\u2500".repeat(63) +  "\u2524");


        System.out.printf("\n" + "\t".repeat(5) + " \u2502 %-13s \u2502 %-61s \u2502", "Nombre: ", personaje.getNombre());
        System.out.printf("\n" + "\t".repeat(5) + " \u2502 %-13s \u2502 %-61s \u2502","Alias: ", personaje.getAlias());

        if (personaje.getTraje() != null) {
            System.out.printf("\n" + "\t".repeat(5) + " \u2502 %-13s \u2502 %-61s \u2502","Traje: ",personaje.getTraje().getEspecificacion());
        } else {
            System.out.printf("\n" + "\t".repeat(5) + " \u2502 %-13s \u2502 %-61s \u2502","Traje: ","Ningún traje asignado");
        }

        System.out.printf("\n" + "\t".repeat(5) + " \u251C" + "\u2500".repeat(15) + "\u2534" + "\u2500".repeat(63) + "\u2524");
        System.out.printf("\n" + "\t".repeat(5) + " \u2502" + EstilosEnum.AMARILLO.getFormato() + " %-77s " + EstilosEnum.RESET.getFormato() + "\u2502", "                           HABILIDADES");
        System.out.print("\n" + "\t".repeat(5) + " \u251C" + "\u2500".repeat(79) +  "\u2524");
        // Habilidades
        if (personaje.getHabilidades() == null || personaje.getHabilidades().isEmpty()) {
            System.out.printf("\n" + "\t".repeat(5) + " \u2502 %-77s \u2502", "No tiene habilidades asignadas");
        } else {
            for (Habilidad h : personaje.getHabilidades()) {
                System.out.printf("\n"+"\t".repeat(5) + " \u2502 %2s %-74s \u2502","- ",  h.getNombre());
            }
        }
        System.out.print("\n" + "\t".repeat(5) + " \u251C" + "\u2500".repeat(79) +  "\u2524");
        System.out.printf("\n" + "\t".repeat(5) + " \u2502" + EstilosEnum.AMARILLO.getFormato() + " %-77s " + EstilosEnum.RESET.getFormato() + "\u2502", "                             EVENTOS");
        if (personaje.getParticipaciones() != null) {

            if (personaje.getParticipaciones().isEmpty()) {
                System.out.print("\n" + "\t".repeat(5) + " \u251C" + "\u2500".repeat(79) +  "\u2524");
                System.out.printf("\n" + "\t".repeat(5) + " \u2502 %-77s \u2502", "No participó en ningún evento");
                System.out.print("\n" + "\t".repeat(5) + " \u2514" + "\u2500".repeat(79) +  "\u2518");
                esperarIntro();
            } else {
                System.out.print("\n" + "\t".repeat(5) + " \u251C" + "\u2500".repeat(30)+ "\u252C" + "\u2500".repeat(35) + "\u252C" + "\u2500".repeat(12)  +  "\u2524");
                System.out.printf("\n" + "\t".repeat(5) + " \u2502"
                                + EstilosEnum.NARANJA.getFormato() + " %-28s " + EstilosEnum.RESET.getFormato() + "\u2502"
                                + EstilosEnum.NARANJA.getFormato() + " %-33s " + EstilosEnum.RESET.getFormato() + "\u2502"
                                + EstilosEnum.NARANJA.getFormato() + " %-10s " + EstilosEnum.RESET.getFormato() + "\u2502"
                        , "Nombre del Evento", "Rol en el Evento", " FECHA");
                System.out.print("\n" + "\t".repeat(5) + " \u251C" + "\u2500".repeat(30)+ "\u253C" + "\u2500".repeat(35) + "\u253C" + "\u2500".repeat(12)  +  "\u2524");

                for (Participa p : personaje.getParticipaciones()) {
                    System.out.printf("\n" + "\t".repeat(5) + " \u2502 %-28s \u2502 %-33s \u2502 %10s \u2502 "
                            , p.getEvento().getNombre(), p.getRol(), p.getFecha());
                }
                System.out.print("\n" + "\t".repeat(5) + " \u2514" + "\u2500".repeat(30)+ "\u2534" + "\u2500".repeat(35) + "\u2534" + "\u2500".repeat(12)  +  "\u2518");
                esperarIntro();
            }
        }

    }

    public void mostrarPersonajesDeUnEvento(List<Personaje> personajes) {
        System.out.println("En este evento participaron:");
        for (Personaje personaje: personajes){
            System.out.println("- " + personaje.getNombre());
        }
        esperarIntro();
    }

    private void esperarIntro() {
        System.out.println("\npulsa intro para volver al menú");
        sc.nextLine();
        System.out.println("\n".repeat(50));
    }
}
