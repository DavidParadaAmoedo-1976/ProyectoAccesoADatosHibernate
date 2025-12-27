package marvel.modelo.dao;

import marvel.modelo.util.HibernateUtil;
import org.hibernate.Session;

public class GenericDAO {

    public GenericDAO() {
    }

    public static <T> int siguienteId(Class<T> entidad, String nombreCampoId) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        String entityName = session
                .getMetamodel()
                .entity(entidad)
                .getName();

        Integer idMaximo = session.createQuery(
                "SELECT MAX(entidad." + nombreCampoId + ") " +
                        "FROM " + entityName + " entidad",
                Integer.class
        ).uniqueResult();

        session.close();

        return (idMaximo == null) ? 1 : idMaximo + 1;
    }

}

