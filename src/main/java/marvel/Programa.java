package marvel;

import marvel.controlador.MarvelControlador;
import marvel.modelo.dao.*;
import marvel.modelo.entidades.Traje;
import marvel.modelo.util.HibernateUtil;
import marvel.servicios.*;
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

        PersonajeServicio personajeServicio = new PersonajeServicio(personajeDAO, trajeDAO, habilidadDAO);
        HabilidadServicio habilidadServicio = new HabilidadServicio(habilidadDAO);
        TrajeServicio trajeServicio = new TrajeServicio(trajeDAO);
        EventoServicio eventoServicio = new EventoServicio();
        ParticipaServicio participaServicio = new ParticipaServicio();

        MarvelControlador controlador = new MarvelControlador(
                vista,
                personajeServicio,
                habilidadServicio,
                trajeServicio,
                eventoServicio,
                participaServicio
        );

        controlador.ejecuta();
    }

}