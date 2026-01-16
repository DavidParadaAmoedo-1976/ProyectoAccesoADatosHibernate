package marvel.controlador;

import marvel.modelo.entidades.Evento;
import marvel.modelo.entidades.Habilidad;
import marvel.modelo.entidades.Personaje;
import marvel.modelo.entidades.Traje;
import marvel.modelo.enums.EstilosEnum;
import marvel.modelo.enums.MenuEnum;
import marvel.modelo.enums.ModificarHabilidadEnum;
import marvel.modelo.enums.ModificarPersonajeEnum;
import marvel.modelo.util.HibernateUtil;
import marvel.servicios.*;
import marvel.vista.MarvelVista;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

public class MarvelControlador {

    public static final int MAX_SEL_TRAJE = 2;
    private final MarvelVista vista;
    private final PersonajeServicio personajeServicio;
    private final HabilidadServicio habilidadServicio;
    private final TrajeServicio trajeServicio;
    private final EventoServicio eventoServicio;
    private final ParticipaServicio participaServicio;


    public MarvelControlador(MarvelVista vista,
                             PersonajeServicio personajeServicio,
                             HabilidadServicio habilidadServicio,
                             TrajeServicio trajeServicio,
                             EventoServicio eventoServicio,
                             ParticipaServicio participaServicio
    ) {
        this.vista = vista;
        this.personajeServicio = personajeServicio;
        this.habilidadServicio = habilidadServicio;
        this.trajeServicio = trajeServicio;
        this.eventoServicio = eventoServicio;
        this.participaServicio = participaServicio;
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
                case CONTAR_POR_HABILIDAD -> mostrarPersonajesPorHabilidad();
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
        String nombre;
        while (true) {
            nombre = vista.solicitarEntrada("Introduce el nombre del personaje: ");
            try {
                personajeServicio.buscarPersonajePorNombre(nombre);
                vista.mensaje("El personaje ya existe. Introduce otro nombre.");
            } catch (RuntimeException e) {
                break;
            }
        }
        try {
            String alias = vista.solicitarEntrada("Introduce el alias del personaje: ");
            Traje traje = seleccionarTraje();
            personajeServicio.crearPersonaje(nombre, alias, traje);
            vista.mensaje("Personaje creado correctamente");
        } catch (IllegalArgumentException e) {
            vista.mensajeError("ERROR: " + e.getMessage());
        }
    }

    private void borrarPersonaje() {
        List<Personaje> personajes = personajeServicio.buscarTodosLosPersonajes();
        vista.mensaje("Has seleccionado la opción de borrar personaje");
        if (personajes.isEmpty()) {
            vista.mensaje("No hay personajes registrados.");
            return;
        }
        while (true) {
            vista.mostrarPersonajes(personajes, true);
            vista.mensaje("\n\n0.-" + EstilosEnum.NARANJA.getFormato()
                    + "Volver al menú anterior"
                    + EstilosEnum.RESET.getFormato());
            int opcion = solicitarInt("Selecciona el personaje a borrar: ",0, personajes.size(),false);
            if (opcion == 0) {
                return;
            }
            Personaje personaje = personajes.get(opcion - 1);
            int idPersonaje = personaje.getId();
            try {
                if (personajeServicio.participaEnEvento(idPersonaje)) {
                    vista.mensajeError("Este personaje participa en uno o más eventos.");
                    String respuesta = vista.solicitarEntrada("¿Deseas borrarlo igualmente? (s/n): ").trim().toLowerCase();
                    if (!respuesta.equals("s")) {
                        vista.mensaje("Operación cancelada.");
                        continue;
                    }
                }
                personajeServicio.borrarPersonajeForzado(idPersonaje);
                vista.mensaje("Personaje borrado correctamente.");

            } catch (IllegalArgumentException e) {
                vista.mensajeError(e.getMessage());
            }
            personajes = personajeServicio.buscarTodosLosPersonajes();
            if (personajes.isEmpty()) {
                vista.mensaje("No hay más personajes registrados.");
                return;
            }
        }
    }


