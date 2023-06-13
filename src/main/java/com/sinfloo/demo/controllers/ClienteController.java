package com.sinfloo.demo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.sinfloo.demo.enums.TipoDocumento;
import com.sinfloo.demo.models.Cliente;
import com.sinfloo.demo.models.Producto;
import com.sinfloo.demo.services.ClienteService;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@Controller
@RequestMapping("cliente")
public class ClienteController {
	
	@Autowired
	ClienteService clienteService;
	
	private String edit_template ="/admin/cliente/editar";
	private String add_template ="/admin/cliente/nuevo";
    private String list_template ="/admin/cliente/listar";
    private String list_redirect ="redirect:/cliente/listar";
	    
	@GetMapping("/add")
    public String addCliente(Cliente cliente, Model model){
		
        model.addAttribute("cliente",cliente);

        return add_template;
    }
	@PostMapping("/save")
    public String saveCliente(@Valid @ModelAttribute("cliente") Cliente cliente, 
    		BindingResult result, Model model){

        if(result.hasErrors()){
        	model.addAttribute("mensajeError","Llene todos los campos");
            return add_template;
        }
        System.out.println(result.hasErrors());
        clienteService.save(cliente);
        String mensajeAlert = "";
        mensajeAlert = "?mensajeAlert=Cliente registrado.";
        return list_redirect+ mensajeAlert;
    }

    @GetMapping("/edit/{id}")
    public String editCliente(@PathVariable("id") Integer id, Model model){
        Cliente cliente = clienteService.get(id);
        model.addAttribute("cliente", cliente);

        return edit_template;
    }
    

    @PostMapping("/edit")
    public String editCliente(@Valid @ModelAttribute("cliente") Cliente cliente, 
    		BindingResult result, Model model){

        if(result.hasErrors()){
        	model.addAttribute("mensajeError","Llene todos los campos");
            return edit_template;
        }
        System.out.println(result.hasErrors());
        clienteService.save(cliente);
        String mensajeAlert = "";
        mensajeAlert = "?mensajeAlert=Cliente actualizado.";
        return list_redirect+ mensajeAlert;
    }

    
    



    @GetMapping("/delete/{id}")
    public String deleteCliente(@PathVariable("id") Integer id, Model model) {
        clienteService.eliminar(id,false);
    
        return list_redirect+ "?mensajeAlert=Cliente eliminado.";
    }

    @GetMapping("/listar")
    public String listCliente(@RequestParam(defaultValue = "0") int pagina,Model model,
    		@RequestParam(required = false) String mensajeAlert,
    		@RequestParam(required = false) String mensajeError) {
    	int tamanoPagina = 5;
    	List<Cliente> listaClientes = clienteService.listarClientes();
        int totalClientes = listaClientes.size();
        int desde = pagina * tamanoPagina;
        int hasta = Math.min(desde + tamanoPagina, totalClientes);
        List<Cliente> rolesPaginados = listaClientes.subList(desde, hasta);
        Page<Cliente> paginaClientes = new PageImpl<Cliente>(rolesPaginados, PageRequest.of(pagina, tamanoPagina), totalClientes);
        model.addAttribute("clientes", paginaClientes);
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", (totalClientes + tamanoPagina - 1) / tamanoPagina);
        model.addAttribute("mensajeAlert",mensajeAlert);
        model.addAttribute("mensajeError",mensajeError);

        return list_template;
    }
    
    @GetMapping(value = "/cargar-clientes/{term}", produces = { "application/json" })
    @ResponseBody
    public List<Cliente> consultarClientePorDocumentoIdentidad(@PathVariable String term) {
    	
    	return clienteService.listarByDocumentoIdentidad(term);
    	
    }

	@GetMapping(value = "/consultar-clientes/{term}", produces = { "application/json" })
	@ResponseBody
	public Cliente consultarClientePorDni(@PathVariable String term) {
		
		String apiToken = "?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5hbmdlbGVzQHVuaXRydS5lZHUucGUifQ.p3EDGwPzdLiO4IlAyUWnSmkuQumo3JgBuQ4DYHQ2Zp4";
        String url = "https://dniruc.apisperu.com/api/v1/dni/";
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url + term + apiToken, HttpMethod.GET, null, String.class);
        
        Cliente cliente = new Cliente();
        
        if (response.getStatusCode().is2xxSuccessful()) {
        	try {
	            JSONParser parser = new JSONParser();
	            JSONObject json = (JSONObject) parser.parse(response.getBody());
	
	            
	            cliente.setNombres((String) json.get("nombres") + " " + json.get("apellidoPaterno") + " "  +json.get("apellidoMaterno"));
	            cliente.setTipoDocumentoIdentidad(TipoDocumento.DNI);
	            cliente.setDocumentoIdentidad((String) json.get("dni"));
	            clienteService.save(cliente);
	            return cliente;
	    	  } catch (ParseException e) {
	    		  return cliente;
	          }
        } else {
            return cliente;
        }
	}
	
	

}
