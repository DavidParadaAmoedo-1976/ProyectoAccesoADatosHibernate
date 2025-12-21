package marvel.controlador;

import marvel.modelo.enums.MenuEnum;
import marvel.vista.MarvelVista;

public class MarvelControlador {

    private final int ZERO = 0;
    private final MarvelVista vista;

    public MarvelControlador(MarvelVista vista) {
        this.vista = vista;
    }

    public void ejecuta() {
        MenuEnum opcion;
        while (true) {
            vista.mostrarMenu();
            int seleccion = solicitarInt("\nIntroduce una opción: ", ZERO, MenuEnum.values().length - 1);
            opcion = MenuEnum.values()[seleccion];
            switch (opcion) {
                case CREAR_PERSONAJE -> crearPersonaje();
                case BORRAR_PERSONAJE -> borrarPersonaje();
                case MODIFICAR_PERSONAJE -> modificarPersonaje();
                case CREAR_HABILIDAD -> crearHabilidad();
                case BORRAR_HABILIDAD -> borrarHabilidad();
                case MODIFICAR_HABILIDAD -> modificarHabilidad();
                case ASIGNAR_HABILIDAD -> asignarHabilidad();
                case REGISTRAR_PARTICIPACION -> registrarParticipacion();
                case CAMBIAR_TRAJE -> cambiarTraje();
                case MOSTRAR_PERSONAJE -> mostrarDatosPersonaje();
                case MOSTRAR_PERSONAJES_EVENTO -> mostrarPersonajesEvento();
                case CONTAR_POR_HABILIDAD -> contarPersonajesPorHabilidad();
                case SALIR -> {
                    vista.mensaje("Saliendo del programa ....");
                    return;
                }
            }
        }
    }

    private void crearPersonaje() {
    }

    private void borrarPersonaje() {
    }

    private void modificarPersonaje() {
    }

    private void crearHabilidad() {
    }

    private void borrarHabilidad() {
    }

    private void modificarHabilidad() {
    }

    private void asignarHabilidad() {
    }

    private void registrarParticipacion() {
    }

    private void cambiarTraje() {
    }

    private void mostrarDatosPersonaje() {
    }

    private void mostrarPersonajesEvento() {
    }

    private void contarPersonajesPorHabilidad() {
    }

    private int solicitarInt(String mensaje, int min, int max) {
        while (true) {
            String input = vista.solicitarEntrada(mensaje);
            try {
                int valor = Integer.parseInt(input);
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    vista.mensajeError("!!! ERROR !!!  El valor debe estar entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                vista.mensajeError("!!! ERROR !!!  Introduce un número entero válido.");
            }
        }
    }
}
