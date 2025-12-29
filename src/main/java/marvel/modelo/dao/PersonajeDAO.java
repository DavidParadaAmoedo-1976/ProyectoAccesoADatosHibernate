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

    public Personaje buscarPorNombreConHabilidades(String nombre) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Personaje p = session.createQuery(
                        "SELECT p FROM Personaje p LEFT JOIN FETCH p.habilidades WHERE lower(p.nombre)=:nombre",
                        Personaje.class
                ).setParameter("nombre", nombre.toLowerCase())
                .uniqueResult();
        session.close();
        return p;
    }

    public long contarPersonajesPorHabilidad(String nombreHabilidad) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Long total = session.createQuery("SELECT COUNT(p) FROM Personaje p JOIN p.habilidades h WHERE lower(h.nombre) = :nombre",
                Long.class).setParameter("nombre", nombreHabilidad.toLowerCase()).uniqueResult();

        session.close();
        return total != null ? total : 0;
    }

    public Personaje buscarPorNombreConTodo(String nombre) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Personaje personaje = session.createQuery(
                        "SELECT DISTINCT p FROM Personaje p " +
                                "LEFT JOIN FETCH p.traje " +
                                "LEFT JOIN FETCH p.habilidades " +
                                "LEFT JOIN FETCH p.participaciones part " +
                                "LEFT JOIN FETCH part.evento " +
                                "WHERE lower(p.nombre) = :nombre",
                        Personaje.class
                )
                .setParameter("nombre", nombre.toLowerCase())
                .uniqueResult();

        session.close();
        return personaje;
    }

}
