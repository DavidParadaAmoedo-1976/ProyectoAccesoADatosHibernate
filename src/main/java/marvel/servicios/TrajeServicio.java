package marvel.servicios;

import marvel.modelo.dao.GenericDAO;
import marvel.modelo.dao.TrajeDAO;
import marvel.modelo.entidades.Traje;

import java.util.List;

public class TrajeServicio {
    private final TrajeDAO trajeDAO;

    public TrajeServicio(TrajeDAO trajeDAO) {
        this.trajeDAO = trajeDAO;
    }

    public Traje crearTraje(String especificacion) {
        int idTraje = GenericDAO.siguienteId(Traje.class,"id");
        
        Traje traje = new Traje();
        traje.setId(idTraje);
        traje.setEspecificacion(especificacion);
        
        trajeDAO.guardar(traje);
        return traje;
    }

    public Traje buscarTrajePorId(int idTraje) {
        Traje traje = trajeDAO.buscarPorId(idTraje);
        if (traje == null) {
            throw new IllegalArgumentException("Traje no encontrado.");
        }
        if (traje.getPersonaje() != null) {
            throw new IllegalArgumentException("El traje ya está asignado a otro personaje.");
        }
        return traje;
    }

    public List<Traje> buscarTrajesDisponibles() {
        return trajeDAO.buscarTrajesDisponibles();
    }
}
