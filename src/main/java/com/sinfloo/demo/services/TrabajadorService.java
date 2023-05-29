package com.sinfloo.demo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.models.Trabajador;
import com.sinfloo.demo.repositories.TrabajadorRepository;


@Service
@Transactional
public class TrabajadorService {
	@Autowired
	TrabajadorRepository trabajadorRepository;

	public void save(Trabajador trabajador) {
		trabajadorRepository.save(trabajador);
	}
	
	public List<Trabajador> listarTrabajadors(){
		return trabajadorRepository.findAll();
	}
	
	public boolean existsById(Integer id) {
		return trabajadorRepository.existsById(id);
	}
	public Trabajador get(Integer  id) {
        return trabajadorRepository.findById(id).get();
    }
	public void eliminar(Integer id, Boolean eliminado) {
		trabajadorRepository.eliminar(id, eliminado);
    }
	
	
}
