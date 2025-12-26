package marvel.modelo.dao;

import marvel.modelo.entidades.Personaje;
import marvel.modelo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PersonajeDAO {

    public List<Personaje> buscarTodosLosPersonajes() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Personaje> todosLosPersonajes = session
                .createQuery("FROM Personaje", Personaje.class)
                .getResultList();
        session.close();

        return todosLosPersonajes;
    }

    public void guardarPersonaje(Personaje personaje) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(personaje);
        tx.commit();
        session.close();
    }

    public Personaje buscarPersonajePorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Personaje personaje = session.find(Personaje.class, id);
        session.close();
        return personaje;
    }

    public void borrarPersonaje(Personaje personaje) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        session.remove(personaje);
        trx.commit();
        session.close();
    }

    public void actualizarPersonaje(Personaje personaje) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.merge(personaje);
        tx.commit();
        session.close();
    }

    public Personaje buscarPersonajePorNombre(String nombrePersonaje) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Personaje personaje = session.createQuery("FROM Personaje p WHERE lower(p.nombre) = :nombre",
                Personaje.class).setParameter("nombre", nombrePersonaje.toLowerCase()).uniqueResult();

        session.close();
        return personaje;
    }
}
