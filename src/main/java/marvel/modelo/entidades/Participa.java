package marvel.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Participa")
@IdClass(ParticipaPK.class)
public class Participa {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_personaje")
    private Personaje personaje;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Evento evento;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "rol")
    private String rol;

}
