package marvel.modelo.dao;

import org.hibernate.Session;

public class GenericDAO {

    public static int siguienteId(Session session, Class<?> entidad, String campoId) {
        Integer max = session.createQuery(
                "SELECT max(e." + campoId + ") FROM " + entidad.getSimpleName() + " e",
                Integer.class
        ).uniqueResult();

        return (max != null) ? max + 1 : 1;
    }
}


