package com.sinfloo.demo.controllers;

import com.sinfloo.demo.models.Categoria;
import com.sinfloo.demo.models.Rol;
import com.sinfloo.demo.models.Rol;
import com.sinfloo.demo.services.RolService;

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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import javax.validation.Valid;


@Controller
@RequestMapping("/rol")
public class RolController {

    @Autowired
    RolService rolService;
    
    private String edit_template ="/admin/rol/editar";
	private String add_template ="/admin/rol/nuevo";
    private String list_template ="/admin/rol/listar";
    private String list_redirect ="redirect:/rol/listar";
	    
	@GetMapping("/add")
    public String addRol(Rol rol, Model model){
		
        model.addAttribute("rol",rol);

        return add_template;
    }
	
	@PostMapping("/save")
    public String saveRol(@Valid @ModelAttribute("rol") Rol rol, 
    		BindingResult result, Model model){

        if(result.hasErrors()){
            return add_template;
        }
        System.out.println(result.hasErrors());
        rolService.save(rol);
        return list_redirect;
    }
	 
	@PostMapping("/editar")
    public String editarRol(@Valid @ModelAttribute("rol") Rol rol, 
    		BindingResult result, Model model){

        if(result.hasErrors()){
            return edit_template;
        }
        if(rolService.existsByNombreRol(rol.getNombreRol())) {
        	 return edit_template;
        }
        System.out.println(result.hasErrors());
        rolService.save(rol);
        return list_redirect;
    }
	 
	@GetMapping("/edit/{id}")
    public String editRol(@PathVariable("id") Integer id, Model model){
        Rol rol = rolService.get(id);
        model.addAttribute("rol", rol);

        return edit_template;
    }
	
	@GetMapping("/delete/{id}")
    public String deleteRol(@PathVariable("id") Integer id, Model model) {
	 	rolService.delete(id);
       return list_redirect;
    }

	 
    @GetMapping("/listar")
    public String listarRoles(@RequestParam(defaultValue = "0") int pagina,Model model) {
    	int tamanoPagina = 5;
        List<Rol> roles = rolService.listarRoles();
        int totalRoles = roles.size();
        int desde = pagina * tamanoPagina;
        int hasta = Math.min(desde + tamanoPagina, totalRoles);
        List<Rol> rolesPaginados = roles.subList(desde, hasta);
        Page<Rol> paginaRoles = new PageImpl<Rol>(rolesPaginados, PageRequest.of(pagina, tamanoPagina), totalRoles);
        model.addAttribute("roles", paginaRoles);
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", (totalRoles + tamanoPagina - 1) / tamanoPagina);

        return "/admin/rol/listar";
    }
    
    @GetMapping("/listadoAsignarPermisos")
    public String listadoAsignarPermisos(Model model) {
        List<Rol> roles = rolService.listarRoles();
        model.addAttribute("roles", roles);
        return "/admin/rol/asignar";
    }


}
