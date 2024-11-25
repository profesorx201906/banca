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
import com.unab.banca.Entity.Transaccion;


@Repository
public interface TransaccionRepository extends CrudRepository<Transaccion,Integer> {
    public Optional<Transaccion> findById(Integer id);
    public List<Transaccion> findByCuenta(Cuenta cuenta);

    @Transactional(readOnly=false)
    @Modifying
    @Query(value="INSERT INTO transaccion(fecha_transaccion,valor_transaccion,tipo_transaccion,id_cuenta) VALUES (current_date(), :valor_transaccion, :tipo, :idcta)", nativeQuery=true)
    public void createTransation(@Param("idcta") String idcta,@Param("valor_transaccion") Double valor_transaccion,@Param("tipo") String tipo);

    
}
