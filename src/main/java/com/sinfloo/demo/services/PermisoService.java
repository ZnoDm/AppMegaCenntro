package com.sinfloo.demo.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.enums.PermisoNombre;
import com.sinfloo.demo.models.Permiso;
import com.sinfloo.demo.repositories.PermisoRepository;

@Service
@Transactional
public class PermisoService {

	@Autowired
	PermisoRepository permisoRepository;

	public void save(Permiso permiso) {
		permisoRepository.save(permiso);
	}
	
	public boolean existsById(int id) {
		return permisoRepository.existsById(id);
	}
	public Optional<Permiso> getByNombrePermiso(PermisoNombre nombrePermiso){
		return permisoRepository.findByNombrePermiso(nombrePermiso);
	}
	
	public boolean existsByNombrePermiso(PermisoNombre nombrePermiso) {
		return permisoRepository.existsByNombrePermiso(nombrePermiso);
	} 

	
}
