package marvel.modelo.dao;

import marvel.modelo.entidades.Evento;
import marvel.modelo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EventoDAO {

    public void guardarEvento(Evento evento) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(evento);
        tx.commit();
        session.close();
    }

    public List<Evento> buscarTodosLosEventos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Evento> todosLosEventos = session
                .createQuery("FROM Evento", Evento.class)
                .getResultList();
        session.close();

        return todosLosEventos;
    }

    public Evento buscarEventoPorNombre(String nombreEvento) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Evento evento = session
                .createQuery("FROM Evento e WHERE lower(e.nombre) =: nombre", Evento.class)
                .setParameter("nombre", nombreEvento.toLowerCase()).uniqueResult();
        session.close();
        return evento;
    }
}
