package marvel.servicios;

import marvel.modelo.dao.*;
import marvel.modelo.entidades.Habilidad;
import marvel.modelo.entidades.Personaje;
import marvel.modelo.entidades.Traje;
import marvel.modelo.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;


public class PersonajeServicio {

    private final PersonajeDAO personajeDAO;
    private final TrajeDAO trajeDAO;
    private final HabilidadDAO habilidadDAO;
    private final ParticipaDAO participaDAO;

    public PersonajeServicio(PersonajeDAO personajeDAO, TrajeDAO trajeDAO, HabilidadDAO habilidadDAO,  ParticipaDAO participaDAO) {
        this.personajeDAO = personajeDAO;
        this.trajeDAO = trajeDAO;
        this.habilidadDAO = habilidadDAO;
        this.participaDAO = participaDAO;
    }

    public void crearPersonaje(String nombre, String alias, Traje traje) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (alias == null || alias.isBlank()) {
            throw new IllegalArgumentException("El alias no puede estar vacío");
        }
        int idPersonaje = GenericDAO.siguienteId(Personaje.class, "id");
        Personaje personaje = new Personaje();
        personaje.setId(idPersonaje);
        personaje.setNombre(nombre);
        personaje.setAlias(alias);
        if (traje != null) {
            personaje.setTraje(traje);
            traje.setPersonaje(personaje);
        }
        personajeDAO.guardarPersonaje(personaje);
    }

    public void borrarPersonaje(int id) {
        Personaje personaje = personajeDAO.buscarPersonajePorId(id);
        if (personaje == null) {
            throw new IllegalArgumentException("Personaje no encontrado.");
        }
        if (personaje.getTraje() != null) {
            personaje.getTraje().setPersonaje(null);
            personaje.setTraje(null);
        }
        personajeDAO.borrarPersonaje(personaje);
    }

    public void borrarPersonajeForzado(int idPersonaje) {

        Personaje personaje = personajeDAO.buscarPersonajePorId(idPersonaje);
        if (personaje == null) {
            throw new IllegalArgumentException("Personaje no encontrado");
        }

        participaDAO.borrarParticipacionesDePersonaje(idPersonaje);

        if (personaje.getTraje() != null) {
            personaje.getTraje().setPersonaje(null);
            personaje.setTraje(null);
        }

        personajeDAO.borrarPersonaje(personaje);
    }

    public void cambiarNombre(int idPersonaje, String nuevoNombre) {
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        Personaje personaje = buscarPorId(idPersonaje);
        personaje.setNombre(nuevoNombre);
        personajeDAO.actualizarPersonaje(personaje);
    }

    public void cambiarAlias(int idPersonaje, String nuevoAlias) {
        if (nuevoAlias == null || nuevoAlias.isBlank()) {
            throw new IllegalArgumentException("El Alias no puede estar vacío");
        }
        Personaje personaje = buscarPorId(idPersonaje);
        personaje.setAlias(nuevoAlias);
        personajeDAO.actualizarPersonaje(personaje);
    }

    public void cambiarTraje(int idPersonaje, Traje nuevoTraje) {
        Personaje personaje = buscarPorId(idPersonaje);
        if (personaje.getTraje() != null) {
            personaje.getTraje().setPersonaje(null);
        }
        if (nuevoTraje != null) {
            nuevoTraje.setPersonaje(personaje);
            personaje.setTraje(nuevoTraje);
        } else {
            personaje.setTraje(null);
        }
        personajeDAO.actualizarPersonaje(personaje);
    }

    public List<Personaje> buscarTodosLosPersonajes() {
        List<Personaje> personajes = personajeDAO.buscarTodosLosPersonajes();
        if (personajes.isEmpty()) {
            throw new IllegalArgumentException("No hay personajes en la lista.");
        }
        return personajes;
    }


    public Personaje buscarPorId(int idPersonaje) {
        Personaje personaje = personajeDAO.buscarPersonajePorId(idPersonaje);
        if (personaje == null) {
            throw new IllegalArgumentException("Personaje no encontrado");
        }
        return personaje;
    }

    public Personaje buscarPersonajePorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        Personaje personaje = personajeDAO.buscarPersonajePorNombre(nombre);
        if (personaje == null) {
            throw new IllegalArgumentException("Personaje no encontrado");
        }
        return personaje;
    }

    public void asignarHabilidad(String nombrePersonaje, String nombreHabilidad) {
        if (nombrePersonaje == null || nombrePersonaje.isBlank()) {
            throw new IllegalArgumentException("El nombre del personaje no puede estar vacío");
        }
        if (nombreHabilidad == null || nombreHabilidad.isBlank()) {
            throw new IllegalArgumentException("El nombre de la habilidad no puede estar vacío");
        }
        Personaje personaje = personajeDAO.buscarPorNombreConHabilidades(nombrePersonaje);
        Habilidad habilidad = habilidadDAO.buscarHabilidadPorNombre(nombreHabilidad);
        if (personaje == null) {
            throw new IllegalArgumentException("Personaje no encontrado");
        }
        if (habilidad == null) {
            throw new IllegalArgumentException("Habilidad no encontrada");
        }
        if (personaje.getHabilidades().contains(habilidad)) {
            throw new IllegalArgumentException("El personaje ya tiene esa habilidad");
        }
        personaje.getHabilidades().add(habilidad);
        habilidad.getPersonajes().add(personaje);
        personajeDAO.actualizarPersonaje(personaje);
     }


    public long contarPersonajesPorHabilidad(String nombreHabilidad) {
        if (nombreHabilidad == null || nombreHabilidad.isBlank()) {
            throw new IllegalArgumentException("El nombre de la habilidad no puede estar vacío");
        }
        return personajeDAO.contarPersonajesPorHabilidad(nombreHabilidad);
    }

    public Personaje obtenerPersonajeConDatos(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        return personajeDAO.buscarPorNombreConTodo(nombre);
    }

    public boolean participaEnEvento(int idPersonaje) {
        return personajeDAO.personajeParticipaEnEvento(idPersonaje);
    }
}


