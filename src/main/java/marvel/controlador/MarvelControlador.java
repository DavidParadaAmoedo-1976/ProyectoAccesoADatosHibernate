package marvel.controlador;

import marvel.modelo.dao.EventoDAO;
import marvel.modelo.dao.HabilidadDAO;
import marvel.modelo.dao.PersonajeDAO;
import marvel.modelo.dao.TrajeDAO;
import marvel.modelo.entidades.Habilidad;
import marvel.modelo.entidades.Personaje;
import marvel.modelo.entidades.Traje;
import marvel.modelo.enums.MenuEnum;
import marvel.modelo.enums.ModificarHabilidadEnum;
import marvel.modelo.enums.ModificarPersonajeEnum;
import marvel.modelo.util.HibernateUtil;
import marvel.servicios.HabilidadServicio;
import marvel.servicios.PersonajeServicio;
import marvel.vista.MarvelVista;

import java.util.List;

public class MarvelControlador {

    private final MarvelVista vista;
    private final PersonajeServicio personajeServicio;
    private final HabilidadServicio habilidadServicio;
    private final TrajeDAO trajeDAO;
    private final PersonajeDAO personajeDAO;
    private final HabilidadDAO habilidadDAO;
    private final EventoDAO eventoDAO;

    public MarvelControlador(MarvelVista vista,
                             PersonajeServicio personajeServicio,
                             HabilidadServicio habilidadServicio,
                             TrajeDAO trajeDAO,
                             PersonajeDAO personajeDAO,
                             HabilidadDAO habilidadDAO,
                             EventoDAO eventoDAO) {
        this.vista = vista;
        this.personajeServicio = personajeServicio;
        this.habilidadServicio = habilidadServicio;
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
                case CONTAR_POR_HABILIDAD -> MostrarPersonajesPorHabilidad();
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

        List<Personaje> personajes = personajeDAO.buscarTodosLosPersonajes();
        vista.mensaje("Has seleccionado la opcion de borrar personaje");
        vista.mostrarPersonajes(personajes, true);
        vista.mensaje("0.- Volver al menu anterior");
        if (personajes.isEmpty()) {
            vista.mensaje("No hay personajes registrados.");
            return;
        }

        int id = solicitarInt("Selecciona el personaje a borrar: ", 0, personajes.size(), false);
        if (id == 0) return;
        Personaje personaje = personajes.get(id - 1);

        if (personaje.getTraje() != null) {
            personaje.getTraje().setPersonaje(null);
            personaje.setTraje(null);
        }

        personajeDAO.borrarPersonaje(personaje);
        vista.mensaje("Personaje borrado correctamente.");
    }


    private void modificarPersonaje() {
        List<Personaje> personajes = personajeDAO.buscarTodosLosPersonajes();

        if (personajes.isEmpty()) {
            vista.mensaje("No hay personajes registrados.");
            return;
        }
        vista.mensaje("Has seleccionado la opcion de modificar personaje");
        vista.mostrarPersonajes(personajes, true);
        vista.mensaje("0.- Volver al menu anterior");

        int id = solicitarInt("Selecciona el personaje a modificar: ", 0, personajes.size(), false);
        if (id == 0) return;
        Personaje personaje = personajes.get(id - 1);
        int idPersonaje = personaje.getId();

        ModificarPersonajeEnum opcion;
        while (true) {
            vista.mostrarMenuModificarPersonaje();
            int seleccion = solicitarInt("\nIntroduce una opción: ", 0, ModificarPersonajeEnum.values().length - 1, false);
            opcion = ModificarPersonajeEnum.values()[seleccion];
            switch (opcion) {
                case NOMBRE: {
                    String nuevoNombre = vista.solicitarEntrada("Nuevo nombre: ");
                    personajeServicio.cambiarNombre(idPersonaje, nuevoNombre);
                    vista.mensaje("Nombre actualizado.");
                    break;
                }
                case ALIAS: {
                    String nuevoAlias = vista.solicitarEntrada("Nuevo alias: ");
                    personajeServicio.cambiarAlias(idPersonaje, nuevoAlias);
                    vista.mensaje("Alias actualizado.");
                    break;
                }
                case TRAJE: {
                    Traje nuevoTraje = seleccionarTraje();
                    personajeServicio.cambiarTraje(idPersonaje, nuevoTraje);
                    vista.mensaje("Traje actualizado.");
                    break;
                }
                case SALIR: {
                    vista.mensaje("Volver al menú principal");
                    return;
                }
            }
        }
    }


