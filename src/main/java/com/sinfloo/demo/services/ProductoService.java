package com.sinfloo.demo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.models.Producto;
import com.sinfloo.demo.repositories.ProductoRepostory;

@Service
@Transactional
public class ProductoService {
	@Autowired
	ProductoRepostory productoRepository;

	public void save(Producto producto) {
		productoRepository.save(producto);
	}
	
	public List<Producto> listarProductos(){
		return productoRepository.findAll();
	}
	
	public boolean existsById(Integer id) {
		return productoRepository.existsById(id);
	}
	public Producto get(Integer  id) {
        return productoRepository.findById(id).get();
    }
	public void eliminar(Integer id, Boolean eliminado) {
		productoRepository.eliminar(id, eliminado);
    }
}
