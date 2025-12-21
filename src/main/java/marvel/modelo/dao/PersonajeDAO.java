package marvel.modelo.dao;

import marvel.modelo.entidades.Personaje;
import marvel.modelo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PersonajeDAO {

    public void guardar(Personaje personaje) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(personaje);
        tx.commit();
        session.close();
    }

    public Personaje buscarPorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Personaje personaje = session.find(Personaje.class, id);
        session.close();
        return personaje;
    }
}
