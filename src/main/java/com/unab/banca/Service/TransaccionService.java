package com.unab.banca.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional;

import com.unab.banca.Entity.Cuenta;
import com.unab.banca.Entity.Transaccion;
import com.unab.banca.Repository.TransaccionRepository;
@Service
public class TransaccionService {
    @Autowired
    private TransaccionRepository transaccionRepository;

    @Transactional(readOnly=false)
    public Transaccion save(Transaccion transaccion) {
        return transaccionRepository.save(transaccion);
    }

    @Transactional(readOnly=false)
    public void delete(Integer id) {
        transaccionRepository.deleteById(id);;
    }

    @Transactional(readOnly=true)
    public Transaccion findById(Integer id) {
        return transaccionRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly=true)
    public List<Transaccion> findByAll() {
        return (List<Transaccion>) transaccionRepository.findAll();
    }

    @Transactional(readOnly=true)
    public List<Transaccion> findByCuenta(String idcta) {
        return (List<Transaccion>) transaccionRepository.findByCuenta(new Cuenta(idcta));
    }

    @Transactional(readOnly=false)
    public void createTransation(String idcta, Double valor_transaccion, String tipo) {
        transaccionRepository.createTransation(idcta, valor_transaccion, tipo);
    }

  

}
