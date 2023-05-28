package com.sinfloo.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sinfloo.demo.models.Categoria;
import com.sinfloo.demo.models.Permiso;
import com.sinfloo.demo.models.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{
	
	Optional<Rol> findByNombreRol(String nombreRol);
	
	boolean existsByNombreRol(String nombreRol);
	// Método para guardar una Categoria en la base de datos
	
	
}
