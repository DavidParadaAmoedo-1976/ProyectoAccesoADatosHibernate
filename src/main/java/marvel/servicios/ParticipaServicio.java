package marvel.servicios;

import marvel.modelo.dao.ParticipaDAO;
import marvel.modelo.entidades.Evento;
import marvel.modelo.entidades.Participa;
import marvel.modelo.entidades.Personaje;
import marvel.modelo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class ParticipaServicio {

    private final ParticipaDAO participaDAO = new ParticipaDAO();

    public void crearParticipa(Evento evento, Personaje personaje, LocalDate fecha, String rol) {
        Session session = HibernateUtil.get().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Participa participa = new Participa();
            participa.setEvento(evento);
            participa.setPersonaje(personaje);
            participa.setFecha(fecha);
            participa.setRol(rol);
            participaDAO.guardarParticipa(session, participa);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public List<Personaje> buscarPersonajesDeUnEvento(Evento evento) {
        Session session = HibernateUtil.get().openSession();
        try {
            return participaDAO.buscarPersonajesDeUnEvento(session, evento);
        } finally {
            session.close();
        }
    }
}
