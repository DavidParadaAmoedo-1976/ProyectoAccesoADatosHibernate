package marvel.modelo.dao;

import marvel.modelo.entidades.Personaje;
import org.hibernate.Session;

import java.util.List;

public class PersonajeDAO {

    public void guardarPersonaje(Session session, Personaje personaje) {
        session.persist(personaje);
    }

    public void actualizarPersonaje(Session session, Personaje personaje) {
        session.merge(personaje);
    }

    public void borrarPersonaje(Session session, Personaje personaje) {
        session.remove(personaje);
    }

    public Personaje buscarPersonajePorId(Session session, int id) {
        return session.find(Personaje.class, id);
    }

    public List<Personaje> buscarTodosLosPersonajes(Session session) {
        return session.createQuery("FROM Personaje", Personaje.class).getResultList();
    }

    public Personaje buscarPersonajePorNombre(Session session, String nombre) {
        return session.createQuery(
                        "FROM Personaje p WHERE lower(p.nombre)=:nombre",
                        Personaje.class
                ).setParameter("nombre", nombre.toLowerCase())
                .uniqueResult();
    }

    public Personaje buscarPorNombreConHabilidades(Session session, String nombre) {
        return session.createQuery(
                        "SELECT p FROM Personaje p LEFT JOIN FETCH p.habilidades WHERE lower(p.nombre)=:nombre",
                        Personaje.class
                ).setParameter("nombre", nombre.toLowerCase())
                .uniqueResult();
    }

    public long contarPersonajesPorHabilidad(Session session, String nombreHabilidad) {
        Long total = session.createQuery(
                        "SELECT COUNT(p) FROM Personaje p JOIN p.habilidades h WHERE lower(h.nombre)=:nombre",
                        Long.class
                ).setParameter("nombre", nombreHabilidad.toLowerCase())
                .uniqueResult();
        return total != null ? total : 0;
    }

    public Personaje buscarPorNombreConTodo(Session session, String nombre) {
        return session.createQuery(
                        "SELECT DISTINCT p FROM Personaje p " +
                                "LEFT JOIN FETCH p.traje " +
                                "LEFT JOIN FETCH p.habilidades " +
                                "LEFT JOIN FETCH p.participaciones part " +
                                "LEFT JOIN FETCH part.evento " +
                                "WHERE lower(p.nombre)=:nombre",
                        Personaje.class
                ).setParameter("nombre", nombre.toLowerCase())
                .uniqueResult();
    }

    public boolean personajeParticipaEnEvento(Session session, int idPersonaje) {
        Long count = session.createQuery(
                        "SELECT COUNT(p) FROM Participa p WHERE p.personaje.id=:id",
                        Long.class
                ).setParameter("id", idPersonaje)
                .uniqueResult();
        return count != null && count > 0;
    }
}
