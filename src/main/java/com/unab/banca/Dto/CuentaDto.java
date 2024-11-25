package com.unab.banca.Dto;

import java.util.Date;

import lombok.Data;

@Data
public class CuentaDto {
    private String id;
    private Date fechaCreaccion;
    private Double saldo;
    private String idCliente;    
    private String nombre;
    private String apellido;
}
