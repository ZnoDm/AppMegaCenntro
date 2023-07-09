package com.sinfloo.demo.services;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public List<Permiso> listarPermisos(){
		return permisoRepository.findAll();
	}
	
	public boolean existsById(int id) {
		return permisoRepository.existsById(id);
	}
	public Optional<Permiso> getByNombrePermiso(String nombrePermiso) {
	    return permisoRepository.findByNombrePermiso(nombrePermiso);
	}
	
	public boolean existsByNombrePermiso(String nombrePermiso) {
		return permisoRepository.existsByNombrePermiso(nombrePermiso);
	} 
	public Permiso get(Integer  id) {
        return permisoRepository.findById(id).get();
    }
	public void delete (Integer id) {
		 Permiso permiso = permisoRepository.findById(id).orElse(null);
	        if (permiso != null) {

	        	// Eliminar las asociaciones en la tabla intermedia
	        	permiso.getRoles().clear();

	        	// Guardar el objeto Rol actualizado para eliminar las asociaciones en la tabla intermedia
	        	permisoRepository.save(permiso);

	        	// Eliminar el objeto Rol
	        	permisoRepository.delete(permiso);
	        }
	}
	
}
