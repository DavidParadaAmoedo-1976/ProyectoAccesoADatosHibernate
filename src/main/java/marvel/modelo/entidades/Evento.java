package marvel.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Evento")

public class Evento {

    @Id
    @EqualsAndHashCode.Include
    private int id;

    @NonNull
    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Lugar")
    private String lugar;

    @OneToMany( mappedBy = "evento")
    private Set<Participa> participaciones = new HashSet<>();
}
