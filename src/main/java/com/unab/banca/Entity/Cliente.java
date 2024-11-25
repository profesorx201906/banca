package com.unab.banca.Entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clientes")
@Entity
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(length = 50)
  private String idCliente;

  @Size(min = 4, message = "Debe tener mínimo 5 caracteres")
  @Column(length = 20)
  private String nombre;

  @Size(min = 4, message = "Debe tener mínimo 5 caracteres")
  @Column(length = 20)
  private String apellido;

  @Size(min = 5, message = "Debe tener mínimo 5 caracteres")
  @Column(length = 20, unique = true)
  private String userName;

  @NotEmpty(message = "El campo clave no debe ser vacio")
  @Column(length = 50)
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public Cliente(String idCliente) {
    this.idCliente = idCliente;
  }

  @Override
  public String toString() {
    return "Cliente [idCliente=" + idCliente + ", nombre=" + nombre + ", apellido=" + apellido + ", userName="
        + userName + ", password=" + password + ", roles=" + roles + "]";
  }

}
