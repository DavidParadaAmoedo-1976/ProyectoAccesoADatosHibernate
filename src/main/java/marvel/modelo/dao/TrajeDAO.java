package marvel.modelo.dao;

import marvel.modelo.entidades.Traje;
import marvel.modelo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TrajeDAO {

    public void guardar(Traje traje) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(traje);
        tx.commit();
        session.close();
    }

    public Traje buscarPorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Traje traje = session.find(Traje.class, id);
        session.close();
        return traje;
    }

    public List<Traje> buscarDisponibles() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Traje> lista = session.createQuery(
                "FROM Traje traje WHERE traje.personaje IS NULL", Traje.class).list();
        session.close();
        return lista;
    }

}

