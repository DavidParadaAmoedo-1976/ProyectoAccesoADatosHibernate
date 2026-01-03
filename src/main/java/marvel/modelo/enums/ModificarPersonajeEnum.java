package marvel.modelo.enums;

public enum ModificarPersonajeEnum {
    SALIR("Volver al menú anterior."),

    NOMBRE("Modificar nombre."),
    ALIAS("Modificar alias."),
    TRAJE("Modificar traje.");

    private final String TEXTO;

    ModificarPersonajeEnum(String TEXTO) {
        this.TEXTO = TEXTO;
    }

    public String getTexto() {
        return TEXTO;
    }
}
