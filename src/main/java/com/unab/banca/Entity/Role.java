package com.unab.banca.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 50)
    private String idTipo;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole nombre;

    public Role(ERole nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Role [idTipo=" + idTipo + ", nombre=" + nombre + "]";
    }

    

}
