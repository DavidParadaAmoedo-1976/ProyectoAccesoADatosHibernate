package marvel.servicios;

import marvel.modelo.dao.EventoDAO;
import marvel.modelo.dao.GenericDAO;
import marvel.modelo.entidades.Evento;

import java.util.List;

public class EventoServicio {

    private final EventoDAO eventoDAO = new EventoDAO();

    public Evento buscarEventoPorNombre(String nombre) {
        Evento evento = eventoDAO.buscarEventoPorNombre(nombre);
        if (evento == null) {
            throw new IllegalArgumentException("No se encontro el evento");
        }
        return evento;
    }

    public List<Evento> buscarTodosLosEventos() {
        List<Evento> eventos = eventoDAO.buscarTodosLosEventos();
        return eventos;
    }

    public void crearEvento(String nombreEvento, String lugarEvento) {
        int idEvento = GenericDAO.siguienteId(Evento.class, "id");
        Evento evento = new Evento();
        evento.setId(idEvento);
        evento.setNombre(nombreEvento);
        evento.setLugar(lugarEvento);
        eventoDAO.guardarEvento(evento);
    }
}


