package com.sinfloo.demo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.models.Categoria;
import com.sinfloo.demo.models.Rol;
import com.sinfloo.demo.models.Trabajador;
import com.sinfloo.demo.repositories.CategoriaRepository;


@Service
@Transactional
public class CategoriaService {
	@Autowired
	CategoriaRepository categoriaRepository;

	public void save(Categoria categoria) {
		categoriaRepository.save(categoria);
	}
	
	public List<Categoria> listarCategorias(){
		return categoriaRepository.findAll();
	}
	public Optional<Categoria> getByNombreCategoria(String nombreCategoria){
		return categoriaRepository.findByNombreCategoria(nombreCategoria);
	}
	
	public boolean existsById(Integer id) {
		return categoriaRepository.existsById(id);
	}
	
	public boolean existsByNombreCategoria(String nombreCategoria) {
		return categoriaRepository.existsByNombreCategoria(nombreCategoria);
	} 
	public Categoria get(Integer  id) {
        return categoriaRepository.findById(id).get();
    }
	public void eliminar(Integer id, Boolean activo, Boolean eliminado) {
		categoriaRepository.eliminar(id, activo,eliminado);
    }
	public void delete (Integer id) {
		Categoria categoria = categoriaRepository.findById(id).orElse(null);
        if (categoria != null) {
        	categoriaRepository.delete(categoria);
        }
	}
	
	
}
