package com.sinfloo.demo.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.repositories.UsuarioRepository;
import com.sinfloo.demo.models.Rol;
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
	
	public void delete (Integer id) {
		 Usuario usuario = usuarioRepository.findById(id).orElse(null);
	        if (usuario != null) {

	        	// Eliminar las asociaciones en la tabla intermedia
	        	usuario.getRoles().clear();

	        	// Guardar el objeto Rol actualizado para eliminar las asociaciones en la tabla intermedia
	        	usuarioRepository.save(usuario);

	        	// Eliminar el objeto Rol
	        	usuarioRepository.delete(usuario);
	        }
	}
	public boolean existsById(int id) {
		return usuarioRepository.existsById(id);
	}
	public Usuario get(Integer  id) {
        return usuarioRepository.findById(id).get();
    }

	public boolean existsByNombreUsuario(String nombreUsuario) {
		return usuarioRepository.existsByNombreUsuario(nombreUsuario);
	}
	
	
}
