package marvel.modelo.dao;

import marvel.modelo.entidades.Evento;
import marvel.modelo.entidades.Participa;
import marvel.modelo.entidades.Personaje;
import org.hibernate.Session;

import java.util.List;

public class ParticipaDAO {

    public void guardarParticipa(Session session, Participa participa) {
        session.persist(participa);
    }

    public List<Personaje> buscarPersonajesDeUnEvento(Session session, Evento evento) {
        return session.createQuery(
                        "SELECT p.personaje FROM Participa p WHERE p.evento=:evento",
                        Personaje.class
                ).setParameter("evento", evento)
                .getResultList();
    }

    public void borrarParticipacionesDePersonaje(Session session, int idPersonaje) {
        session.createQuery(
                        "DELETE FROM Participa p WHERE p.personaje.id=:id"
                ).setParameter("id", idPersonaje)
                .executeUpdate();
    }
}
