package marvel.servicios;

import marvel.modelo.dao.EventoDAO;
import marvel.modelo.dao.GenericDAO;
import marvel.modelo.entidades.Evento;
import marvel.modelo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EventoServicio {

    private final EventoDAO eventoDAO = new EventoDAO();

    /* ===================== CONSULTAS ===================== */

    public Evento buscarEventoPorNombre(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("No se encontró el nombre del evento");
        }

        Session session = HibernateUtil.get().openSession();
        try {
            Evento evento = eventoDAO.buscarEventoPorNombre(session, nombre);
            if (evento == null) {
                throw new IllegalArgumentException("No se encontró el evento");
            }
            return evento;
        } finally {
            session.close();
        }
    }

    public List<Evento> buscarTodosLosEventos() {
        Session session = HibernateUtil.get().openSession();
        try {
            return eventoDAO.buscarTodosLosEventos(session);
        } finally {
            session.close();
        }
    }

    public boolean existeEventoConNombre(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("No se encontró el nombre del evento");
        }

        Session session = HibernateUtil.get().openSession();
        try {
            return eventoDAO.existeEventoConNombre(session, nombre);
        } finally {
            session.close();
        }
    }

    /* ===================== CREAR ===================== */

    public void crearEvento(String nombreEvento, String lugarEvento) {

        if (nombreEvento == null || nombreEvento.isBlank()) {
            throw new IllegalArgumentException("El nombre del evento no puede estar vacío");
        }
        if (lugarEvento == null || lugarEvento.isBlank()) {
            throw new IllegalArgumentException("El lugar del evento no puede estar vacío");
        }

        Session session = HibernateUtil.get().openSession();
        Transaction tx = session.beginTransaction();

        try {
            int idEvento = GenericDAO.siguienteId(session, Evento.class, "id");

            Evento evento = new Evento();
            evento.setId(idEvento);
            evento.setNombre(nombreEvento);
            evento.setLugar(lugarEvento);

            eventoDAO.guardarEvento(session, evento);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
