package marvel.servicios;

import marvel.modelo.dao.GenericDAO;
import marvel.modelo.dao.TrajeDAO;
import marvel.modelo.entidades.Traje;

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
}
