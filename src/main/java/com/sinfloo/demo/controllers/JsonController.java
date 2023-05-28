package com.sinfloo.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;

import com.sinfloo.demo.models.Permiso;
import com.sinfloo.demo.models.Rol;
import com.sinfloo.demo.services.PermisoService;
import com.sinfloo.demo.services.RolService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class JsonController {
    
    @Autowired
    RolService rolService;

    @Autowired
    PermisoService permisoService;

    @GetMapping("/listadoRolPermisos")
    @ResponseBody
    public List<Object> listadoRolPermisos(@RequestParam() int idRol) {
        List<Permiso> rolPermisos = new ArrayList<>(rolService.get(idRol).getPermisos());
        List<Permiso> permisos = permisoService.listarPermisos();
         // Crear un arreglo genérico de objetos
        List<Object> resultado = new ArrayList<>();

        for (Permiso permiso : permisos) {
            
            if (rolPermisos.contains(permiso)) {
                Map<String, Object> objetoCoincidente = new HashMap<>();
                objetoCoincidente.put("id", permiso.getId());
                objetoCoincidente.put("nombre", permiso.getNombrePermiso());
                objetoCoincidente.put("activo", true);
                resultado.add(objetoCoincidente);
            }else{
                Map<String, Object> objetoCoincidente = new HashMap<>();
                objetoCoincidente.put("id", permiso.getId());
                objetoCoincidente.put("nombre", permiso.getNombrePermiso());
                objetoCoincidente.put("activo", false);
                resultado.add(objetoCoincidente);
            }
        }
        
        return resultado;
    }
    /*http://localhost:4403/listadoRolPermisos?idRol=1*/
    /*http://localhost:4403/actualizarRolPermisos?idRol=1&idPermiso=3&valor=1*/
    @GetMapping("/actualizarRolPermisos")
    @ResponseBody
    public Object actualizarRolPermisos(@RequestParam() int idRol,@RequestParam() int idPermiso,@RequestParam()int valor) {
    	Rol rol = rolService.get(idRol);
    	if(valor == 1) {
    		boolean permisoExistente = rol.getPermisos().contains(permisoService.get(idPermiso));
    		if (!permisoExistente) {
    		    Permiso permiso = permisoService.get(idPermiso);
    		    rol.getPermisos().add(permiso);
    		    rolService.save(rol);
    		}
    		Map<String, Object> resultado = new HashMap<>();
        	resultado.put("Success", true);
        	resultado.put("Mensaje", "Se activó correctamente");
            return resultado;
    	}else {
    		rol.getPermisos().removeIf(permiso -> permiso.getId() == idPermiso);
    		rolService.save(rol);
    		Map<String, Object> resultado = new HashMap<>();
        	resultado.put("Success", true);
        	resultado.put("Mensaje", "Se desactivó correctamente");
            return resultado;
    	}
    	
    	
    }
    
}
