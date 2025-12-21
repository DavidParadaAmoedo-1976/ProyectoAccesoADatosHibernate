package marvel.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "Traje")

public class Traje {
    @Id
    @EqualsAndHashCode.Include
    private int id;

    @NonNull
    @Column( name = "Especificaciones")
    private String especificacion;

    @OneToOne( mappedBy = "id_personaje")
    private Personaje personaje;
}
