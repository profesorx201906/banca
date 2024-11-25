package com.unab.banca.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unab.banca.Entity.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente,String> {
    public Optional<Cliente> findById(String id);
    public Cliente findByNombre(String valor);
    public Cliente findByUserName(String valor);
    public List<Cliente> findByNombreContaining(String valor);
    
    @Transactional(readOnly = true)
    @Query(value = "select id, nombre, user_name from clientes where user_name like %:nombre%\r\n" + //
            "",nativeQuery = true)
    public List<Cliente> findByNombrePartialManual(@Param("nombre") String nombre);

    @Transactional(readOnly = true)
    @Query(value = "select count(*) from clientes where user_name=:nombre and password=:clave",nativeQuery = true)
    public Integer logIn(@Param("nombre") String nombre,@Param("clave") String clave);
    
}
