package com.sinfloo.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sinfloo.demo.enums.RolNombre;
import com.sinfloo.demo.models.Permiso;
import com.sinfloo.demo.models.Rol;
import com.sinfloo.demo.models.RolPermiso;
import com.sinfloo.demo.services.PermisoService;
import com.sinfloo.demo.services.RolService;

@Controller
@RequestMapping("/permiso")
public class PermisoController {

	@Autowired
    RolService rolService;
	
    @Autowired
    PermisoService permisoService;

    @GetMapping("/listar")
    public String listarPermisos(@RequestParam(defaultValue = "0") int pagina,Model model) {
    	int tamanoPagina = 2;
    	List<Rol> roles = rolService.listarRoles();
    	System.out.println("Total de roles: " +roles.size());
    	
    	List<RolPermiso> permisos = new ArrayList<>();

    	for (Rol rol : roles) {
    		System.out.println(rol.getPermisos());
    	    for (Permiso permiso : rol.getPermisos()) {
    	        RolPermiso permisoInfo = new RolPermiso(
    	            rol.getId(),
    	            rol.getNombreRol(),
    	            permiso.getId(),
    	            permiso.getNombrePermiso()
    	        );
    	        permisos.add(permisoInfo);
    	    }
    	}
    	
    	
    	
        int totalPermisos = permisos.size();
        int desde = pagina * tamanoPagina;
        int hasta = Math.min(desde + tamanoPagina, totalPermisos);
        List<RolPermiso> permisosPaginados = permisos.subList(desde, hasta);
        Page<RolPermiso> paginaPermisos = new PageImpl<RolPermiso>(permisosPaginados, PageRequest.of(pagina, tamanoPagina), totalPermisos);
        model.addAttribute("permisos", paginaPermisos);
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", (totalPermisos + tamanoPagina - 1) / tamanoPagina);

        return "/admin/permiso/listar";
    }
}
