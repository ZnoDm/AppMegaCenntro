package com.sinfloo.demo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.models.Permiso;
import com.sinfloo.demo.models.Rol;
import com.sinfloo.demo.repositories.PermisoRepository;
import com.sinfloo.demo.repositories.RolRepository;

@Service
@Transactional
public class RolService {

	@Autowired
	RolRepository rolRepository;
	
	@Autowired
	PermisoRepository permisoRepository;

	public void save(Rol rol) {
		rolRepository.save(rol);
	}
	
	public List<Rol> listarRoles(){
		return rolRepository.findAll();
	}
	
	public boolean existsById(int id) {
		return rolRepository.existsById(id);
	}
	public Optional<Rol> getByNombreRol(String nombreRol){
		return rolRepository.findByNombreRol(nombreRol);
	}
	public Rol get(Integer  id) {
        return rolRepository.findById(id).get();
    }
	public boolean existsByNombreRol(String nombreRol) {
		return rolRepository.existsByNombreRol(nombreRol);
	} 
	public void delete (Integer id) {
		 Rol rol = rolRepository.findById(id).orElse(null);
	        if (rol != null) {

	        	// Eliminar las asociaciones en la tabla intermedia
	        	rol.getPermisos().clear();

	        	// Guardar el objeto Rol actualizado para eliminar las asociaciones en la tabla intermedia
	        	rolRepository.save(rol);

	        	// Eliminar el objeto Rol
	        	rolRepository.delete(rol);
	        }
	}
	
}
