package com.unab.banca.Repository;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unab.banca.Entity.Cuenta;


@Repository
public interface CuentaRepository extends CrudRepository<Cuenta,String> {
    public Optional<Cuenta> findById(String id);
    public List<Cuenta> findByCliente(com.unab.banca.Entity.Cliente cliente);

    @Transactional(readOnly=false)
    @Modifying
    @Query(value="UPDATE cuentas SET saldo_cuenta=saldo_cuenta + :saldo WHERE id_cuenta like :idcta", nativeQuery=true)
    public void deposito(@Param("idcta") String idcta,@Param("saldo") Double saldo); 

    @Transactional(readOnly=false)
    @Modifying
    @Query(value="UPDATE cuentas SET saldo_cuenta=saldo_cuenta - :saldo WHERE id_cuenta like :idcta", nativeQuery=true)
    public void retiro(@Param("idcta") String idcta,@Param("saldo") Double saldo); 

 
}