    private void modificarPersonaje() {
        List<Personaje> personajes = personajeServicio.buscarTodosLosPersonajes();
        if (personajes.isEmpty()) {
            vista.mensaje("No hay personajes registrados.");
            return;
        }
        vista.mensaje("Has seleccionado la opción de modificar personaje");
        vista.mostrarPersonajes(personajes, true);
        vista.mensaje("\n\n0.-" + EstilosEnum.NARANJA.getFormato() + "Volver al menú anterior" + EstilosEnum.RESET.getFormato());
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
        List<Habilidad> habilidades = habilidadServicio.buscarTodasLasHabilidades();
        if (habilidades.isEmpty()) {
            vista.mensaje("No hay habilidades registradas.");
            return;
        }

        vista.mostrarHabilidades(habilidades, false);

        while (true) {
            vista.mensaje("\n\nVas a borrar una habilidad.");
            vista.mensaje("0.- Volver al menú anterior");

            String nombre = vista.solicitarEntrada(
                    "Nombre de la habilidad que quieres borrar: "
            ).trim().toLowerCase();

            if (nombre.equals("0")) return;

            try {
                if (habilidadServicio.habilidadUsadaPorUnPersonaje(nombre)) {
                    vista.mensajeError("Esa habilidad la tiene algún personaje.");
                    String respuesta = vista.solicitarEntrada(
                            "¿Deseas borrarla igualmente? (s/n): "
                    ).trim().toLowerCase();

                    if (!respuesta.equals("s")) {
                        vista.mensaje("Operación cancelada.");
                        continue;
                    }
                }

                habilidadServicio.borrarHabilidad(nombre);
                vista.mensaje("Habilidad borrada correctamente.");
                return;

            } catch (IllegalArgumentException e) {
                vista.mensajeError(e.getMessage());
            }
        }
    }



