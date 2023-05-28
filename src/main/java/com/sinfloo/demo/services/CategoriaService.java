package com.sinfloo.demo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.models.Categoria;
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
	
	public boolean existsById(Integer id) {
		return categoriaRepository.existsById(id);
	}
	public Categoria get(Integer  id) {
        return categoriaRepository.findById(id).get();
    }
	public void eliminar(Integer id, Boolean eliminado) {
		categoriaRepository.eliminar(id, eliminado);
    }
	
	
}
