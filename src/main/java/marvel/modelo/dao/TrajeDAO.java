package marvel.modelo.dao;

import marvel.modelo.entidades.Traje;
import org.hibernate.Session;

import java.util.List;

public class TrajeDAO {

    public void guardar(Session session, Traje traje) {
        session.persist(traje);
    }

    public Traje buscarPorId(Session session, int id) {
        return session.find(Traje.class, id);
    }

    public List<Traje> buscarTrajesDisponibles(Session session) {
        return session.createQuery(
                "FROM Traje t WHERE t.personaje IS NULL",
                Traje.class
        ).getResultList();
    }
}
