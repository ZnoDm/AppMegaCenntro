package com.sinfloo.demo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.models.Categoria;
import com.sinfloo.demo.models.Producto;
import com.sinfloo.demo.models.Trabajador;
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
	public boolean existsByNombreProducto(String nombreProducto) {
		return productoRepository.existsByNombreProducto(nombreProducto);
	} 
	public boolean existsById(Integer id) {
		return productoRepository.existsById(id);
	}
	public Producto get(Integer  id) {
        return productoRepository.findById(id).get();
    }
	public Optional<Producto> getByNombreProducto(String nombreProducto){
		return productoRepository.findByNombreProducto(nombreProducto);
	}
	public List<Producto> listarByNombreProducto(String term){
		return productoRepository.listarByNombreProducto(term);
	}

	public void eliminar(Integer id, Boolean activo, Boolean eliminado) {
		productoRepository.eliminar(id, activo,eliminado);
    }
	public void delete (Integer id) {
		Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {
        	productoRepository.delete(producto);
        }
	}
	
	
}
