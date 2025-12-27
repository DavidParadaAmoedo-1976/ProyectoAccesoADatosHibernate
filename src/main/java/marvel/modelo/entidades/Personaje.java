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
@Table(name = "Personaje")
public class Personaje {
    @Id
    @EqualsAndHashCode.Include
    private int id;

    @NonNull
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "alias")
    private String alias;

    @OneToOne(optional = true)
    @JoinColumn(name = "id_traje", unique = true, nullable = true)
    private Traje traje;

    @ManyToMany
    @JoinTable(name = "Personaje_Habilidad",
            joinColumns = @JoinColumn(name = "id_Personaje"),
            inverseJoinColumns = @JoinColumn(name = "id_Habilidad")
    )
    private Set<Habilidad> habilidades = new HashSet<>();

    @OneToMany(mappedBy = "personaje")
    private Set<Participa> participaciones = new HashSet<>();
}
