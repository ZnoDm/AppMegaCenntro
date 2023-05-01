package com.sinfloo.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinfloo.demo.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	Optional<Usuario> findByNombreUsuario(String nombreUsuario);
	
	boolean existsByNombreUsuario(String nombreUsuario);
	
	default Optional<Usuario> findById(Integer id) {
		return Optional.empty();
	}
}
