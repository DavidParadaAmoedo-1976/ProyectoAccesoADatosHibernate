package marvel.modelo.enums;

public enum MenuEnum {

    SALIR("Salir"),

    CREAR_PERSONAJE("Crear personaje."),
    BORRAR_PERSONAJE("Borrar personaje (por id)."),
    MODIFICAR_PERSONAJE("Modificar personaje."),

    CREAR_HABILIDAD("Crear habilidad."),
    BORRAR_HABILIDAD("Borrar habilidad."),
    MODIFICAR_HABILIDAD("Modificar habilidad."),

    ASIGNAR_HABILIDAD("Asignar habilidad a un personaje."),
    REGISTRAR_PARTICIPACION("Registrar participación en un evento."),
    CAMBIAR_TRAJE("Cambiar el traje de un personaje."),

    MOSTRAR_PERSONAJE("Mostrar los datos de un personaje."),
    MOSTRAR_PERSONAJES_EVENTO("Mostrar los personajes de un evento."),
    CONTAR_POR_HABILIDAD("Contar cuantos personajes tienen una habilidad concreta.");


    private final String texto;

    MenuEnum(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
