package marvel.servicios;

import marvel.modelo.dao.GenericDAO;
import marvel.modelo.dao.HabilidadDAO;
import marvel.modelo.dao.PersonajeDAO;
import marvel.modelo.entidades.Habilidad;
import marvel.modelo.entidades.Personaje;

import java.util.ArrayList;
import java.util.List;

public class HabilidadServicio {
    private final HabilidadDAO habilidadDAO;
    private final PersonajeDAO personajeDAO;

    public HabilidadServicio(HabilidadDAO habilidadDAO, PersonajeDAO personajeDAO) {
        this.habilidadDAO = habilidadDAO;
        this.personajeDAO = personajeDAO;
    }


    public void crearHabilidad(String nombre, String descripcion) {
        int idHabilidad = GenericDAO.siguienteId(Habilidad.class, "id");

        Habilidad habilidad = new Habilidad();
        habilidad.setId(idHabilidad);
        habilidad.setNombre(nombre);
        habilidad.setDescripcion(descripcion);

        habilidadDAO.guardarHabilidades(habilidad);
    }

    public List<Habilidad> buscarTodasLasHabilidades() {
        return habilidadDAO.buscarTodasLasHabilidades();
    }

    public Habilidad buscarHabilidadPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        Habilidad habilidad = habilidadDAO.buscarHabilidadPorNombre(nombre);
        if (habilidad == null) {
            throw new IllegalArgumentException("La habilidad no existe");
        }
        return habilidad;
    }

    public void cambiarNombre(String nombreHabilidad, String nuevoNombre) {
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        Habilidad habilidad = buscarHabilidadPorNombre(nombreHabilidad);
        habilidad.setNombre(nuevoNombre);
        habilidadDAO.actualizarHabilidad(habilidad);
    }

    public void cambiarDescripcion(String nombreHabilidad, String nuevaDescripcion) {
        if (nuevaDescripcion == null || nuevaDescripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacío");
        }
        Habilidad habilidad = buscarHabilidadPorNombre(nombreHabilidad);
        habilidad.setDescripcion(nuevaDescripcion);
        habilidadDAO.actualizarHabilidad(habilidad);
    }

    public boolean habilidadUsadaPorUnPersonaje(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la habilidad no puede estar vacío");
        }
        return habilidadDAO.habilidadPerteneceAUnPersonaje(nombre);
    }

    public void borrarHabilidad(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la habilidad no puede estar vacío");
        }

        Habilidad habilidad = habilidadDAO.buscarHabilidadPorNombreConPersonajes(nombre);

        if (habilidad == null) {
            throw new IllegalArgumentException("Habilidad no encontrada");
        }

          List<Personaje> personajes = new ArrayList<>(habilidad.getPersonajes());

        for (Personaje personaje : personajes) {
            personaje.getHabilidades().remove(habilidad);
            personajeDAO.actualizarPersonaje(personaje);
        }

        habilidadDAO.borrarHabilidad(habilidad);
    }



}
