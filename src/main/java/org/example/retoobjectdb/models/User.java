package org.example.retoobjectdb.models;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;

    @Column(name="is_admin")
    private Boolean isAdmin;

    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "user", fetch = FetchType.EAGER)
    private List<Copy> copies = new ArrayList<>();

    public void addCopy(Copy c) {
        c.setUser(this);
        this.copies.add(c);
    }
}
