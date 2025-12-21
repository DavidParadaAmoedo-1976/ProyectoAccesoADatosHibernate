package marvel.modelo.enums;

public enum EstilosEnum {

    RESET("\u001B[0m"),

    // Colores basicos
    NEGRO("\u001B[30m"),
    ROJO("\u001B[31m"),
    VERDE("\u001B[32m"),
    AMARILLO("\u001B[33m"),
    AZUL("\u001B[34m"),
    MORADO("\u001B[35m"),
    CYAN("\u001B[36m"),
    BLANCO("\u001B[37m"),

    // Colores Extendidos (Rosa, Naranja y otros tonos)
    ROSA("\u001B[38;5;206m"),
    NARANJA("\u001B[38;5;208m"),
    ORO("\u001B[38;5;220m"),
    GRIS("\u001B[38;5;244m"),
    SALMON("\u001B[38;5;211m"),
    TURQUESA("\u001B[38;5;45m"),
    LIMA("\u001B[38;5;118m"),
    CAFE("\u001B[38;5;94m"),

    // Especiales
    NEGRITA("\u001B[1m"),
    SUBRAYADO("\u001B[4m"),

    // Colores de Fondo
    FONDO_NEGRO("\u001B[40m"),
    FONDO_ROJO("\u001B[41m"),
    FONDO_VERDE("\u001B[42m"),
    FONDO_AMARILLO("\u001B[43m"),
    FONDO_AZUL("\u001B[44m"),
    FONDO_MORADO("\u001B[45m"),
    FONDO_CIAN("\u001B[46m"),
    FONDO_BLANCO("\u001B[47m");

    private final String formato;

    EstilosEnum(String unicode) {
        this.formato = unicode;
    }

    public String getFormato() {
        return formato;
    }
}

