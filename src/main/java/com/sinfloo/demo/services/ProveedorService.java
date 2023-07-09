package com.sinfloo.demo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.models.Cliente;
import com.sinfloo.demo.models.Producto;
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
	
	public Optional<Proveedor> getByDocumentoIdentidad(String documentoIdentidad){
		return proveedorRepository.findByDocumentoIdentidad(documentoIdentidad);
	}
	
	
	public boolean existsByDocumentoIdentidad(String documentoIdentidad) {
		return proveedorRepository.existsByDocumentoIdentidad(documentoIdentidad);
	}
	
	public Proveedor get(Integer  id) {
        return proveedorRepository.findById(id).get();
    }
	public void eliminar(Integer id, Boolean activo, Boolean eliminado) {
		proveedorRepository.eliminar(id, activo,eliminado);
    }
	public void delete (Integer id) {
		Proveedor proveedor = proveedorRepository.findById(id).orElse(null);
        if (proveedor != null) {
        	proveedorRepository.delete(proveedor);
        }
	}
	public List<Proveedor> listarByDocumentoIdentidad(String term){
		return proveedorRepository.listarByDocumentoIdentidad(term);
	}
	
}
