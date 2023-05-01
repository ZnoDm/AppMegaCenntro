package com.sinfloo.demo.secutiry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sinfloo.demo.models.Usuario;
import com.sinfloo.demo.services.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.getByNombreUsuario(nombreUsuario).
        					orElseThrow(()-> new UsernameNotFoundException(nombreUsuario));
        return UsuarioPrincipal.build(usuario);
    }
}