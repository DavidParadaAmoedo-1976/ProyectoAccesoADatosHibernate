package marvel.controlador;

import marvel.modelo.dao.GenericDAO;
import marvel.modelo.dao.PersonajeDAO;
import marvel.modelo.dao.TrajeDAO;
import marvel.modelo.entidades.Personaje;
import marvel.modelo.entidades.Traje;
import marvel.modelo.enums.MenuEnum;
import marvel.modelo.util.HibernateUtil;
import marvel.servicios.PersonajeServicio;
import marvel.vista.MarvelVista;
import org.hibernate.Session;

import java.util.List;

public class MarvelControlador {

    private final int ZERO = 0;
    private final MarvelVista vista;
    private PersonajeServicio personajeServicio = null;
    private TrajeDAO trajeDAO = null;

    public MarvelControlador(MarvelVista vista) {
        this.vista = vista;
        this.personajeServicio = new PersonajeServicio();
        this.trajeDAO = new TrajeDAO();
    }

    public void ejecuta() {
        MenuEnum opcion;
        while (true) {
            vista.mostrarMenu();
            int seleccion = solicitarInt("\nIntroduce una opción: ", ZERO, MenuEnum.values().length - 1, false);
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

            int idTraje = seleccionarTraje().getId();
            String especificacion = seleccionarTraje().getEspecificacion();
            Traje traje = seleccionarTraje();

            personajeServicio.crearPersonaje(nombre, alias, traje);

            vista.mensaje("Personaje creado correctamente");

        } catch (IllegalArgumentException e) {
            vista.mensajeError("ERROR: " + e.getMessage());
        }
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
            return nuevoTraje; // 👉 sin ID, sin guardar
        }

        Traje traje = trajeDAO.buscarPorId(opcion);
        if (traje == null || traje.getPersonaje() != null) {
            vista.mensaje("Traje no válido.");
            return null;
        }

        return traje;
    }


//    private Traje seleccionarTraje() {
//
//        List<Traje> disponibles = trajeDAO.buscarDisponibles();
//        vista.mostrarTrajesDisponibles(disponibles);
//
//        int opcion = solicitarInt("Elige ID de traje: ", 0, Integer.MAX_VALUE, true);
//
//        if (opcion == -1) {
//            return null; // sin traje
//        }
//
//        if (opcion == 0) {
//            String esp = vista.solicitarEntrada("Especificación del nuevoTraje traje: ");
//
//            int nuevoID = GenericDAO.siguienteId(Traje.class, "id");
//
//            Traje nuevoTraje = new Traje();
//            nuevoTraje.setId(nuevoID);
//            nuevoTraje.setEspecificacion(esp);
//
//            trajeDAO.guardar(nuevoTraje);
//            return nuevoTraje;
//        }
//        return trajeDAO.buscarPorId(opcion);
//    }
}
