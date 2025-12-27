package marvel.modelo.entidades;

import java.io.Serializable;
import java.util.Objects;

public class ParticipaPK implements Serializable {

    private int evento;
    private int personaje;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipaPK clave)) return false;
        return personaje == clave.personaje && evento == clave.evento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personaje, evento);

    }
}
