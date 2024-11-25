package com.unab.banca.Dto;

import com.unab.banca.Entity.ERole;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RoleDto {

    private ERole nombre;

    public RoleDto(ERole nombre) {
        this.nombre = nombre;
    }

}
