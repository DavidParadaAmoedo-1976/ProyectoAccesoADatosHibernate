package marvel.modelo.enums;

public enum MenuEnum {

    CREAR_PERSONAJE("Crear personaje"),
    BORRAR_PERSONAJE("Borrar personaje (por id)"),
    MODIFICAR_PERSONAJE("Modificar personaje"),

    CREAR_HABILIDAD("Crear habilidad"),
    BORRAR_HABILIDAD("Borrar habilidad (por nombre)"),
    MODIFICAR_HABILIDAD("Modificar habilidad"),

    ASIGNAR_HABILIDAD("Asignar habilidad a personaje"),
    REGISTRAR_PARTICIPACION("Registrar participación en evento"),
    CAMBIAR_TRAJE("Cambiar traje de un personaje"),

    MOSTRAR_PERSONAJE("Mostrar datos de un personaje"),
    MOSTRAR_PERSONAJES_EVENTO("Mostrar personajes de un evento"),
    CONTAR_POR_HABILIDAD("Contar personajes con una habilidad"),

    SALIR("Salir");

    private final String texto;

    MenuEnum(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
