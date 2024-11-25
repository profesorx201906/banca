package com.unab.banca.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.unab.banca.Entity.Cliente;
import com.unab.banca.Entity.Cuenta;
import com.unab.banca.Repository.CuentaRepository;
import com.unab.banca.Utility.Security.Hash;
import com.unab.banca.Validation.Exception.InvalidDataException;
import com.unab.banca.Validation.Exception.NoAuthorizeException;
import com.unab.banca.Validation.Exception.NoFoundException;
import com.unab.banca.Validation.Entity.Error;;

@Service
public class CuentaService {
    @Autowired
    CuentaRepository cuentaRespository;

    @Autowired
    ClienteService clienteService;

    public Cuenta save(Cuenta cuenta) {
        String id = cuenta.getCliente().getIdCliente();
        clienteService.findById(id).orElseThrow(() -> new NoFoundException("Registro no existente",
        new Error("Crear", "No existe un cliente registro con el id " + id)));
        return cuentaRespository.save(cuenta);
    }

    public List<Cuenta> findAll() {
        return (List<Cuenta>) cuentaRespository.findAll();
    }

    public List<Cuenta> findByIdCliente(Cliente cliente) {
        return  cuentaRespository.findByCliente(cliente);
    }

    public Cuenta findById(String id) {
        return  cuentaRespository.findById(id).get();
    }

    public String deleteById(String id) {
        cuentaRespository.findById(id).orElseThrow(() -> new NoFoundException("Registro no existente",
                new Error("Eliminar", "No existe un registro con el id " + id)));
        cuentaRespository.deleteById(id);
        return "registro eliminado";
    }

    public void validarDatosCrearCuenta(String user, String key, BindingResult result) {
        clienteService.validarUsuarioAdmin(user, key);
        if (!Hash.sha1(clienteService.findByUserName(user).getPassword()+user).equals(key)) {
            throw new NoAuthorizeException("Acceso No Autorizado",
                    new Error("Campo nombre", "Acceso no Autorizado "));
        } 
        if (result.hasErrors()) {
            throw new InvalidDataException("Datos de entrada con errores", result);
        }

    }
}
