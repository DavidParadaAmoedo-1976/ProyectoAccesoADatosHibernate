package marvel.servicios;

import marvel.modelo.dao.GenericDAO;
import marvel.modelo.dao.HabilidadDAO;
import marvel.modelo.entidades.Habilidad;
import marvel.modelo.entidades.Personaje;

public class HabilidadServicio {
    private HabilidadDAO habilidadDAO;

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

    public Habilidad buscarPorNombre(String nombre) {
        Habilidad habilidad = habilidadDAO.buscarHabilidadPorNombre(nombre);
        if (habilidad == null) {
            throw  new RuntimeException("Habilidad no encontrada");
        }
        return habilidad;
    }

}
