package marvel.servicios;

import marvel.modelo.dao.GenericDAO;
import marvel.modelo.dao.HabilidadDAO;
import marvel.modelo.dao.PersonajeDAO;
import marvel.modelo.entidades.Habilidad;
import marvel.modelo.entidades.Personaje;
import marvel.modelo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class HabilidadServicio {

    private final HabilidadDAO habilidadDAO;
    private final PersonajeDAO personajeDAO;

    public HabilidadServicio(HabilidadDAO habilidadDAO, PersonajeDAO personajeDAO) {
        this.habilidadDAO = habilidadDAO;
        this.personajeDAO = personajeDAO;
    }

    public void crearHabilidad(String nombre, String descripcion) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        Session session = HibernateUtil.get().openSession();
        Transaction tx = session.beginTransaction();
        try {
            int idHabilidad = GenericDAO.siguienteId(session, Habilidad.class, "id");

            Habilidad habilidad = new Habilidad();
            habilidad.setId(idHabilidad);
            habilidad.setNombre(nombre);
            habilidad.setDescripcion(descripcion);
            habilidadDAO.guardarHabilidades(session, habilidad);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public List<Habilidad> buscarTodasLasHabilidades() {
        Session session = HibernateUtil.get().openSession();
        try {
            return habilidadDAO.buscarTodasLasHabilidades(session);
        } finally {
            session.close();
        }
    }

    public Habilidad buscarHabilidadPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        Session session = HibernateUtil.get().openSession();
        try {
            Habilidad habilidad = habilidadDAO.buscarHabilidadPorNombre(session, nombre);
            if (habilidad == null) {
                throw new IllegalArgumentException("La habilidad no existe");
            }
            return habilidad;
        } finally {
            session.close();
        }
    }

    public boolean habilidadUsadaPorUnPersonaje(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la habilidad no puede estar vacío");
        }
        Session session = HibernateUtil.get().openSession();
        try {
            return habilidadDAO.habilidadPerteneceAUnPersonaje(session, nombre);
        } finally {
            session.close();
        }
    }

    public void cambiarNombre(String nombreHabilidad, String nuevoNombre) {
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        Session session = HibernateUtil.get().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Habilidad habilidad =
                    habilidadDAO.buscarHabilidadPorNombre(session, nombreHabilidad);
            if (habilidad == null) {
                throw new IllegalArgumentException("La habilidad no existe");
            }
            habilidad.setNombre(nuevoNombre);
            habilidadDAO.actualizarHabilidad(session, habilidad);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void cambiarDescripcion(String nombreHabilidad, String nuevaDescripcion) {
        if (nuevaDescripcion == null || nuevaDescripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        Session session = HibernateUtil.get().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Habilidad habilidad =
                    habilidadDAO.buscarHabilidadPorNombre(session, nombreHabilidad);
            if (habilidad == null) {
                throw new IllegalArgumentException("La habilidad no existe");
            }
            habilidad.setDescripcion(nuevaDescripcion);
            habilidadDAO.actualizarHabilidad(session, habilidad);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void borrarHabilidad(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la habilidad no puede estar vacío");
        }
        Session session = HibernateUtil.get().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Habilidad habilidad =
                    habilidadDAO.buscarHabilidadPorNombreConPersonajes(session, nombre);
            if (habilidad == null) {
                throw new IllegalArgumentException("Habilidad no encontrada");
            }
            List<Personaje> personajes =
                    new ArrayList<>(habilidad.getPersonajes());
            for (Personaje personaje : personajes) {
                personaje.getHabilidades().remove(habilidad);
                personajeDAO.actualizarPersonaje(session, personaje);
            }
            habilidadDAO.borrarHabilidad(session, habilidad);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
