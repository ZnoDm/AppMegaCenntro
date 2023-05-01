package com.sinfloo.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinfloo.demo.enums.RolNombre;
import com.sinfloo.demo.models.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{
	
	Optional<Rol> findByNombreRol(RolNombre nombreRol);
	
	boolean existsByNombreRol(RolNombre nombreRol);
	
}
