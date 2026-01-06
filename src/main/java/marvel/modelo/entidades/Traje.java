package marvel.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Traje")

public class Traje {
    @Id
    private int id;

    @NonNull
    @Column(name = "especificacion")
    private String especificacion;

    @OneToOne(mappedBy = "traje")
    private Personaje personaje;
}
