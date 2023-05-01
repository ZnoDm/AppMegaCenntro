package com.sinfloo.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinfloo.demo.enums.PermisoNombre;
import com.sinfloo.demo.models.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer>{
	
	Optional<Permiso> findByNombrePermiso(PermisoNombre nombrePermiso);
	
	boolean existsByNombrePermiso(PermisoNombre nombrePermiso);
	
}
