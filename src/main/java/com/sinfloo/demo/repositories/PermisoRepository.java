package com.sinfloo.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sinfloo.demo.models.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer>{
	
	
	boolean existsByNombrePermiso(String nombrePermiso);
	
	Optional<Permiso> findByNombrePermiso(String nombrePermiso);
}
