package marvel.modelo.dao;

import marvel.modelo.entidades.Personaje;
import marvel.modelo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PersonajeDAO {

    public List<Personaje> buscarTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Personaje> todosLosPersonajes = session
                .createQuery("FROM Personaje", Personaje.class)
                .getResultList();
        session.close();

        return todosLosPersonajes;
    }

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

    public void borrar(Personaje personaje) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        session.remove(personaje);
        trx.commit();
        session.close();
    }

    public void actualizar(Personaje personaje) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.merge(personaje);
        tx.commit();
        session.close();
    }

}
