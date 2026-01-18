package marvel.servicios;

import marvel.modelo.dao.*;
import marvel.modelo.entidades.Habilidad;
import marvel.modelo.entidades.Personaje;
import marvel.modelo.entidades.Traje;
import marvel.modelo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PersonajeServicio {

    private final PersonajeDAO personajeDAO;
    private final TrajeDAO trajeDAO;
    private final HabilidadDAO habilidadDAO;
    private final ParticipaDAO participaDAO;

    public PersonajeServicio(PersonajeDAO personajeDAO,
                             TrajeDAO trajeDAO,
                             HabilidadDAO habilidadDAO,
                             ParticipaDAO participaDAO) {
        this.personajeDAO = personajeDAO;
        this.trajeDAO = trajeDAO;
        this.habilidadDAO = habilidadDAO;
        this.participaDAO = participaDAO;
    }

    public void crearPersonaje(String nombre, String alias, Traje traje) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (alias == null || alias.isBlank()) {
            throw new IllegalArgumentException("El alias no puede estar vacío");
        }
        Session session = HibernateUtil.get().openSession();
        Transaction tx = session.beginTransaction();
        try {
            int idPersonaje = GenericDAO.siguienteId(session, Personaje.class, "id");
            Personaje personaje = new Personaje();
            personaje.setId(idPersonaje);
            personaje.setNombre(nombre);
            personaje.setAlias(alias);
            if (traje != null) {
                personaje.setTraje(traje);
                traje.setPersonaje(personaje);
            }
            personajeDAO.guardarPersonaje(session, personaje);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void borrarPersonaje(int id) {
        Session session = HibernateUtil.get().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Personaje personaje = personajeDAO.buscarPersonajePorId(session, id);
            if (personaje == null) {
                throw new IllegalArgumentException("Personaje no encontrado.");
            }
            if (personaje.getTraje() != null) {
                personaje.getTraje().setPersonaje(null);
                personaje.setTraje(null);
            }
            personajeDAO.borrarPersonaje(session, personaje);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void borrarPersonajeForzado(int idPersonaje) {
        Session session = HibernateUtil.get().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Personaje personaje = personajeDAO.buscarPersonajePorId(session, idPersonaje);
            if (personaje == null) {
                throw new IllegalArgumentException("Personaje no encontrado");
            }
            participaDAO.borrarParticipacionesDePersonaje(session, idPersonaje);
            if (personaje.getTraje() != null) {
                personaje.getTraje().setPersonaje(null);
                personaje.setTraje(null);
            }
            personajeDAO.borrarPersonaje(session, personaje);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void cambiarNombre(int idPersonaje, String nuevoNombre) {
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        Session session = HibernateUtil.get().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Personaje personaje = personajeDAO.buscarPersonajePorId(session, idPersonaje);
            if (personaje == null) {
                throw new IllegalArgumentException("Personaje no encontrado");
            }
            personaje.setNombre(nuevoNombre);
            personajeDAO.actualizarPersonaje(session, personaje);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void cambiarAlias(int idPersonaje, String nuevoAlias) {
        if (nuevoAlias == null || nuevoAlias.isBlank()) {
            throw new IllegalArgumentException("El alias no puede estar vacío");
        }
        Session session = HibernateUtil.get().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Personaje personaje = personajeDAO.buscarPersonajePorId(session, idPersonaje);
            if (personaje == null) {
                throw new IllegalArgumentException("Personaje no encontrado");
            }
            personaje.setAlias(nuevoAlias);
            personajeDAO.actualizarPersonaje(session, personaje);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void cambiarTraje(int idPersonaje, Traje nuevoTraje) {
        Session session = HibernateUtil.get().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Personaje personaje = personajeDAO.buscarPersonajePorId(session, idPersonaje);
            if (personaje == null) {
                throw new IllegalArgumentException("Personaje no encontrado");
            }
            if (personaje.getTraje() != null) {
                personaje.getTraje().setPersonaje(null);
            }
            if (nuevoTraje != null) {
                nuevoTraje.setPersonaje(personaje);
                personaje.setTraje(nuevoTraje);
            } else {
                personaje.setTraje(null);
            }
            personajeDAO.actualizarPersonaje(session, personaje);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public List<Personaje> buscarTodosLosPersonajes() {
        Session session = HibernateUtil.get().openSession();
        try {
            List<Personaje> personajes = personajeDAO.buscarTodosLosPersonajes(session);
            if (personajes.isEmpty()) {
                throw new IllegalArgumentException("No hay personajes en la lista.");
            }
            return personajes;
        } finally {
            session.close();
        }
    }

    public Personaje buscarPorId(int idPersonaje) {
        Session session = HibernateUtil.get().openSession();
        try {
            Personaje personaje = personajeDAO.buscarPersonajePorId(session, idPersonaje);
            if (personaje == null) {
                throw new IllegalArgumentException("Personaje no encontrado");
            }
            return personaje;
        } finally {
            session.close();
        }
    }

    public Personaje buscarPersonajePorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        Session session = HibernateUtil.get().openSession();
        try {
            Personaje personaje = personajeDAO.buscarPersonajePorNombre(session, nombre);
            if (personaje == null) {
                throw new IllegalArgumentException("Personaje no encontrado");
            }
            return personaje;
        } finally {
            session.close();
        }
    }

    public Personaje obtenerPersonajeConDatos(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        Session session = HibernateUtil.get().openSession();
        try {
            return personajeDAO.buscarPorNombreConTodo(session, nombre);
        } finally {
            session.close();
        }
    }

    public long contarPersonajesPorHabilidad(String nombreHabilidad) {
        if (nombreHabilidad == null || nombreHabilidad.isBlank()) {
            throw new IllegalArgumentException("El nombre de la habilidad no puede estar vacío");
        }
        Session session = HibernateUtil.get().openSession();
        try {
            return personajeDAO.contarPersonajesPorHabilidad(session, nombreHabilidad);
        } finally {
            session.close();
        }
    }

    public boolean participaEnEvento(int idPersonaje) {
        Session session = HibernateUtil.get().openSession();
        try {
            return personajeDAO.personajeParticipaEnEvento(session, idPersonaje);
        } finally {
            session.close();
        }
    }

    public void asignarHabilidad(String nombrePersonaje, String nombreHabilidad) {
        if (nombrePersonaje == null || nombrePersonaje.isBlank()) {
            throw new IllegalArgumentException("El nombre del personaje no puede estar vacío");
        }
        if (nombreHabilidad == null || nombreHabilidad.isBlank()) {
            throw new IllegalArgumentException("El nombre de la habilidad no puede estar vacío");
        }
        Session session = HibernateUtil.get().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Personaje personaje =
                    personajeDAO.buscarPorNombreConHabilidades(session, nombrePersonaje);
            Habilidad habilidad =
                    habilidadDAO.buscarHabilidadPorNombreConPersonajes(session, nombreHabilidad);
            if (personaje == null) {
                throw new IllegalArgumentException("Personaje no encontrado");
            }
            if (habilidad == null) {
                throw new IllegalArgumentException("Habilidad no encontrada");
            }
            if (personaje.getHabilidades().contains(habilidad)) {
                throw new IllegalArgumentException("El personaje ya tiene esa habilidad");
            }
            personaje.getHabilidades().add(habilidad);
            habilidad.getPersonajes().add(personaje);
            personajeDAO.actualizarPersonaje(session, personaje);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
