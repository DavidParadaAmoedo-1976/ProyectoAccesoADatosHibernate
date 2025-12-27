package marvel.modelo.enums;

public enum ModificarHabilidadEnum {
    SALIR("Volver al menú anterior."),

    NOMBRE("Modificar nombre."),
    DESCRIPCION("Modificar descripción.");

    private final String TEXTO;

    ModificarHabilidadEnum(String TEXTO) {
        this.TEXTO = TEXTO;
    }

    public String getTexto() {
        return TEXTO;
    }
}
