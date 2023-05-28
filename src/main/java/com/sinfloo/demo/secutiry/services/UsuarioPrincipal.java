package com.sinfloo.demo.secutiry.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sinfloo.demo.models.Permiso;
import com.sinfloo.demo.models.Rol;
import com.sinfloo.demo.models.Usuario;
import com.sinfloo.demo.repositories.UsuarioRepository;
import com.sinfloo.demo.services.UsuarioService;

public class UsuarioPrincipal implements UserDetails {
	@Autowired
    UsuarioService usuarioService;
	
	private int id;
    private String nombreUsuario;
    private String password;
    
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioPrincipal(int id, String nombreUsuario, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.authorities = authorities;
    }

    public static UsuarioPrincipal build(Usuario usuario){
        List<GrantedAuthority> authorities =
                usuario.getRoles().stream().map(
                		rol -> new SimpleGrantedAuthority(rol.getNombreRol())
                ).collect(Collectors.toList());
        
        List<GrantedAuthority> permisos =
        	    usuario.getRoles().stream().flatMap(
        	        rol -> (rol.getPermisos().stream().map(
        	        			permiso -> new SimpleGrantedAuthority(permiso.getNombrePermiso())
        	            )
        	        )
        	    ).collect(Collectors.toList());
        
        authorities.addAll(permisos);
        
        return new UsuarioPrincipal(usuario.getId(), usuario.getNombreUsuario(), usuario.getPassword(), authorities);
    }
    
    
    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getId() {
        return id;
    }
}
