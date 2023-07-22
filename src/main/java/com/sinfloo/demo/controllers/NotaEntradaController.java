package com.sinfloo.demo.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

import com.sinfloo.demo.enums.EstadoVenta;
import com.sinfloo.demo.models.*;
import com.sinfloo.demo.services.*;


@Controller
@RequestMapping("notaentrada")
public class NotaEntradaController {
	
	@Autowired
	NotaEntradaService notaentradaService;
	
	@Autowired
	ProductoService productoService;
	
	@Autowired
	TrabajadorService trabajadorService;
	
	@Autowired
    UsuarioService usuarioService;
	
	@Autowired
    ProveedorService proveedorService;
	
	private String edit_template ="admin/notaentrada/editar";
	private String add_template ="admin/notaentrada/nuevo";
	private String ver_template ="admin/notaentrada/ver";
    private String list_template ="admin/notaentrada/listar";
    private String list_redirect ="redirect:/notaentrada/listar";
	    
	@GetMapping("/add")
    public String addNotaEntrada(Model model){
		NotaEntrada notaentrada =  new NotaEntrada();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
	     // Verificar si el usuario está autenticado
	     if (authentication != null && authentication.isAuthenticated()) {
	         // Obtener el nombre de usuario
	         String username = authentication.getName();
	         
	         // Hacer algo con el nombre de usuario
	         System.out.println("Usuario autenticado: " + username);
	         Trabajador trabajador = trabajadorService.getByUsuario(usuarioService.getByNombreUsuario(username).get());
	         System.out.println(trabajadorService.getByUsuario(usuarioService.getByNombreUsuario(username).get()).getApellidos());
	         
	                
	         
	         notaentrada.setTrabajador(trabajador);
	         model.addAttribute("notaentrada",notaentrada);
	         return add_template;
	     }else {
	    	 return list_redirect;
	     }
    }
	@PostMapping("/save")
    public String saveNotaEntrada(@Valid @ModelAttribute("notaentrada") NotaEntrada notaentrada, 
    		BindingResult result, Model model,
    		@RequestParam(name = "item_id[]", required = false) String[] itemId,
			@RequestParam(name = "cantidad[]", required = false) String[] cantidad){
    	
    	if (result.hasErrors()) {
    		model.addAttribute("mensajeError","Llene todos los campos");
			return add_template;
		}
    	System.out.println(notaentrada.getProveedor());
    	if(!proveedorService.existsById(notaentrada.getProveedor().getId())) {
    		model.addAttribute("mensajeError","Llene un proveedor los campos");
			return add_template;
    	}
		if (itemId == null || itemId.length == 1) {
			model.addAttribute("mensajeError","Error: La notaentrada NO puede no tener productos!");
			return add_template;
		}
		System.out.println("ID: " + itemId + ", cantidad: " + cantidad);
		for (int i = 1; i < itemId.length; i++) {
			Producto producto = productoService.get( Integer.parseInt(itemId[i]));

			DetalleNotaEntrada linea = new DetalleNotaEntrada();
			linea.setCantidad(Integer.parseInt(cantidad[i]));
			linea.setProducto(producto);
			notaentrada.addItemDetalleNotaEntrada(linea);
			producto.setStock(producto.getStock()+Integer.parseInt(cantidad[i]));
			productoService.save(producto);
		}
		List<NotaEntrada> listaNotaEntradas = notaentradaService.listarNotaEntradas();
        int correlativo = listaNotaEntradas.size()>0 ?  listaNotaEntradas.get(listaNotaEntradas.size() - 1).getCorrelativo() : 1;
        notaentrada.setCorrelativo(correlativo);
        notaentrada.setCodigo("V2023-" + (correlativo< 10 ? ("0" + correlativo): correlativo  ));
        notaentrada.setEstado(EstadoVenta.CERRADO);
        Date fechaActual = new Date();
        notaentrada.setFechaRecepcion(fechaActual);
        notaentrada.setFechaRegistro(fechaActual);
        notaentrada.setUsuarioRegistro(trabajadorService.get(notaentrada.getTrabajador().getId()).getUsuario().getNombreUsuario());
		notaentradaService.save(notaentrada);
		String mensajeAlert = "";
        mensajeAlert = "?mensajeAlert=NotaEntrada registrado.";
        return list_redirect + mensajeAlert;
    }

    @GetMapping("/edit/{id}")
    public String editNotaEntrada(@PathVariable("id") Integer id, Model model){
        NotaEntrada notaentrada = notaentradaService.get(id);
        List<DetalleNotaEntrada> detalleNotaEntradas = notaentrada.getItems();
        System.out.println(notaentrada.getItems().size());
        // Crear una lista de objetos con los datos relevantes de cada objeto DetalleNotaEntrada
        List<Object> datosDetalleNotaEntradas = new ArrayList<>();
        for (DetalleNotaEntrada detalleNotaEntrada : detalleNotaEntradas) {
            Map<String, Object> datos = new HashMap<>();
            datos.put("id", detalleNotaEntrada.getProducto().getId()); 
            datos.put("nombre", detalleNotaEntrada.getProducto().getNombreProducto()); 
            datos.put("cantidad", detalleNotaEntrada.getCantidad()); 
            datosDetalleNotaEntradas.add(datos);
        }

        model.addAttribute("detalleNotaEntradas", datosDetalleNotaEntradas);
        model.addAttribute("notaentrada", notaentrada);
        notaentrada.getItems();
        return edit_template;
    }
    
