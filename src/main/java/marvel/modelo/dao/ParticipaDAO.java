package marvel.modelo.dao;

import marvel.modelo.entidades.Participa;
import marvel.modelo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ParticipaDAO {

    public void guardarParticipa(Participa participa) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(participa);
        tx.commit();
        session.close();
    }

}
