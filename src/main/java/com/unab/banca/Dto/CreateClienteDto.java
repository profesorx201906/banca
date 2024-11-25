package com.unab.banca.Dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClienteDto {
    private String idCliente;
        
    private String nombre;
    @Size(min = 5, message = "Debe tener mínimo 5 caracteres")
    private String apellido;
    @Size(min = 5, message = "Debe tener mínimo 5 caracteres")
    private String userName;
    @NotBlank(message = "No puede ser vacio")
    private String password;
    private Set<String> role;

}