    @PostMapping("/edit")
    public String editNotaEntrada(@Valid @ModelAttribute("notaentrada") NotaEntrada notaentrada, 
    		BindingResult result, Model model,
    		@RequestParam(name = "item_id[]", required = false) String[] itemId,
			@RequestParam(name = "cantidad[]", required = false) String[] cantidad){
    	if (result.hasErrors()) {
    		model.addAttribute("mensajeError","Llene todos los campos");
			return add_template;
		}
    	System.out.println(notaentrada.getProveedor());
    	if(!proveedorService.existsById(notaentrada.getProveedor().getId())) {
    		model.addAttribute("mensajeError","Llene un proveedor los campos");
			return add_template;
    	}
		if (itemId == null || itemId.length == 1) {
			model.addAttribute("mensajeError","Error: La notaentrada NO puede no tener productos!");
			return add_template;
		}
		NotaEntrada notaentradax = notaentradaService.get(notaentrada.getId());
        List<DetalleNotaEntrada> detalleNotaEntradas = notaentradax.getItems();
		System.out.println("detalleNotaEntradas.size()"+detalleNotaEntradas.size());
		for (int i = 0; i < detalleNotaEntradas.size(); i++) {
		    DetalleNotaEntrada detalle = detalleNotaEntradas.get(i);
		    Producto producto = productoService.get(detalle.getProducto().getId());
		    Integer cant = producto.getStock() - detalle.getCantidad();
		    producto.setStock(cant);
		    System.out.println("Cantidad restada"+cant);
		    productoService.save(producto);
		}
		
		notaentradax.getItems().clear();
		
		for (int i = 1; i < itemId.length; i++) {
			Producto producto = productoService.get( Integer.parseInt(itemId[i]));

			DetalleNotaEntrada linea = new DetalleNotaEntrada();
			linea.setCantidad(Integer.parseInt(cantidad[i]));
			linea.setProducto(producto);
			notaentrada.addItemDetalleNotaEntrada(linea);
			producto.setStock(producto.getStock()+Integer.parseInt(cantidad[i]));
			productoService.save(producto);
		}
        notaentrada.setEstado(EstadoVenta.CERRADO);
        Date fechaActual = new Date();
        notaentrada.setFechaModificacion(fechaActual);
        notaentrada.setUsuarioModificacion(trabajadorService.get(notaentrada.getTrabajador().getId()).getUsuario().getNombreUsuario());
		notaentradaService.save(notaentrada);
        
		 String mensajeAlert = "";
	        mensajeAlert = "?mensajeAlert=NotaEntrada actualizada.";
	        
	        return list_redirect + mensajeAlert;
    }


    
    
    @GetMapping("/abrir/{id}")
    public String anularNotaEntrada(@PathVariable("id") Integer id, Model model) {
		 NotaEntrada notaentrada = notaentradaService.get(id);
		 
		 notaentrada.setEstado(EstadoVenta.ABIERTO);
		 notaentradaService.save(notaentrada);
		 return list_redirect+ "?mensajeAlert=NotaEntrada abierta.";
    }
    
    @GetMapping("/ver/{id}")
    public String verNotaEntrada(@PathVariable("id") Integer id, Model model){
        NotaEntrada notaentrada = notaentradaService.get(id);
        List<DetalleNotaEntrada> detalleNotaEntradas = notaentrada.getItems();
        System.out.println(notaentrada.getItems().size());
        // Crear una lista de objetos con los datos relevantes de cada objeto DetalleNotaEntrada
        List<Object> datosDetalleNotaEntradas = new ArrayList<>();
        for (DetalleNotaEntrada detalleNotaEntrada : detalleNotaEntradas) {
            Map<String, Object> datos = new HashMap<>();
            datos.put("id", detalleNotaEntrada.getProducto().getId()); 
            datos.put("nombre", detalleNotaEntrada.getProducto().getNombreProducto()); 
            datos.put("cantidad", detalleNotaEntrada.getCantidad()); 
            datosDetalleNotaEntradas.add(datos);
        }

        // Agregar la lista de datosDetalleNotaEntradas al modelo
        model.addAttribute("detalleNotaEntradas", datosDetalleNotaEntradas);
        model.addAttribute("notaentrada", notaentrada);
        notaentrada.getItems();
        return ver_template;
    }
    
    @GetMapping("/listar")
    public String listNotaEntrada(@RequestParam(defaultValue = "0") int pagina,Model model,
    		@RequestParam(required = false) String mensajeAlert,
    		@RequestParam(required = false) String mensajeError) {
    	int tamanoPagina = 5;
    	List<NotaEntrada> listaNotaEntradas = notaentradaService.listarNotaEntradas();
        int totalNotaEntradas = listaNotaEntradas.size();
        int desde = pagina * tamanoPagina;
        int hasta = Math.min(desde + tamanoPagina, totalNotaEntradas);
        List<NotaEntrada> rolesPaginados = listaNotaEntradas.subList(desde, hasta);
        Page<NotaEntrada> paginaNotaEntradas = new PageImpl<NotaEntrada>(rolesPaginados, PageRequest.of(pagina, tamanoPagina), totalNotaEntradas);
        model.addAttribute("notaentradas", paginaNotaEntradas);
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", (totalNotaEntradas + tamanoPagina - 1) / tamanoPagina);
        model.addAttribute("mensajeAlert",mensajeAlert);
        model.addAttribute("mensajeError",mensajeError);
        

        return list_template;
    }
    
    
}
