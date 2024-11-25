package com.unab.banca.Entity;

import java.util.Date;
import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cuentas")
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "idCuenta")
    private String id;
    @Column(name = "fechaApertura")
    private Date fechaApertura;
    @Column(name = "saldoCuenta")
    private double saldoCuenta;
    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;
    @Override
    public String toString() {
        return "Cuenta [id_cuenta=" + id + ", fecha_apertura=" + fechaApertura + ", saldo_cuenta="
                + saldoCuenta + ", cliente=" + cliente + "]";
    }
    public Cuenta(String id) {
        this.id = id;
    }

    

}