    private void crearHabilidad() {
        try {
            String nombre = vista.solicitarEntrada("Nombre de la Habilidad: ");
            String descripcion = vista.solicitarEntrada("Descripcion de la Habilidad: ");
            habilidadServicio.crearHabilidad(nombre, descripcion);
            vista.mensaje("Habilidad creada correctamente.");
        } catch (IllegalArgumentException e) {
            vista.mensajeError("ERROR: " + e.getMessage());
        }
    }

    private void borrarHabilidad() {

        List<Habilidad> habilidades = habilidadDAO.buscarTodasLasHabilidades();
        vista.mostrarHabilidades(habilidades, false);

        if (habilidades.isEmpty()) {
            return;
        }

        while (true) {
            vista.mensaje("\nVas a borrar una habilidad.");
            vista.mensaje("Escribe 0 para volver al menú.");

            String nombre = vista
                    .solicitarEntrada("Nombre de la habilidad que quieres borrar: ").trim().toLowerCase();

            if (nombre.equals("0")) {
                return;
            }

            try {
                Habilidad habilidad = habilidadServicio.buscarPorNombre(nombre);
                habilidadDAO.borrarHabilidad(habilidad);

                vista.mensaje("Habilidad borrada correctamente.");
                return;

            } catch (IllegalArgumentException e) {
                vista.mensajeError(e.getMessage());

            }
        }
    }


    private void modificarHabilidad() {
        List<Habilidad> habilidades = habilidadDAO.buscarTodasLasHabilidades();
        vista.mensaje("Vas a modificar una habilidad.");
        vista.mostrarHabilidades(habilidades, true);
        vista.mensaje("0.- Volver al menu anterior");
        if (habilidades.isEmpty()) {
            vista.mensaje("No hay habilidades para modificar.");
        }
        int id = solicitarInt("Seleccione una habilidad: ", 0, habilidades.size() - 1, false);
        Habilidad habilidad = habilidades.get(id - 1);
        String nombreHabilidad = habilidad.getNombre();
        vista.mensaje("Has selecionado la habilidad: " + habilidad.getNombre());

        ModificarHabilidadEnum opcion;
        while (true) {
            vista.mostrarMenuModificarHabilidad();
            int seleccion = solicitarInt("Seleccione una opción: ", 0, habilidades.size() - 1, false);
            opcion = ModificarHabilidadEnum.values()[seleccion];
            switch (opcion) {
                case NOMBRE: {
                    String nuevoNombre = vista.solicitarEntrada("Nuevo nombre para la habilidad: ");
                    habilidadServicio.cambiarNombre(nombreHabilidad, nuevoNombre);
                    vista.mensaje("Nombre de habilidad cambiado correctamente.");
                    break;
                }
                case DESCRIPCION: {
                    String nuevaDescripcion = vista.solicitarEntrada("Nueva descripción para la habilidad: ");
                    habilidadServicio.cambiarDescripcion(nombreHabilidad, nuevaDescripcion);
                    vista.mensaje("Descripcion de la habilidad cambiada correctamente.");
                    break;
                }

                case SALIR: {
                    vista.mensaje("Volver al menú principal");
                    return;
                }
            }
        }

    }

    private void asignarHabilidad() {
        List<Personaje> personajes = personajeDAO.buscarTodosLosPersonajes();
        List<Habilidad> habilidades = habilidadDAO.buscarTodasLasHabilidades();
        if (personajes.isEmpty() || habilidades.isEmpty()) {
            vista.mensaje("No hay personajes o habilidades disponibles.");
            return;
        }
        while (true) {
            try {
                vista.mensaje("Vas a asignar una habilidad a un personaje.");

                vista.mostrarPersonajes(personajes, false);
                String nombrePersonaje =
                        vista.solicitarEntrada("Nombre del personaje (0 para salir): ").trim();

                if (nombrePersonaje.equals("0")) return;

                vista.mostrarHabilidades(habilidades, false);
                String nombreHabilidad =
                        vista.solicitarEntrada("Nombre de la habilidad (0 para salir): ").trim();

                if (nombreHabilidad.equals("0")) return;

                personajeServicio.asignarHabilidad(nombrePersonaje, nombreHabilidad);

                vista.mensaje("Habilidad asignada correctamente.");
                return;

            } catch (IllegalArgumentException e) {
                vista.mensajeError(e.getMessage());
            }
        }
    }


    private void registrarParticipacion() {
    }

    private void cambiarTraje() {
    }

    private void mostrarDatosPersonaje() {
    }

    private void mostrarPersonajesEvento() {
    }

    private void MostrarPersonajesPorHabilidad() {
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
