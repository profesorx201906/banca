package com.unab.banca.Controller;

import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unab.banca.Dto.CuentaDto;
import com.unab.banca.Entity.Cliente;
import com.unab.banca.Entity.Cuenta;
import com.unab.banca.Service.ClienteService;
import com.unab.banca.Service.CuentaService;
import com.unab.banca.Utility.ConvertEntity;
import com.unab.banca.Utility.Entity.Message;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/cuenta")
@CrossOrigin(origins = "*")
public class CuentaController {

    @Autowired
    CuentaService cuentaService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    ConvertEntity convertEntity;

    CuentaDto cuentaDto = new CuentaDto();

    @PostMapping("/create")
    public ResponseEntity<Object> save(@Valid @RequestBody Cuenta cuenta, BindingResult result, @RequestHeader String user,
            @RequestHeader String key) {
                System.out.println(cuenta+"---");
        cuentaService.validarDatosCrearCuenta(user, key, result);
        return new ResponseEntity<>((CuentaDto) convertEntity.convert(cuentaService.save(cuenta), cuentaDto),HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public Object update(@Valid @RequestBody Cuenta cuenta, BindingResult result, @PathVariable("id") String id,
            @RequestHeader String user, @RequestHeader String key) {
        cuentaService.validarDatosCrearCuenta(user, key, result);
        cuenta.setId(id);
        return convertEntity.convert(cuentaService.save(cuenta), cuentaDto);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Object>> findAll(@RequestHeader String user, @RequestHeader String key) {
        clienteService.validarUsuarioAdmin(user, key);
        List<Object> cuentaDtoLista = new ArrayList<>();
        for (Cuenta cuenta : cuentaService.findAll()) {
            cuentaDtoLista.add(convertEntity.convert(cuenta, cuentaDto));
        }
        return new ResponseEntity<List<Object>>(cuentaDtoLista, HttpStatus.OK);
    }

    @GetMapping("/list/cliente/{id}")
    public ResponseEntity<List<Object>> findByCliente(@RequestHeader String user, @RequestHeader String key,@PathVariable("id") String id) {
        clienteService.validarUsuario(user, key,id);
        Cliente cliente = new Cliente(id);
        List<Object> cuentaDtoLista = new ArrayList<>();
        for (Cuenta cuenta : cuentaService.findByIdCliente(cliente)) {
            cuentaDtoLista.add(convertEntity.convert(cuenta, cuentaDto));
        }
        return new ResponseEntity<List<Object>>(cuentaDtoLista, HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Object> findById(@RequestHeader String user, @RequestHeader String key,@PathVariable("id") String id) {
        clienteService.validarUsuario(user, key,id);
        return new ResponseEntity<Object>((CuentaDto) convertEntity.convert(cuentaService.findById(id), cuentaDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteById(@PathVariable("id") String id, @RequestHeader String user,
            @RequestHeader String key) {
        clienteService.validarUsuarioAdmin(user, key);
        return new ResponseEntity<>(new Message(200, cuentaService.deleteById(id)), HttpStatus.OK);
    }

}
