package marvel.modelo.dao;

import marvel.modelo.entidades.Habilidad;
import org.hibernate.Session;

import java.util.List;

public class HabilidadDAO {

    public void guardarHabilidades(Session session, Habilidad habilidad) {
        session.persist(habilidad);
    }

    public List<Habilidad> buscarTodasLasHabilidades(Session session) {
        return session.createQuery("FROM Habilidad", Habilidad.class).getResultList();
    }

    public Habilidad buscarHabilidadPorNombre(Session session, String nombre) {
        return session.createQuery(
                        "FROM Habilidad h WHERE lower(h.nombre)=:nombre",
                        Habilidad.class
                ).setParameter("nombre", nombre.toLowerCase())
                .uniqueResult();
    }

    public Habilidad buscarHabilidadPorNombreConPersonajes(Session session, String nombre) {
        return session.createQuery(
                        "SELECT DISTINCT h FROM Habilidad h " +
                                "LEFT JOIN FETCH h.personajes p " +
                                "LEFT JOIN FETCH p.habilidades " +
                                "WHERE lower(h.nombre)=:nombre",
                        Habilidad.class
                ).setParameter("nombre", nombre.toLowerCase())
                .uniqueResult();
    }

    public void actualizarHabilidad(Session session, Habilidad habilidad) {
        session.merge(habilidad);
    }

    public void borrarHabilidad(Session session, Habilidad habilidad) {
        session.remove(habilidad);
    }

    public boolean habilidadPerteneceAUnPersonaje(Session session, String nombre) {
        Long count = session.createQuery(
                        "SELECT COUNT(p) FROM Personaje p JOIN p.habilidades h WHERE lower(h.nombre)=:nombre",
                        Long.class
                ).setParameter("nombre", nombre.toLowerCase())
                .uniqueResult();
        return count != null && count > 0;
    }
}
