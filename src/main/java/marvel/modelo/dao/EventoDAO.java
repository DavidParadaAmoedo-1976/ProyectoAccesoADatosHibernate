package marvel.modelo.dao;

import marvel.modelo.entidades.Evento;
import org.hibernate.Session;

import java.util.List;

public class EventoDAO {

    public void guardarEvento(Session session, Evento evento) {
        session.persist(evento);
    }

    public Evento buscarEventoPorNombre(Session session, String nombre) {
        return session.createQuery(
                        "FROM Evento e WHERE lower(e.nombre)=:nombre",
                        Evento.class
                ).setParameter("nombre", nombre.toLowerCase())
                .uniqueResult();
    }

    public List<Evento> buscarTodosLosEventos(Session session) {
        return session.createQuery("FROM Evento", Evento.class).getResultList();
    }

    public boolean existeEventoConNombre(Session session, String nombre) {
        Long count = session.createQuery(
                        "SELECT COUNT(e) FROM Evento e WHERE lower(e.nombre)=:nombre",
                        Long.class
                ).setParameter("nombre", nombre.toLowerCase())
                .uniqueResult();
        return count != null && count > 0;
    }
}
