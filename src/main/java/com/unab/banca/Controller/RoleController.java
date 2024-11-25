package com.unab.banca.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unab.banca.Entity.Role;
import com.unab.banca.Repository.RoleRepository;

@RestController
@RequestMapping("/api/v1/rol")
public class RoleController {
    @Autowired
    RoleRepository roleRepository;
    
    @GetMapping
    public List<Role> findAll(){
        return roleRepository.findAll();
    }
}
