package com.unab.banca.Service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.unab.banca.Entity.ERole;
import com.unab.banca.Entity.Role;

import com.unab.banca.Repository.RoleRepository;

@Service
@Transactional
public class RolService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RoleRepository roleRepository;

    

    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }


    public Optional<Role> findByNombre(ERole valor) {
        return roleRepository.findByNombre(valor);
    }
  
    public Role save(Role role){
        return roleRepository.save(role);
    }

}
