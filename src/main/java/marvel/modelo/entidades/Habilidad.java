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
@Table(name = "Habilidad")
public class Habilidad {

    @Id
    @EqualsAndHashCode.Include
    private int id;

    @NonNull
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany(mappedBy = "habilidades")
    private Set<Personaje> personajes = new HashSet<>();

}