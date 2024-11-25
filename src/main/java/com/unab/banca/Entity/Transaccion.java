package com.unab.banca.Entity;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="transaccion")
public class Transaccion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idTransaccion")
    private int id;
    //@NotEmpty(message = "EL campo Fecha transacción no debe ser vacío")
    @Column(name="fecha_transaccion")
    private Date fechaTransaccion;
    //@NotEmpty(message = "EL campo valor transacción no debe ser vacío")
    @Column(name="valor_transaccion")
    private double valorTransaccion;
    //@NotEmpty(message = "EL campo Tipo transacción no debe ser vacío")
    @Column(name="tipo_transaccion")
    private String tipoTransaccion;
    //@NotEmpty(message = "EL campo identificador cuenta que realizó la transacción no debe ser vacío")
    @ManyToOne
    @JoinColumn(name="idCuenta")
    private Cuenta cuenta;

    @Override
    public String toString() {
        return "Transaccion [id_transaccion=" + id + ", fecha_transaccion=" + fechaTransaccion
                + ", valor_transaccion=" + valorTransaccion + ", tipo_transaccion=" + tipoTransaccion + ", cuenta="
                + cuenta + "]";
    }
}
