package marvel.servicios;

import marvel.modelo.dao.GenericDAO;
import marvel.modelo.dao.PersonajeDAO;
import marvel.modelo.dao.TrajeDAO;
import marvel.modelo.entidades.Personaje;
import marvel.modelo.entidades.Traje;

public class PersonajeServicio {

    private final PersonajeDAO personajeDAO;
    private final TrajeDAO trajeDAO;

    public PersonajeServicio(PersonajeDAO personajeDAO, TrajeDAO trajeDAO) {
        this.personajeDAO = personajeDAO;
        this.trajeDAO = trajeDAO;
    }

    public void crearPersonaje(String nombre, String alias, Traje traje) {

        int idPersonaje = GenericDAO.siguienteId(Personaje.class, "id");

        Personaje personaje = new Personaje();
        personaje.setId(idPersonaje);
        personaje.setNombre(nombre);
        personaje.setAlias(alias);

        if (traje != null) {

            if (traje.getId() == 0) {
                int idTraje = GenericDAO.siguienteId(Traje.class, "id");
                traje.setId(idTraje);
                trajeDAO.guardar(traje);
            }
            personaje.setTraje(traje);
            traje.setPersonaje(personaje);
        }
        personajeDAO.guardar(personaje);
    }

    public void cambiarNombre(int idPersonaje, String nuevoNombre) {

        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        Personaje personaje = buscarPorId(idPersonaje);
        personaje.setNombre(nuevoNombre);
        personajeDAO.actualizar(personaje);
    }



    public void cambiarAlias(int idPersonaje, String nuevoAlias) {
        if (nuevoAlias == null || nuevoAlias.isBlank()) {
            throw new IllegalArgumentException("El Alias no puede estar vacío");
        }
        Personaje personaje = buscarPorId(idPersonaje);

        personaje.setAlias(nuevoAlias);
        personajeDAO.actualizar(personaje);
    }


    public void cambiarTraje(int idPersonaje, Traje nuevoTraje) {

        Personaje personaje = buscarPorId(idPersonaje);

        if (personaje.getTraje() != null) {
            personaje.getTraje().setPersonaje(null);
        }

        if (nuevoTraje != null) {

            if (nuevoTraje.getId() == 0) {
                int idTraje = GenericDAO.siguienteId(Traje.class, "id");
                nuevoTraje.setId(idTraje);
                trajeDAO.guardar(nuevoTraje);
            }

            nuevoTraje.setPersonaje(personaje);
            personaje.setTraje(nuevoTraje);
        } else {
            personaje.setTraje(null);
        }

        personajeDAO.actualizar(personaje);
    }

    private Personaje buscarPorId(int idPersonaje) {
        Personaje personaje = personajeDAO.buscarPorId(idPersonaje);
        if (personaje == null) {
            throw new IllegalArgumentException("Personaje no encontrado");
        }
        return personaje;
    }
}


