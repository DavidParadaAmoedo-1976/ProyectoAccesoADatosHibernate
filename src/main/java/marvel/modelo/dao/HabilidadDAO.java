package marvel.modelo.dao;

import marvel.modelo.entidades.Habilidad;
import marvel.modelo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HabilidadDAO {
    public void guardarHabilidades(Habilidad habilidad) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(habilidad);
        tx.commit();
        session.close();
    }

    public List<Habilidad> buscarTodasLasHabilidades() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Habilidad> todasLasHabilidades = session
                .createQuery("FROM Habilidad", Habilidad.class)
                .getResultList();
        session.close();

        return todasLasHabilidades;
    }

    public Habilidad buscarHabilidadPorNombre(String nombreHabilidad) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Habilidad habilidad = session.createQuery("FROM Habilidad h WHERE lower(h.nombre) = :nombre",
                                Habilidad.class).setParameter("nombre", nombreHabilidad.toLowerCase()).uniqueResult();

        session.close();
        return habilidad;
    }

    public void borrarHabilidad(Habilidad habilidad){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.remove(habilidad);
        tx.commit();
        session.close();
    }

    public void actualizarHabilidad(Habilidad habilidad){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.merge(habilidad);
        tx.commit();
        session.close();
    }

}
