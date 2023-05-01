package com.sinfloo.demo.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.repositories.UsuarioRepository;
import com.sinfloo.demo.models.Usuario;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public List<Usuario> listarUsuarios(){
		return usuarioRepository.findAll();
	}
	
	public Optional<Usuario> getByIdUsuario(int id){
		return usuarioRepository.findById(id);
	}
	
	
	public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
		return usuarioRepository.findByNombreUsuario(nombreUsuario);
	}
	
	public void save(Usuario usuario) {
		usuarioRepository.save(usuario);
	}
	
	public boolean existsById(int id) {
		return usuarioRepository.existsById(id);
	}


	public boolean existsByNombreUsuario(String nombreUsuario) {
		return usuarioRepository.existsByNombreUsuario(nombreUsuario);
	}
}
