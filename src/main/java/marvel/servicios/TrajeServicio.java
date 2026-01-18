package marvel.servicios;

import marvel.modelo.dao.GenericDAO;
import marvel.modelo.dao.TrajeDAO;
import marvel.modelo.entidades.Traje;
import marvel.modelo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TrajeServicio {

    private final TrajeDAO trajeDAO;

    public TrajeServicio(TrajeDAO trajeDAO) {
        this.trajeDAO = trajeDAO;
    }

    public Traje crearTraje(String especificacion) {
        Session session = HibernateUtil.get().openSession();
        Transaction tx = session.beginTransaction();
        try {
            int id = GenericDAO.siguienteId(session, Traje.class, "id");
            Traje traje = new Traje();
            traje.setId(id);
            traje.setEspecificacion(especificacion);
            trajeDAO.guardar(session, traje);
            tx.commit();
            return traje;
        } finally {
            session.close();
        }
    }

    public Traje buscarTrajePorId(int idTraje) {
        Session session = HibernateUtil.get().openSession();
        try {
            Traje traje = trajeDAO.buscarPorId(session, idTraje);
            if (traje == null) {
                throw new IllegalArgumentException("Traje no encontrado.");
            }
            if (traje.getPersonaje() != null) {
                throw new IllegalArgumentException("El traje ya está asignado.");
            }
            return traje;
        } finally {
            session.close();
        }
    }

    public List<Traje> buscarTrajesDisponibles() {
        Session session = HibernateUtil.get().openSession();
        try {
            return trajeDAO.buscarTrajesDisponibles(session);
        } finally {
            session.close();
        }
    }
}
