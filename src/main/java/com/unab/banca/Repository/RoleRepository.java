package com.unab.banca.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unab.banca.Entity.ERole;
import com.unab.banca.Entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
  Optional<Role> findByNombre(ERole name);
}
