package marvel;

import marvel.controlador.MarvelControlador;
import marvel.modelo.dao.*;
import marvel.modelo.util.HibernateUtil;
import marvel.servicios.HabilidadServicio;
import marvel.servicios.PersonajeServicio;
import marvel.vista.MarvelVista;

public class Programa {

    public static void main(String[] args) {

        HibernateUtil.getSessionFactory();

        MarvelVista vista = new MarvelVista();

        PersonajeDAO personajeDAO = new PersonajeDAO();
        TrajeDAO trajeDAO = new TrajeDAO();
        HabilidadDAO habilidadDAO = new HabilidadDAO();
        EventoDAO eventoDAO = new EventoDAO();
        GenericDAO genericDAO = new GenericDAO();

        PersonajeServicio personajeServicio =
                new PersonajeServicio(personajeDAO, trajeDAO, habilidadDAO);
        HabilidadServicio habilidadServicio = new HabilidadServicio(habilidadDAO);

        MarvelControlador controlador = new MarvelControlador(
                vista,
                personajeServicio,
                habilidadServicio,
                trajeDAO,
                personajeDAO,
                habilidadDAO,
                eventoDAO);

        controlador.ejecuta();
    }

}