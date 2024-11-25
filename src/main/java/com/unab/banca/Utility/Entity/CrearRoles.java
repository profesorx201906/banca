package com.unab.banca.Utility.Entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.unab.banca.Entity.Cliente;
import com.unab.banca.Entity.ERole;
import com.unab.banca.Entity.Role;
import com.unab.banca.Repository.RoleRepository;
import com.unab.banca.Service.ClienteService;
import com.unab.banca.Service.RolService;
import com.unab.banca.Utility.Security.Hash;

@Component
public class CrearRoles implements CommandLineRunner {
    @Autowired
    RolService rolService;

    @Autowired
    ClienteService clienteService;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (rolService.findAll().size() == 0) {
            Role rolAdmin = new Role(ERole.ROLE_ADMIN);
            Role rolUser = new Role(ERole.ROLE_USER);
            Role rolModerator = new Role(ERole.ROLE_MODERATOR);
            Role rolCahser = new Role(ERole.ROLE_CASHER);
            rolService.save(rolAdmin);
            rolService.save(rolUser);
            rolService.save(rolModerator);
            rolService.save(rolCahser);
        }
        System.out.println(Hash.sha1("123456"));
        if (clienteService.findAll().size() == 0) {
            Role userRole = roleRepository.findByNombre(ERole.ROLE_ADMIN).get();
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            Cliente cliente = new Cliente();
            cliente.setNombre("admin");
            cliente.setApellido("admin");
            cliente.setUserName("admin");
            cliente.setPassword(Hash.sha1("123456"));
            cliente.setRoles(roles);
            clienteService.save(cliente);
        }

    }

}
