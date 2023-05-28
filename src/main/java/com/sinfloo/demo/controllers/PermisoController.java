package com.sinfloo.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sinfloo.demo.enums.PermisoNombre;
import com.sinfloo.demo.models.Permiso;
import com.sinfloo.demo.models.Permiso;
import com.sinfloo.demo.services.PermisoService;
import com.sinfloo.demo.services.PermisoService;

@Controller
@RequestMapping("/permiso")
public class PermisoController {

	@Autowired
    PermisoService permisoService;
	
    
    private String edit_template ="/admin/permiso/editar";
   	private String add_template ="/admin/permiso/nuevo";
   	private String list_template ="/admin/permiso/listar";
   	private String list_redirect ="redirect:/permiso/listar";
       
    @GetMapping("/add")
    public String addPermiso(Permiso permiso, Model model){
		
        model.addAttribute("permiso",permiso);

        return add_template;
    }
	
	@PostMapping("/save")
    public String savePermiso(@Valid @ModelAttribute("permiso") Permiso permiso, 
    		BindingResult result, Model model){

        if(result.hasErrors()){
            return add_template;
        }
        System.out.println(result.hasErrors());
        permisoService.save(permiso);
        return list_redirect;
    }
	 
	@PostMapping("/editar")
    public String editarPermiso(@Valid @ModelAttribute("permiso") Permiso permiso, 
    		BindingResult result, Model model){

        if(result.hasErrors()){
            return edit_template;
        }
        if(permisoService.existsByNombrePermiso(permiso.getNombrePermiso())) {
        	 return edit_template;
        }
        System.out.println(result.hasErrors());
        permisoService.save(permiso);
        return list_redirect;
    }
	 
	@GetMapping("/edit/{id}")
    public String editPermiso(@PathVariable("id") Integer id, Model model){
        Permiso permiso = permisoService.get(id);
        model.addAttribute("permiso", permiso);

        return edit_template;
    }
	
	@GetMapping("/delete/{id}")
    public String deletePermiso(@PathVariable("id") Integer id, Model model) {
	 	permisoService.delete(id);
       return list_redirect;
    }
	
    @GetMapping("/listar")
    public String listarPermisos(@RequestParam(defaultValue = "0") int pagina,Model model) {
    	int tamanoPagina = 5;    	
    	List<Permiso> permisos = permisoService.listarPermisos();

    	System.out.println("Total de permisos: " +permisos.size());
    	    	
        int totalPermisos = permisos.size();
        int desde = pagina * tamanoPagina;
        int hasta = Math.min(desde + tamanoPagina, totalPermisos);
        List<Permiso> permisosPaginados = permisos.subList(desde, hasta);
        Page<Permiso> paginaPermisos = new PageImpl<Permiso>(permisosPaginados, PageRequest.of(pagina, tamanoPagina), totalPermisos);
        model.addAttribute("permisos", paginaPermisos);
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", (totalPermisos + tamanoPagina - 1) / tamanoPagina);

        return "/admin/permiso/listar";
    }

    
}
