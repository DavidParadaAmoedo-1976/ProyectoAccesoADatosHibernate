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
    @NonNull
    @EqualsAndHashCode.Include
    private int id;

    @NonNull
    private String especificacion;

    @OneToOne( mappedBy = "Id_Personaje")
    private Personaje personaje;
}
