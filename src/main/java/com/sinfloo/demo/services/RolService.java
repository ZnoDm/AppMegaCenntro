package com.sinfloo.demo.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.enums.RolNombre;
import com.sinfloo.demo.models.Rol;
import com.sinfloo.demo.repositories.RolRepository;

@Service
@Transactional
public class RolService {

	@Autowired
	RolRepository rolRepository;

	public void save(Rol rol) {
		rolRepository.save(rol);
	}
	
	public boolean existsById(int id) {
		return rolRepository.existsById(id);
	}
	public Optional<Rol> getByNombreRol(RolNombre nombreRol){
		return rolRepository.findByNombreRol(nombreRol);
	}
	
	public boolean existsByNombreRol(RolNombre nombreRol) {
		return rolRepository.existsByNombreRol(nombreRol);
	} 

	
}
