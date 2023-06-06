package com.sinfloo.demo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.models.Trabajador;
import com.sinfloo.demo.models.Usuario;
import com.sinfloo.demo.repositories.*;


@Service
@Transactional
public class TrabajadorService {
	@Autowired
	UsuarioRepository usuarioRepository;
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
	
	public Trabajador getByUsuario(Usuario usuario) {
        return trabajadorRepository.findByUsuario(usuario);
    }
	
	public void eliminar(Integer id, Boolean activo, Boolean eliminado) {
		trabajadorRepository.eliminar(id, activo,eliminado);
    }
	public void delete (Integer id) {
		Trabajador trabajador = trabajadorRepository.findById(id).orElse(null);
        if (trabajador != null) {
        	trabajadorRepository.delete(trabajador);
        }
	}
	
}
