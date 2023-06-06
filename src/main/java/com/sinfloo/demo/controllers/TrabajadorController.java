package com.sinfloo.demo.controllers;

import java.util.List;

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

import com.sinfloo.demo.models.Trabajador;
import com.sinfloo.demo.services.TrabajadorService;



@Controller
@RequestMapping("/trabajador")
public class TrabajadorController {
	
	@Autowired
	TrabajadorService trabajadorService;
	
	private String edit_template ="/admin/trabajador/editar";
	private String add_template ="/admin/trabajador/nuevo";
    private String list_template ="/admin/trabajador/listar";
    private String list_redirect ="redirect:/trabajador/listar";
	    
	@GetMapping("/add")
    public String addTrabajador(Trabajador trabajador, Model model){
		
        model.addAttribute("trabajador",trabajador);

        return add_template;
    }


    @GetMapping("/edit/{id}")
    public String editTrabajador(@PathVariable("id") Integer id, Model model){
        Trabajador trabajador = trabajadorService.get(id);
        model.addAttribute("trabajador", trabajador);

        return edit_template;
    }
    @PostMapping("/save")
    public String saveTrabajador(@Valid @ModelAttribute("trabajador") Trabajador trabajador, 
    		BindingResult result, Model model){
    	
    	System.out.println(trabajador.getActivo());

        if(result.hasErrors()){
        	model.addAttribute("mensajeError","Llene todos los campos");
            return add_template;
        }
        System.out.println(result.hasErrors());
        trabajadorService.save(trabajador);
        String mensajeAlert = "";
        mensajeAlert = "?mensajeAlert=Trabajador registrado.";
        return list_redirect + mensajeAlert;
    }

    @PostMapping("/edit")
    public String editTrabajador(@Valid @ModelAttribute("trabajador") Trabajador trabajador, 
    		BindingResult result, Model model){
    	
    	System.out.println(trabajador.getActivo());

        if(result.hasErrors()){
        	model.addAttribute("mensajeError","Llene todos los campos");
            return edit_template;
        }
        System.out.println(result.hasErrors());
        trabajadorService.save(trabajador);
        String mensajeAlert = "";
        mensajeAlert = "?mensajeAlert=Trabajador actualizado.";
        
        return list_redirect + mensajeAlert;
    }

    @GetMapping("/enabledDisabled/{id}/{estado}")
    public String enabledDisabledTrabajador(@PathVariable("id") Integer id,@PathVariable("estado") Boolean estado, Model model) {
    	String mensajeAlert = "";
    	if(estado == true) {
            trabajadorService.eliminar(id,true,false);
            mensajeAlert = "?mensajeAlert=Trabajador habilitado.";
    	}
    	else {
            trabajadorService.eliminar(id,false,true);
            mensajeAlert = "?mensajeAlert=Trabajador deshabilitado.";
    	}
    		
        return list_redirect+ mensajeAlert;
    }
    
    @GetMapping("/delete/{id}")
    public String deleteTrabajador(@PathVariable("id") Integer id, Model model) {
        trabajadorService.delete(id);
    
        return list_redirect+ "?mensajeAlert=Trabajador eliminado.";
    }

    @GetMapping("/listar")
    public String listTrabajador(@RequestParam(defaultValue = "0") int pagina,Model model,
    		@RequestParam(required = false) String mensajeAlert,
    		@RequestParam(required = false) String mensajeError) {
    	int tamanoPagina = 5;
    	List<Trabajador> listaTrabajadors = trabajadorService.listarTrabajadors();
        int totalTrabajadors = listaTrabajadors.size();
        int desde = pagina * tamanoPagina;
        int hasta = Math.min(desde + tamanoPagina, totalTrabajadors);
        List<Trabajador> rolesPaginados = listaTrabajadors.subList(desde, hasta);
        Page<Trabajador> paginaTrabajadors = new PageImpl<Trabajador>(rolesPaginados, PageRequest.of(pagina, tamanoPagina), totalTrabajadors);
        model.addAttribute("trabajadores", paginaTrabajadors);
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", (totalTrabajadors + tamanoPagina - 1) / tamanoPagina);
        model.addAttribute("mensajeAlert",mensajeAlert);
        model.addAttribute("mensajeError",mensajeError);

        return list_template;
    }
    
}