    private void modificarHabilidad() {
        List<Habilidad> habilidades = habilidadServicio.buscarTodasLasHabilidades();
        vista.mensaje("Vas a modificar una habilidad.");
        vista.mostrarHabilidades(habilidades, true);
        vista.mensaje("\n\n0.-" + EstilosEnum.NARANJA.getFormato() + "Volver al menú anterior" + EstilosEnum.RESET.getFormato());
        if (habilidades.isEmpty()) {
            vista.mensaje("No hay habilidades para modificar.");
            return;
        }
        int id = solicitarInt("Seleccione una habilidad: ", 0, habilidades.size(), false);
        if (id == 0) return;
        Habilidad habilidad = habilidades.get(id - 1);
        String nombreHabilidad = habilidad.getNombre();
        vista.mensaje("Has seleccionado la habilidad: " + habilidad.getNombre());
        ModificarHabilidadEnum opcion;
        while (true) {
            vista.mostrarMenuModificarHabilidad();
            int seleccion = solicitarInt("Seleccione una opción: ", 0, ModificarHabilidadEnum.values().length - 1, false);
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
                    vista.mensaje("Descripción de la habilidad cambiada correctamente.");
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
        List<Personaje> personajes = personajeServicio.buscarTodosLosPersonajes();
        List<Habilidad> habilidades = habilidadServicio.buscarTodasLasHabilidades();
        if (personajes.isEmpty() || habilidades.isEmpty()) {
            vista.mensaje("No hay personajes o habilidades disponibles.");
            return;
        }
        while (true) {
            try {
                vista.mensaje("Vas a asignar una habilidad a un personaje.");
                vista.mostrarPersonajes(personajes, false);
                String nombrePersonaje = vista.solicitarEntrada("\nNombre del personaje (0 para salir): ").trim();
                if (nombrePersonaje.equals("0")) return;
                vista.mostrarHabilidades(habilidades, false);
                String nombreHabilidad = vista.solicitarEntrada("\nNombre de la habilidad (0 para salir): ").trim();
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
        vista.mensaje("Vas a registrar una participación de un personaje en un evento.");
        Evento evento = null;
        Personaje personaje;
        LocalDate fecha;
        String rol;
        while (true) {
            vista.mensaje("Selección de evento:");
            vista.mostrarMenuEventos();
            int opcionEvento = solicitarInt("Seleccione una opción: ", 0, 2, false);
            switch (opcionEvento) {
                case 1 -> {
                    String nombreEvento;
                    while (true) {
                        nombreEvento = vista.solicitarEntrada("Introduce el nombre del evento: ");
                        if (eventoServicio.existeEventoConNombre(nombreEvento)) {
                            vista.mensajeError("Ya existe un evento con ese nombre. Introduce otro.");
                        } else {
                            break;
                        }
                    }
                    String lugarEvento = vista.solicitarEntrada("Introduce el lugar del evento: ");
                    eventoServicio.crearEvento(nombreEvento, lugarEvento);
                    evento = eventoServicio.buscarEventoPorNombre(nombreEvento);
                }
                case 2 -> {
                    List<Evento> eventos = eventoServicio.buscarTodosLosEventos();

                    vista.mostrarEventos(eventos,false);
                    String nombreEvento = vista.solicitarEntrada("\nIntroduce el nombre del evento: ");
                    try {
                        evento = eventoServicio.buscarEventoPorNombre(nombreEvento);
                    } catch (IllegalArgumentException e) {
                        vista.mensajeError(e.getMessage());
                        continue;
                    }
                }
                case 0 -> {
                    vista.mensaje("Volviendo al menú principal.");
                    return;
                }
            }
            vista.mensaje("Selección de personaje:");
            List<Personaje> personajes = personajeServicio.buscarTodosLosPersonajes();
            vista.mostrarPersonajes(personajes, false);
            String nombrePersonaje = vista.solicitarEntrada("\nIntroduce el nombre del personaje: ");
            try {
                personaje = personajeServicio.buscarPersonajePorNombre(nombrePersonaje);
            } catch (IllegalArgumentException e) {
                vista.mensajeError(e.getMessage());
                continue; // vuelve a pedir el personaje
            }
            fecha = solicitarFecha();
            rol = vista.solicitarEntrada("Introduce el rol del personaje: ");
            try {
                participaServicio.crearParticipa(evento, personaje, fecha, rol);
                vista.mensaje("Participación registrada correctamente.");
                return;
            } catch (IllegalArgumentException e) {
                vista.mensajeError("No se pudo registrar la participación: " + e.getMessage());
            }
        }
    }

    private LocalDate solicitarFecha() {
        int dia = solicitarInt("Introduce el día del evento: ", 1, 31, false);
        int mes = solicitarInt("Introduce el mes del evento en número: ", 1, 12, false);
        int anio = solicitarInt("Introduce el año del evento: ", 1, 2050, false);
        try {
            return LocalDate.of(anio, mes, dia);
        } catch (DateTimeException e) {
            vista.mensajeError("Fecha no válida. Intenta de nuevo.");
            return solicitarFecha();
        }
    }

    private void cambiarTraje() {
        vista.mensaje("Vas a cambiar el Traje de un personaje.");
        List<Personaje> personajes = personajeServicio.buscarTodosLosPersonajes();
        if (personajes.isEmpty()) {
            vista.mensaje("No hay personajes registrados.");
            return;
        }
        while (true) {
            vista.mostrarPersonajes(personajes, false);
            String nombrePersonaje = vista.solicitarEntrada("\nIntroduce el nombre del personaje (0 para salir): ");
            if (nombrePersonaje.equals("0")) return;
            Personaje personaje;
            try {
                personaje = personajeServicio.buscarPersonajePorNombre(nombrePersonaje);
                vista.mensaje("Has seleccionado el personaje: " + personaje.getNombre());
            } catch (IllegalArgumentException e) {
                vista.mensajeError("Personaje no encontrado.");
                continue;
            }
            if (personaje.getTraje() != null) {
                vista.mensaje(
                        "El personaje " + personaje.getNombre() +
                                " ya tiene el traje: " + personaje.getTraje());
                while (true) {
                    String decision = vista.solicitarEntrada(
                            "¿Deseas cambiar el traje? S|s / N|n : "
                    ).trim();

                    if (decision.equalsIgnoreCase("S")) {
                        break;
                    }

                    if (decision.equalsIgnoreCase("N")) {
                        return;
                    }

                    vista.mensajeError("Error: Introduce S o N correctamente.");
                }
            }
            Traje traje = seleccionarTraje();
            personajeServicio.cambiarTraje(personaje.getId(), traje);
            vista.mensaje("Traje cambiado correctamente.");
            return;
        }
    }

    private Traje seleccionarTraje() {
        List<Traje> disponibles = trajeServicio.buscarTrajesDisponibles();
        if (disponibles.isEmpty()) {
            vista.mensaje("No hay trajes disponibles.\n");
            if(solicitarBooleano("Deseas crear un traje nuevo: ")){
                return trajeServicio.crearTraje(vista.solicitarEntrada("Introduce la especificación del nuevo traje: "));
            }
            return null;
        }
        vista.mostrarMenuSeleccionarTraje();
        int opcion = solicitarInt("Elige una opción: ", 0, MAX_SEL_TRAJE, true);
        if (opcion == -1) {
            return null;
        }
        if (opcion == 0) {
            return null;
        }
        if (opcion == 1) {
            String especificacion = vista.solicitarEntrada("Especificación del nuevo traje: ");
            return trajeServicio.crearTraje(especificacion);
        }
        int idTraje = 0;
        if (opcion == 2) {
            vista.mostrarTrajesDisponibles(disponibles);
            int seleccion = solicitarInt("Seleccione un traje de la lista: ", 0, disponibles.size(), false);
            idTraje = disponibles.get(seleccion -1 ).getId();
        }
        Traje traje = null;
        try {
            traje = trajeServicio.buscarTrajePorId(idTraje);
        } catch (IllegalArgumentException e) {
            vista.mensajeError(e.getMessage());
        }
        return traje;
    }

    private void mostrarDatosPersonaje() {
        List<Personaje> personajes = personajeServicio.buscarTodosLosPersonajes();
        if (personajes.isEmpty()) {
            vista.mensaje("No hay personajes registrados.");
            return;
        }
        vista.mostrarPersonajes(personajes, true);
        int id = solicitarInt("\nIntroduce el número del personaje, o 0 para volver al menú principal: ",0, personajes.size(),false);
        if (id == 0) return;
        try {
            Personaje personaje = personajeServicio.obtenerPersonajeConDatos(personajes.get(id - 1).getNombre());
            vista.mostrarDatosPersonaje(personaje);
        } catch (IllegalArgumentException e) {
            vista.mensajeError(e.getMessage());
        }
    }

    private void mostrarPersonajesEvento() {
        List<Evento> eventos = eventoServicio.buscarTodosLosEventos();
        if (eventos.isEmpty()) {
            vista.mensaje("No hay eventos registrados.");
            return;
        }
        vista.mostrarEventos(eventos, true);
        int id = solicitarInt("\nIntroduce el número del evento para consultar que personajes participaron " +
                "\n o pulsa 0 para volver al menú principal: ", 0 , eventos.size(),false);
        if (id == 0) return;
        List<Personaje> personajes = participaServicio.buscarPersonajesDeUnEvento(eventos.get(id-1));
        if (personajes.isEmpty()) {
            vista.mensaje("No hay personajes asociados a este evento.");
            vista.esperarIntro();
            return;
        }
        vista.mensaje("Has solicitado ver los personajes que participan en el evento -> " + eventos.get(id-1).getNombre());
        vista.mostrarPersonajesDeUnEvento(personajes);
    }

    private void mostrarPersonajesPorHabilidad() {
        List<Habilidad> habilidades = habilidadServicio.buscarTodasLasHabilidades();
        vista.mostrarHabilidades(habilidades, false);
        String nombreHabilidad;
        while (true) {
            try {
                nombreHabilidad = vista.solicitarEntrada("\nNombre de la habilidad: ").trim();
                long total = personajeServicio.contarPersonajesPorHabilidad(nombreHabilidad);
                vista.mensaje("Número de personajes con la habilidad " + nombreHabilidad + ": " + total);
                vista.esperarIntro();
                return;
            } catch (IllegalArgumentException e) {
                vista.mensajeError(e.getMessage());
            }
        }
    }

    private int solicitarInt(String mensaje, int min, int max,boolean permitirNulo) {
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

    public boolean solicitarBooleano(String dato) {
        boolean datoOk = false;
        String input = "";

        while (!datoOk) {
            vista.mensaje(dato);
            input = vista.solicitarEntrada("S|s o N|n -> ");
            if (input.equalsIgnoreCase("S") || input.equalsIgnoreCase("N")) {
                datoOk = true;
            } else {
                vista.mensajeError("Error: Introduzca S o N correctamente.");
            }
        }
        return input.equalsIgnoreCase("S");
    }
}
