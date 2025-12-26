package marvel.controlador;

import marvel.modelo.dao.*;
import marvel.modelo.entidades.Personaje;
import marvel.modelo.entidades.Traje;
import marvel.modelo.enums.MenuEnum;
import marvel.modelo.util.HibernateUtil;
import marvel.servicios.PersonajeServicio;
import marvel.vista.MarvelVista;

import java.util.List;

public class MarvelControlador {

    private final MarvelVista vista;
    private final PersonajeServicio personajeServicio;
    private final TrajeDAO trajeDAO;
    private final PersonajeDAO personajeDAO;
    private final HabilidadDAO habilidadDAO;
    private final EventoDAO eventoDAO;

    public MarvelControlador(MarvelVista vista,
                             PersonajeServicio personajeServicio,
                             TrajeDAO trajeDAO,
                             PersonajeDAO personajeDAO,
                             HabilidadDAO habilidadDAO,
                             EventoDAO eventoDAO)
    {
        this.vista = vista;
        this.personajeServicio = personajeServicio;
        this.trajeDAO = trajeDAO;
        this.personajeDAO = personajeDAO;
        this.habilidadDAO = habilidadDAO;
        this.eventoDAO = eventoDAO;
    }

    public void ejecuta() {
        MenuEnum opcion;
        while (true) {
            vista.mostrarMenu();
            int seleccion = solicitarInt("\nIntroduce una opción: ", 0, MenuEnum.values().length - 1, false);
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
                    cerrarAplicacion();
                    return;
                }
            }
        }
    }

    private void cerrarAplicacion() {
        HibernateUtil.cerrar();
        vista.mensaje("Finalizando la conexión a MySQL");
    }

    private void crearPersonaje() {
        try {
            String nombre = vista.solicitarEntrada("Introduce el nombre del personaje:");
            String alias = vista.solicitarEntrada("Introduce el alias del personaje: ");
            Traje traje = seleccionarTraje();
            personajeServicio.crearPersonaje(nombre, alias, traje);
            vista.mensaje("Personaje creado correctamente");
        } catch (IllegalArgumentException e) {
            vista.mensajeError("ERROR: " + e.getMessage());
        }
    }


    private void borrarPersonaje() {

        List<Personaje> personajes = personajeDAO.buscarTodos();
        vista.mensaje("Has seleccionado la opcion de borrar personaje");
        vista.mostrarPersonajes(personajes);
        vista.mensaje("0.- Volver al menu anterior");
        if (personajes.isEmpty()) {
            vista.mensaje("No hay personajes registrados.");
            return;
        }

        int id = solicitarInt("Selecciona el personaje a borrar: ", 0, personajes.size(), false);
        if (id == 0) return;
        Personaje personaje = personajes.get(id-1);

        if (personaje.getTraje() != null) {
            personaje.getTraje().setPersonaje(null);
            personaje.setTraje(null);
        }

        personajeDAO.borrar(personaje);
        vista.mensaje("Personaje borrado correctamente.");
    }


    private void modificarPersonaje() {
        final int MAX_MENU=3;

        List<Personaje> personajes = personajeDAO.buscarTodos();

        if (personajes.isEmpty()) {
            vista.mensaje("No hay personajes registrados.");
            return;
        }

        vista.mensaje("Has seleccionado la opcion de modificar personaje");
        vista.mostrarPersonajes(personajes);
        vista.mensaje("0.- Volver al menu anterior");

        int id = solicitarInt("Selecciona el personaje a modificar: ", 0, personajes.size(), false);
        if (id == 0) return;
        Personaje personaje = personajes.get(id-1);
        int idPersonaje = personaje.getId();

        int opcion;
        while (true) {
            vista.menuModificarPersonaje();
            opcion = solicitarInt("Seleccione una opción: ",0, MAX_MENU,false);
            switch (opcion){
                case 1: {
                    String nuevoNombre = vista.solicitarEntrada("Nuevo nombre: ");
                    personajeServicio.cambiarNombre(idPersonaje, nuevoNombre);
                    vista.mensaje("Nombre actualizado.");
                    break;
                }
                case 2: {
                    String nuevoAlias = vista.solicitarEntrada("Nuevo alias: ");
                    personajeServicio.cambiarAlias(idPersonaje,nuevoAlias);
                    vista.mensaje("Alias actualizado.");
                    break;
                }
                case 3: {
                    Traje nuevoTraje = seleccionarTraje();
                    personajeServicio.cambiarTraje(idPersonaje,nuevoTraje);
                    vista.mensaje("Traje actualizado.");
                    break;
                }
                case 0:  {
                    vista.mensaje("Volver al menú principal");
                    return;
                }
            }
        }
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

    private int solicitarInt(String mensaje, int min, int max, boolean permitirNulo) {
        while (true) {
            String input = vista.solicitarEntrada(mensaje);
            if (permitirNulo) {
                if (input.isBlank()) return -1;
            }
            try {
                int valor = Integer.parseInt(input);
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    vista.mensaje("!!! ERROR !!!  El valor debe estar entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                vista.mensaje("!!! ERROR !!!  Introduce un número entero válido.");
            }
        }
    }

    private Traje seleccionarTraje() {

        List<Traje> disponibles = trajeDAO.buscarDisponibles();
        vista.mostrarTrajesDisponibles(disponibles);

        int opcion = solicitarInt("Elige ID de traje: ", 0, Integer.MAX_VALUE, true);

        if (opcion == -1) {
            return null;
        }

        if (opcion == 0) {
            String especificacion = vista.solicitarEntrada("Especificación del nuevo traje: ");

            Traje nuevoTraje = new Traje();
            nuevoTraje.setEspecificacion(especificacion);
            return nuevoTraje;
        }

        Traje traje = trajeDAO.buscarPorId(opcion);
        if (traje == null || traje.getPersonaje() != null) {
            vista.mensaje("Traje no válido.");
            return null;
        }

        return traje;
    }

}
