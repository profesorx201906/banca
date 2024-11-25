package com.unab.banca.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.unab.banca.Dto.CreateClienteDto;
import com.unab.banca.Entity.Cliente;
import com.unab.banca.Entity.ERole;
import com.unab.banca.Entity.Role;
import com.unab.banca.Repository.ClienteRepository;
import com.unab.banca.Repository.RoleRepository;
import com.unab.banca.Utility.ConvertEntity;
import com.unab.banca.Utility.Security.Hash;
import com.unab.banca.Validation.Exception.InvalidDataException;
import com.unab.banca.Validation.Exception.NoAuthorizeException;
import com.unab.banca.Validation.Exception.NoFoundException;
import com.unab.banca.Validation.Exception.UniqueException;
import com.unab.banca.Validation.Entity.Error;

@Service
public class ClienteService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ConvertEntity convertEntity;

    Cliente cliente = new Cliente();

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> findAll() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    public String deleteById(String id) {

        clienteRepository.findById(id).orElseThrow(() -> new NoFoundException("Registro no existente",
                new Error("Eliminar", "No existe un registro con el id " + id)));

        clienteRepository.deleteById(id);
        return "registro eliminado";

    }

    public Cliente findByNombre(String valor) {
        return clienteRepository.findByNombre(valor);
    }

    public Cliente findByUserName(String valor) {
        return clienteRepository.findByUserName(valor);
    }

    public List<Cliente> findByNombreContaining(String valor) {
        return clienteRepository.findByNombreContaining(valor);
    }

    public Optional<Cliente> findById(String valor) {
        return clienteRepository.findById(valor);
    }

    public Integer logIn(String nombre, String clave) {
        return clienteRepository.logIn(nombre, clave);
    }

    public Cliente validarDatosCrearCliente(String user, String key, String nombre, BindingResult result,
            CreateClienteDto createClienteDto) {
        if (!Hash.sha1(clienteRepository.findByUserName(user).getPassword()+user).equals(key)) {
            throw new NoAuthorizeException("Acceso No Autorizado",
                    new Error("Campo nombre", "Acceso no Autorizado "));
        } else {
            int cantidad = 0;
            for (Role role : clienteRepository.findByUserName(user).getRoles()) {
                if (role.getNombre().toString().equals("ROLE_ADMIN"))
                    cantidad++;
            }
           
            if (cantidad == 0) {
                throw new NoAuthorizeException("Acceso No Autorizado",
                        new Error("Rol", "No tiene permiso de administrador para crear usuarios "));
            } else {
                asignarRolesUsuario(createClienteDto);
            }
        }
        if (result.hasErrors()) {
            throw new InvalidDataException("Datos de entrada con errores", result);
        }
        if (clienteRepository.findByUserName(nombre) != null) {

            throw new UniqueException("Datos de entrada con errores",
                    new Error("Credenciales", "Ya existe un registro con el nombre " + nombre));
        }
        cliente.setIdCliente(null);
        return cliente;
    }

    public Cliente validarDatosModificarCliente(String user, String key, BindingResult result,
            CreateClienteDto createClienteDto) {
        Set<Role> roles = new HashSet<>();
        if (!Hash.sha1(clienteRepository.findByUserName(user).getPassword()+user).equals(key)) {
            throw new NoAuthorizeException("Acceso No Autorizado",
                    new Error("Campo nombre", "Acceso no Autorizado "));
        } else {
            int cantidad = 0;
            for (Role role : clienteRepository.findByUserName(user).getRoles()) {
                if (role.getNombre().toString().equals("ROLE_ADMIN"))
                    cantidad++;
            }
            if (cantidad == 0) {
                Role userRole = roleRepository.findByNombre(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
                roles.add(userRole);
            } else {
                asignarRolesUsuario(createClienteDto);

            }
        }

        if (result.hasErrors()) {
            throw new InvalidDataException("Datos de entrada con errores", result);
        }

        return cliente;
    }

    public void asignarRolesUsuario(CreateClienteDto createCliente) {
        Set<String> strRoles = createCliente.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByNombre(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByNombre(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByNombre(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
                        roles.add(modRole);
                        break;
                    case "cash":
                        Role cashRole = roleRepository.findByNombre(ERole.ROLE_CASHER)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
                        roles.add(cashRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByNombre(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
                        roles.add(userRole);
                }
            });
        }
        cliente = (Cliente) convertEntity.convert(createCliente, cliente);
        cliente.setRoles(roles);
    }

    public void validarUsuarioAdmin(String user, String key) {
        if (!Hash.sha1(clienteRepository.findByUserName(user).getPassword()+user).equals(key)) {
            throw new NoAuthorizeException("Acceso No Autorizado", new Error("Campo nombre", "Acceso no Autorizado "));
        } else {
            int cantidad = 0;
            for (Role role : clienteRepository.findByUserName(user).getRoles()) {
                if (role.getNombre().toString().equals("ROLE_ADMIN"))
                    cantidad++;
            }
            System.out.println(user+"----");
            if (cantidad == 0) {
                throw new NoAuthorizeException("Acceso No Autorizado",
                        new Error("Tipo de usuario", "Acceso no Autorizado para tipo de usuario"));
            }
        }

    }

    public void validarUsuario(String user, String key, String id) {
        if (!Hash.sha1(clienteRepository.findByUserName(user).getPassword()+user).equals(key)) {
            throw new NoAuthorizeException("Acceso No Autorizado", new Error("Campo nombre", "Acceso no Autorizado "));
        } else {
            int cantidad = 0;
            for (Role role : clienteRepository.findByUserName(user).getRoles()) {
                if (role.getNombre().toString().equals("ROLE_ADMIN"))
                    cantidad++;
            }
            if (cantidad == 0) {

                if (!clienteRepository.findByUserName(user).getIdCliente().equals(id)) {
                    throw new NoAuthorizeException("Acceso No Autorizado",
                            new Error("Tipo de usuario", "Acceso no Autorizado para tipo de usuario"));
                }

            }
        }

    }
}
