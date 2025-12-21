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
@Table( name = "Personaje")
public class Personaje {
    @Id
    @EqualsAndHashCode.Include
    private int id;

    @NonNull
    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Alias")
    private String alias;

    @OneToOne
    @JoinColumn( name = "Id_Traje")
    private Traje traje;

    @ManyToMany
    @JoinTable( name = "Personaje_Habilidad",
            joinColumns = @JoinColumn(name = "id_Personaje"),
            inverseJoinColumns = @JoinColumn( name = "id_Habilidad")
    )
    private Set<Habilidad> habilidades = new HashSet<>();

    @OneToMany(mappedBy = "personaje")
    private Set<Participa> participaciones = new HashSet<>();

}
