package com.sinfloo.demo.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.enums.PermisoNombre;
import com.sinfloo.demo.enums.RolNombre;
import com.sinfloo.demo.models.Permiso;
import com.sinfloo.demo.models.Rol;
import com.sinfloo.demo.services.PermisoService;
import com.sinfloo.demo.services.RolService;

@Service
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;
    
    @Autowired
    PermisoService permisoService;

    @Override
    public void run(String... args) throws Exception {
    	/*Permiso rolPermiso = new Permiso(PermisoNombre.PERMISO_ELIMINAR);
        Permiso rolPermiso2 = new Permiso(PermisoNombre.PERMISO_CREAR);
        permisoService.save(rolPermiso);
        permisoService.save(rolPermiso2);
    	
        Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
        Rol rolUser = new Rol(RolNombre.ROLE_USER);
        rolService.save(rolAdmin);
        rolService.save(rolUser);*/
    }
}
