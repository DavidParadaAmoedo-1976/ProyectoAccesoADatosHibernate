package marvel.modelo.entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table( name = "Personaje")
public class Personaje {
    @Id
    @NonNull
    @EqualsAndHashCode.Include
    private int id;

    @NonNull
    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @NonNull
    @Column(name = "Alias", nullable = false)
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

}
