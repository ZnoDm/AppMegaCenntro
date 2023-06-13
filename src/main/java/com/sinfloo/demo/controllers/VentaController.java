package com.sinfloo.demo.controllers;

import java.util.List;

import javax.validation.Valid;



import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.sinfloo.demo.models.*;
import com.sinfloo.demo.services.*;


@Controller
@RequestMapping("/venta")
public class VentaController {
	
	@Autowired
	VentaService ventaService;
	
	@Autowired
	ProductoService productoService;
	
	@Autowired
	TrabajadorService trabajadorService;
	
	@Autowired
    UsuarioService usuarioService;
	
	private String edit_template ="admin/venta/editar";
	private String add_template ="admin/venta/nuevo";
    private String list_template ="admin/venta/listar";
    private String list_redirect ="redirect:/venta/listar";
	    
	@GetMapping("/add")
    public String addVenta(Model model){
		Venta venta =  new Venta();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
	     // Verificar si el usuario está autenticado
	     if (authentication != null && authentication.isAuthenticated()) {
	         // Obtener el nombre de usuario
	         String username = authentication.getName();
	         
	         // Hacer algo con el nombre de usuario
	         System.out.println("Usuario autenticado: " + username);
	         Trabajador trabajador = trabajadorService.getByUsuario(usuarioService.getByNombreUsuario(username).get());
	         System.out.println(trabajadorService.getByUsuario(usuarioService.getByNombreUsuario(username).get()).getApellidos());
	         
	                
	         
	         venta.setTrabajador(trabajador);
	         model.addAttribute("venta",venta);
	         return add_template;
	     }else {
	    	 return list_redirect;
	     }
    }
	@PostMapping("/save")
    public String saveVenta(@Valid @ModelAttribute("venta") Venta venta, 
    		BindingResult result, Model model,
    		@RequestParam(name = "item_id[]", required = false) String[] itemId,
			@RequestParam(name = "cantidad[]", required = false) String[] cantidad){
    	
    	if (result.hasErrors()) {
    		model.addAttribute("mensajeError","Llene todos los campos");
			return add_template;
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("mensajeError","Error: La factura NO puede no tener líneas!");
			return add_template;
		}
		System.out.println("ID: " + itemId + ", cantidad: " + cantidad);
		for (int i = 1; i < itemId.length; i++) {
			Producto producto = productoService.get( Integer.parseInt(itemId[i]));

			DetalleVenta linea = new DetalleVenta();
			linea.setCantidad(Integer.parseInt(cantidad[i]));
			linea.setProducto(producto);
			venta.addItemDetalleVenta(linea);

			System.out.println("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
		}
		List<Venta> listaVentas = ventaService.listarVentas();
        int correlativo = listaVentas.size()>0 ?  listaVentas.get(listaVentas.size() - 1).getCorrelativo() : 1;
        venta.setCorrelativo(correlativo);
        venta.setCodigo("V2023-" + (correlativo< 10 ? ("0" + correlativo): correlativo  ));
        
		ventaService.save(venta);
        return list_redirect;
    }

    @GetMapping("/edit/{id}")
    public String editVenta(@PathVariable("id") Integer id, Model model){
        Venta venta = ventaService.get(id);
        model.addAttribute("venta", venta);

        return edit_template;
    }
    
    @PostMapping("/edit")
    public String editVenta(@Valid @ModelAttribute("venta") Venta venta, 
    		BindingResult result, Model model){
    	
    	
        
        return list_redirect;
    }


    
    
    @GetMapping("/delete/{id}")
    public String deleteVenta(@PathVariable("id") Integer id, Model model) {
        ventaService.delete(id);
    
        return list_redirect+ "?mensajeAlert=Venta eliminado.";
    }

    @GetMapping("/listar")
    public String listVenta(@RequestParam(defaultValue = "0") int pagina,Model model,
    		@RequestParam(required = false) String mensajeAlert,
    		@RequestParam(required = false) String mensajeError) {
    	int tamanoPagina = 5;
    	List<Venta> listaVentas = ventaService.listarVentas();
        int totalVentas = listaVentas.size();
        int desde = pagina * tamanoPagina;
        int hasta = Math.min(desde + tamanoPagina, totalVentas);
        List<Venta> rolesPaginados = listaVentas.subList(desde, hasta);
        Page<Venta> paginaVentas = new PageImpl<Venta>(rolesPaginados, PageRequest.of(pagina, tamanoPagina), totalVentas);
        model.addAttribute("ventas", paginaVentas);
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", (totalVentas + tamanoPagina - 1) / tamanoPagina);
        model.addAttribute("mensajeAlert",mensajeAlert);
        model.addAttribute("mensajeError",mensajeError);
        

        return list_template;
    }
    
    
}
