package marvel.servicios;

import marvel.modelo.dao.GenericDAO;
import marvel.modelo.dao.PersonajeDAO;
import marvel.modelo.dao.TrajeDAO;
import marvel.modelo.entidades.Personaje;
import marvel.modelo.entidades.Traje;

public class PersonajeServicio {


    private final PersonajeDAO personajeDAO = new PersonajeDAO();
    private final TrajeDAO trajeDAO = new TrajeDAO();

    public void crearPersonaje(String nombre, String alias, Traje traje) {

        int idPersonaje = GenericDAO.siguienteId(Personaje.class, "id");

        Personaje personaje = new Personaje();
        personaje.setId(idPersonaje);
        personaje.setNombre(nombre);
        personaje.setAlias(alias);

        if (traje != null) {

            // si es nuevo, no tiene ID
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


//    public void crearPersonaje(String nombre, String alias, Traje traje) {
//
//        Integer id_personaje = GenericDAO.siguienteId(Personaje.class,"id");
//
//        Personaje personaje = new Personaje();
//        personaje.setId(id_personaje);
//        personaje.setNombre(nombre);
//        personaje.setAlias(alias);
//
//        if (traje != null) {
//            // Traje YA existe en BD o se guarda antes
//            personaje.setTraje(traje);
//            traje.setPersonaje(personaje);
//        }
//
//        personajeDAO.guardar(personaje);
//    }
}


