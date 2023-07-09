package com.sinfloo.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.sinfloo.demo.models.Cliente;
import com.sinfloo.demo.models.Proveedor;
import com.sinfloo.demo.services.ProveedorService;



@Controller
@RequestMapping("proveedor")
public class ProveedorController {
	
	@Autowired
	ProveedorService proveedorService;
	
	private String edit_template ="/admin/proveedor/editar";
	private String add_template ="/admin/proveedor/nuevo";
    private String list_template ="/admin/proveedor/listar";
    private String list_redirect ="redirect:/proveedor/listar";
	    
	@GetMapping("/add")
    public String addProveedor(Proveedor proveedor, Model model){
		
        model.addAttribute("proveedor",proveedor);

        return add_template;
    }
	 @PostMapping("/save")
	    public String saveProveedor(@Valid @ModelAttribute("proveedor") Proveedor proveedor, 
	    		BindingResult result, Model model){

	        if(result.hasErrors()){
	        	model.addAttribute("mensajeError","Llene todos los campos");
	            return add_template;
	        }
	        if(proveedorService.existsByDocumentoIdentidad(proveedor.getDocumentoIdentidad())) {
	        	model.addAttribute("mensajeError","Proveedor ya existe");
		      	 return add_template;
		       }
	        
	        System.out.println(result.hasErrors());
	        proveedorService.save(proveedor);
	        String mensajeAlert = "";
	        mensajeAlert = "?mensajeAlert=Proveedor registrado.";
	        return list_redirect+ mensajeAlert;
	    }



    @GetMapping("/edit/{id}")
    public String editProveedor(@PathVariable("id") Integer id, Model model){
        Proveedor proveedor = proveedorService.get(id);
        model.addAttribute("proveedor", proveedor);

        return edit_template;
    }
    
    @GetMapping("/enabledDisabled/{id}/{estado}")
    public String enabledDisabledProveedor(@PathVariable("id") Integer id,
    		@PathVariable("estado") Boolean estado, Model model) {
    	String mensajeAlert = "";
    	if(estado == true) {
            proveedorService.eliminar(id,true,false);
            mensajeAlert = "?mensajeAlert=Proveedor habilitado.";
    	}
    	else {
    		proveedorService.eliminar(id,false,true);
            mensajeAlert = "?mensajeAlert=Proveedor deshabilitado.";
    	}
    		
        return list_redirect+ mensajeAlert;
    }

    
    @PostMapping("/edit")
    public String editProveedor(@Valid @ModelAttribute("proveedor") Proveedor proveedor, 
    		BindingResult result, Model model){
    	
    	 if(result.hasErrors()){
         	model.addAttribute("mensajeError","Llene todos los campos");
             return edit_template;
         }
    	 if(proveedorService.existsByDocumentoIdentidad(proveedor.getDocumentoIdentidad())) {
        	 if(proveedorService.getByDocumentoIdentidad(proveedor.getDocumentoIdentidad()).get().getId() != proveedor.getId() ) {
 	        	
	        	model.addAttribute("mensajeError","Proveedor con ese documento de identidad ya existe");
		      	 return edit_template;
        	 }
	      }
        System.out.println(result.hasErrors());
        proveedorService.save(proveedor);
        String mensajeAlert = "";
        mensajeAlert = "?mensajeAlert=Proveedor actualizado.";
        return list_redirect+ mensajeAlert;
    }



    @GetMapping("/delete/{id}")
    public String deleteProveedor(@PathVariable("id") Integer id, Model model) {
    	proveedorService.delete(id);
    
        return list_redirect+ "?mensajeAlert=Proveedor eliminado.";
    }

    @GetMapping("/listar")
    public String listProveedor(@RequestParam(defaultValue = "0") int pagina,Model model,
    		@RequestParam(required = false) String mensajeAlert,
    		@RequestParam(required = false) String mensajeError) {
    	int tamanoPagina = 5;
    	List<Proveedor> listaProveedores = proveedorService.listarProveedores();
        int totalProveedores = listaProveedores.size();
        int desde = pagina * tamanoPagina;
        int hasta = Math.min(desde + tamanoPagina, totalProveedores);
        List<Proveedor> rolesPaginados = listaProveedores.subList(desde, hasta);
        Page<Proveedor> paginaProveedores = new PageImpl<Proveedor>(rolesPaginados, PageRequest.of(pagina, tamanoPagina), totalProveedores);
        model.addAttribute("proveedores", paginaProveedores);
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", (totalProveedores + tamanoPagina - 1) / tamanoPagina);
        model.addAttribute("mensajeAlert",mensajeAlert);
        model.addAttribute("mensajeError",mensajeError);

        return list_template;
    }
    
    
    @GetMapping(value = "/cargar-proveedores/{term}", produces = { "application/json" })
    @ResponseBody
    public List<Object> consultarProveedorPorDocumentoIdentidad(@PathVariable String term) {
    	
    	List<Proveedor> proveedores = proveedorService.listarByDocumentoIdentidad(term);
    	 List<Object> resultado = new ArrayList<>();

          for (Proveedor proveedor : proveedores) {
              Map<String, Object> objetoCoincidente = new HashMap<>();
              objetoCoincidente.put("id", proveedor.getId());
              objetoCoincidente.put("nombres", proveedor.getNombres());
              objetoCoincidente.put("tipoDocumentoIdentidad", proveedor.getTipoDocumentoIdentidad());
              objetoCoincidente.put("documentoIdentidad", proveedor.getDocumentoIdentidad());
              resultado.add(objetoCoincidente);
          }
		
		return resultado;
    }
    
}
