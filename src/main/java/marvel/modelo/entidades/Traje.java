package marvel.modelo.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
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
