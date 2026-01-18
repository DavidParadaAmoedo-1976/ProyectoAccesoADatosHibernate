package marvel.modelo.util;

import java.io.File;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;

    static {
        try {
            String hibernatePropsFilePath = "src/main/resources/hibernate.cfg.xml";
            File hibernatePropsFile = new File(hibernatePropsFilePath);
            SESSION_FACTORY = new Configuration().configure(hibernatePropsFile).buildSessionFactory();
        }catch(Throwable ex) {
            System.err.println("Error al crear la configuración de hibernate" + ex.getMessage());
            throw new ExceptionInInitializerError();
        }
    }

    public static SessionFactory get() {
        return SESSION_FACTORY;
    }

    public static void cerrar() {
        if (SESSION_FACTORY != null && !SESSION_FACTORY.isClosed()) {
            SESSION_FACTORY.close();
        }
    }
}
