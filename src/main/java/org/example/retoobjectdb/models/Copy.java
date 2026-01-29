package org.example.retoobjectdb.models;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "copies")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Copy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private String estado;
    private String soporte;

    @Override
    public String toString() {
        return "Copy{" +
                "id=" + id +
                ", film=" + film +
                ", user=" + user +
                ", estado='" + estado + '\'' +
                ", soporte='" + soporte + '\'' +
                '}';
    }
}
