package marvel.servicios;

import marvel.modelo.dao.GenericDAO;
import marvel.modelo.dao.HabilidadDAO;
import marvel.modelo.entidades.Habilidad;

import java.util.List;

public class HabilidadServicio {
    private final HabilidadDAO habilidadDAO;

    public HabilidadServicio(HabilidadDAO habilidadDAO) {
        this.habilidadDAO = habilidadDAO;
    }


    public void crearHabilidad(String nombre, String descripcion) {
        int idHabilidad = GenericDAO.siguienteId(Habilidad.class, "id");

        Habilidad habilidad = new Habilidad();
        habilidad.setId(idHabilidad);
        habilidad.setNombre(nombre);
        habilidad.setDescripcion(descripcion);

        habilidadDAO.guardarHabilidades(habilidad);
    }

    public void borrarHabilidad(Habilidad habilidad) {
        habilidadDAO.borrarHabilidad(habilidad);
    }

    public List<Habilidad> buscarTodasLasHabilidades() {
        return habilidadDAO.buscarTodasLasHabilidades();
    }

    public Habilidad buscarPorNombre(String nombre) {

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

        Habilidad habilidad = buscarPorNombre(nombreHabilidad);
        habilidad.setNombre(nuevoNombre);
        habilidadDAO.actualizarHabilidad(habilidad);
    }

    public void cambiarDescripcion(String nombreHabilidad, String nuevaDescripcion) {
        if (nuevaDescripcion == null || nuevaDescripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacío");
        }
        Habilidad habilidad = buscarPorNombre(nombreHabilidad);
        habilidad.setDescripcion(nuevaDescripcion);
        habilidadDAO.actualizarHabilidad(habilidad);
    }


}
