package com.sinfloo.demo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.models.Proveedor;
import com.sinfloo.demo.repositories.ProveedorRepository;


@Service
@Transactional
public class ProveedorService {
	@Autowired
	ProveedorRepository proveedorRepository;

	public void save(Proveedor proveedor) {
		proveedorRepository.save(proveedor);
	}
	
	public List<Proveedor> listarProveedores(){
		return proveedorRepository.findAll();
	}
	
	public boolean existsById(Integer id) {
		return proveedorRepository.existsById(id);
	}
	public Proveedor get(Integer  id) {
        return proveedorRepository.findById(id).get();
    }
	public void eliminar(Integer id, Boolean eliminado) {
		proveedorRepository.eliminar(id, eliminado);
    }
	
	
}
