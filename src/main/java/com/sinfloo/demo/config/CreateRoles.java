package com.sinfloo.demo.config;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.enums.PermisoNombre;
import com.sinfloo.demo.enums.RolNombre;
import com.sinfloo.demo.models.Permiso;
import com.sinfloo.demo.models.Rol;
import com.sinfloo.demo.models.Usuario;
import com.sinfloo.demo.services.PermisoService;
import com.sinfloo.demo.services.RolService;
import com.sinfloo.demo.services.UsuarioService;

@Service
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;
    
    @Autowired
    PermisoService permisoService;
    
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
    	
    	Rol rolAdmin1 = new Rol(RolNombre.ROLE_ADMIN.toString());
        Rol rolUser1 = new Rol(RolNombre.ROLE_USER.toString());
        rolService.save(rolAdmin1);
        rolService.save(rolUser1);
         
    	Permiso permiso_eliminar = new Permiso(PermisoNombre.PERMISO_ELIMINAR.toString());
        Permiso permiso_crear = new Permiso(PermisoNombre.PERMISO_CREAR.toString());
        

        permisoService.save(permiso_eliminar);
        permisoService.save(permiso_crear);
        
        Set<Permiso> permisos = new HashSet<>();
        permisos.add(permiso_eliminar);
        permisos.add(permiso_crear);
    	
       
        rolAdmin1.setPermisos(permisos);
        rolUser1.setPermisos(permisos);
        rolService.save(rolAdmin1);
        rolService.save(rolUser1);
        
        
        Usuario usuario = new Usuario();
        String passwordEncoded = passwordEncoder.encode("123");
        usuario.setNombreUsuario("admin");
        usuario.setPassword(passwordEncoded);
        Rol rolAdmin = rolService.getByNombreRol(RolNombre.ROLE_ADMIN.toString()).get();
        Set<Rol> roles = new HashSet<>();
        roles.add(rolAdmin);
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        
    }
}
