package com.sinfloo.demo.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
@RequestMapping("venta")
public class VentaController {
	
	@Autowired
    NotaSalidaService notasalidaService;
	
	@Autowired
	VentaService ventaService;
	
	@Autowired
	ProductoService productoService;
	
	@Autowired
	TrabajadorService trabajadorService;
	
	@Autowired
    UsuarioService usuarioService;
	
	@Autowired
    ClienteService clienteService;
	
	private String edit_template ="admin/venta/editar";
	private String add_template ="admin/venta/nuevo";
	private String ver_template ="admin/venta/ver";
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
    	System.out.println(venta.getCliente());
    	if(!clienteService.existsById(venta.getCliente().getId())) {
    		model.addAttribute("mensajeError","Llene un cliente los campos");
			return add_template;
    	}
		if (itemId == null || itemId.length == 1) {
			model.addAttribute("mensajeError","Error: La venta NO puede no tener productos!");
			return add_template;
		}
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
        venta.setEstado(EstadoVenta.ABIERTO);
        Date fechaActual = new Date();
        venta.setFechaEmision(fechaActual);
        venta.setFechaRegistro(fechaActual);
        venta.setUsuarioRegistro(trabajadorService.get(venta.getTrabajador().getId()).getUsuario().getNombreUsuario());
		ventaService.save(venta);
		
		/*NOTA SALIDA*/
		NotaSalida notasalida = new NotaSalida();
		List<NotaSalida> listaNotaSalidas = notasalidaService.listarNotaSalidas();
		int correlativoNotaSalida = listaNotaSalidas.size()>0 ?  listaNotaSalidas.get(listaNotaSalidas.size() - 1).getCorrelativo() : 1;
		notasalida.setCorrelativo(correlativoNotaSalida);
		notasalida.setCodigo("NS2023-" + (correlativoNotaSalida< 10 ? ("0" + correlativoNotaSalida): correlativoNotaSalida  ));
		notasalida.setVenta(venta);
		notasalida.setCliente(venta.getCliente());
		notasalida.setEstado(EstadoVenta.ABIERTO);
		notasalida.setFechaRegistro(fechaActual);
		notasalida.setUsuarioRegistro(trabajadorService.get(venta.getTrabajador().getId()).getUsuario().getNombreUsuario());
		notasalidaService.save(notasalida);
		
		String mensajeAlert = "";
        mensajeAlert = "?mensajeAlert=Venta registrado.";
        return list_redirect + mensajeAlert;
    }

    @GetMapping("/edit/{id}")
    public String editVenta(@PathVariable("id") Integer id, Model model){
        Venta venta = ventaService.get(id);
        List<DetalleVenta> detalleVentas = venta.getItems();
        System.out.println(venta.getItems().size());
        // Crear una lista de objetos con los datos relevantes de cada objeto DetalleVenta
        List<Object> datosDetalleVentas = new ArrayList<>();
        for (DetalleVenta detalleVenta : detalleVentas) {
            Map<String, Object> datos = new HashMap<>();
            datos.put("id", detalleVenta.getProducto().getId()); 
            datos.put("nombre", detalleVenta.getProducto().getNombreProducto()); 
            datos.put("precio", detalleVenta.calcularImporteTotal()); 
            datos.put("cantidad", detalleVenta.getCantidad()); 
            datosDetalleVentas.add(datos);
        }

        // Agregar la lista de datosDetalleVentas al modelo
        model.addAttribute("detalleVentas", datosDetalleVentas);
        model.addAttribute("venta", venta);
        return edit_template;
    }
    
    @PostMapping("/edit")
    public String editVenta(@Valid @ModelAttribute("venta") Venta venta, 
    		BindingResult result, Model model,
    		@RequestParam(name = "item_id[]", required = false) String[] itemId,
			@RequestParam(name = "cantidad[]", required = false) String[] cantidad){
    	if (result.hasErrors()) {
    		model.addAttribute("mensajeError","Llene todos los campos");
			return add_template;
		}
    	System.out.println(venta.getCliente());
    	if(!clienteService.existsById(venta.getCliente().getId())) {
    		model.addAttribute("mensajeError","Llene un cliente los campos");
			return add_template;
    	}
		if (itemId == null || itemId.length == 1) {
			model.addAttribute("mensajeError","Error: La venta NO puede no tener productos!");
			return add_template;
		}
		System.out.println("ID: " + itemId + ", cantidad: " + cantidad);
		
		venta.getItems().clear();
		
		for (int i = 1; i < itemId.length; i++) {
			Producto producto = productoService.get( Integer.parseInt(itemId[i]));

			DetalleVenta linea = new DetalleVenta();
			linea.setCantidad(Integer.parseInt(cantidad[i]));
			linea.setProducto(producto);
			venta.addItemDetalleVenta(linea);

			System.out.println("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
		}
        venta.setEstado(EstadoVenta.ABIERTO);
        Date fechaActual = new Date();
        venta.setFechaModificacion(fechaActual);
        venta.setUsuarioModificacion(trabajadorService.get(venta.getTrabajador().getId()).getUsuario().getNombreUsuario());
		ventaService.save(venta);
        
		 String mensajeAlert = "";
	        mensajeAlert = "?mensajeAlert=Venta actualizada.";
	        
	        return list_redirect + mensajeAlert;
    }


    
    
    @GetMapping("/anular/{id}")
    public String anularVenta(@PathVariable("id") Integer id, Model model) {
		 Venta venta = ventaService.get(id);
		 venta.setEstado(EstadoVenta.ANULADO);
		 ventaService.save(venta);
		 NotaSalida notaSalida = notasalidaService.getByVenta(venta).get();
		 
		 notaSalida.setEstado(EstadoVenta.ANULADO);
		 notasalidaService.save(notaSalida);
		 return list_redirect+ "?mensajeAlert=Venta anulada.";
    }
    
    @GetMapping("/ver/{id}")
    public String verVenta(@PathVariable("id") Integer id, Model model){
        Venta venta = ventaService.get(id);
        List<DetalleVenta> detalleVentas = venta.getItems();
        System.out.println(venta.getItems().size());
        // Crear una lista de objetos con los datos relevantes de cada objeto DetalleVenta
        List<Object> datosDetalleVentas = new ArrayList<>();
        for (DetalleVenta detalleVenta : detalleVentas) {
            Map<String, Object> datos = new HashMap<>();
            datos.put("id", detalleVenta.getProducto().getId()); 
            datos.put("nombre", detalleVenta.getProducto().getNombreProducto()); 
            datos.put("precio", detalleVenta.calcularImporteTotal()); 
            datos.put("cantidad", detalleVenta.getCantidad()); 
            datosDetalleVentas.add(datos);
        }

        // Agregar la lista de datosDetalleVentas al modelo
        model.addAttribute("detalleVentas", datosDetalleVentas);
        model.addAttribute("venta", venta);
        return ver_template;
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
