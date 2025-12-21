package marvel;

import marvel.controlador.MarvelControlador;
import marvel.modelo.util.HibernateUtil;
import marvel.servicios.PersonajeServicio;
import marvel.vista.MarvelVista;
import org.hibernate.Session;

public class Programa {

    public static void main(String[] args) {

        MarvelVista vista = new MarvelVista();
        MarvelControlador controlador = new MarvelControlador(vista);

        controlador.ejecuta();
    }
}