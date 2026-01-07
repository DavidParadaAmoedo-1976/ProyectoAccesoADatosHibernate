package marvel.servicios;

import marvel.modelo.dao.ParticipaDAO;
import marvel.modelo.entidades.Evento;
import marvel.modelo.entidades.Participa;
import marvel.modelo.entidades.Personaje;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ParticipaServicio {

    private final ParticipaDAO participaDAO = new ParticipaDAO();

    public void crearParticipa(Evento evento, Personaje personaje, LocalDate fecha, String rol) {
        if (evento == null) {
            throw new IllegalArgumentException("Evento no puede ser nulo");
        }
        if (personaje == null) {
            throw new IllegalArgumentException("Personaje no puede ser nulo");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("Fecha no puede ser nula");
        }
        if (rol == null || rol.isBlank()) {
            throw new IllegalArgumentException("Rol no puede estar vacío");
        }
        Participa participa = new Participa();
        participa.setEvento(evento);
        participa.setPersonaje(personaje);
        participa.setFecha(fecha);
        participa.setRol(rol);
        participaDAO.guardarParticipa(participa);
    }

    public List<Personaje> buscarPersonajesDeUnEvento(Evento evento) {
        List<Personaje> personajes = participaDAO.buscarPersonajesDeUnEvento(evento);
        return personajes;
    }
}
