package marvel.modelo.dao;

import marvel.modelo.entidades.Evento;
import marvel.modelo.entidades.Participa;
import marvel.modelo.entidades.Personaje;
import marvel.modelo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ParticipaDAO {

    public void guardarParticipa(Participa participa) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(participa);
        tx.commit();
        session.close();
    }

    public List<Personaje> buscarPersonajesDeUnEvento(Evento evento) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Personaje> personajes = session.createQuery("SELECT p.personaje FROM Participa p WHERE p.evento = :evento",Personaje.class)
                .setParameter("evento", evento)
                .getResultList();
        session.close();
        return personajes;
    }
}
