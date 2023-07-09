package com.sinfloo.demo.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.format.annotation.DateTimeFormat;
import com.sinfloo.demo.enums.EstadoVenta;
import com.sinfloo.demo.models.*;
import com.sinfloo.demo.services.*;


@Controller
@RequestMapping("notasalida")
public class NotaSalidaController {
	
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
	
	private String edit_template ="/admin/notasalida/editar";
	private String add_template ="/admin/notasalida/nuevo";
	private String ver_template ="/admin/notasalida/ver";
    private String list_template ="/admin/notasalida/listar";
    private String list_redirect ="redirect:/notasalida/listar";
	 

    @GetMapping("/edit/{id}")
    public String editNotaSalida(@PathVariable("id") Integer id, Model model){
        NotaSalida notasalida = notasalidaService.get(id);
        
        
        Venta venta = ventaService.get(notasalida.getVenta().getId());
        
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

        model.addAttribute("detalleVentas", datosDetalleVentas);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     String username = authentication.getName();
     System.out.println("Usuario autenticado: " + username);
     Trabajador trabajador = trabajadorService.getByUsuario(usuarioService.getByNombreUsuario(username).get());              

     notasalida.setTrabajador(trabajador);
     
     Date fechaActual = new Date();
     notasalida.setFechaEntrega(fechaActual);
     
     model.addAttribute("notasalida", notasalida);

	return edit_template;
    }
    
    @PostMapping("/edit")
    public String editNotaSalida(@Valid @ModelAttribute("notasalida") NotaSalida notasalida, 
    		@RequestParam("fechaEntrega") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaEntrega,
    		BindingResult result, Model model){
    	
    	notasalida.setFechaEntrega(fechaEntrega);
    	notasalida.setEstado(EstadoVenta.CERRADO);
    	Date fechaActual = new Date();
    	notasalida.setFechaModificacion(fechaActual);
    	notasalida.setUsuarioModificacion(trabajadorService.get(notasalida.getTrabajador().getId()).getUsuario().getNombreUsuario());
    	
 		
    	
    	Venta venta = ventaService.get(notasalida.getVenta().getId());
    	 String mensajeAlert = "";
    	 
         List<DetalleVenta> detalleVentas = venta.getItems();
         System.out.println(venta.getItems().size());
         // Crear una lista de objetos con los datos relevantes de cada objeto DetalleVenta
         List<Object> datosDetalleVentas = new ArrayList<>();
         for (DetalleVenta detalleVenta : detalleVentas) {
        	 Producto productoUpdate = productoService.get(detalleVenta.getProducto().getId());

        	 Integer stockActual =  productoUpdate.getStock() - detalleVenta.getCantidad();
        	 
        	 if(stockActual <0) {
        		 mensajeAlert = "?mensajeError = NotaSalida no se puede cerrar.";
        		 return list_redirect + mensajeAlert;
        	 }
        	 
        	 productoUpdate.setStock(stockActual);
        	 productoService.save(productoUpdate);
         }
        venta.setEstado(EstadoVenta.CERRADO);
        ventaService.save(venta);
        notasalidaService.save(notasalida);
		
	    mensajeAlert = "?mensajeAlert=NotaSalida CERRADA.";
	        
	    return list_redirect + mensajeAlert;
    }


    
    @GetMapping("/listar")
    public String listNotaSalida(@RequestParam(defaultValue = "0") int pagina,Model model,
    		@RequestParam(required = false) String mensajeAlert,
    		@RequestParam(required = false) String mensajeError) {
    	int tamanoPagina = 5;
    	List<NotaSalida> listaNotaSalidas = notasalidaService.listarNotaSalidas();
        int totalNotaSalidas = listaNotaSalidas.size();
        int desde = pagina * tamanoPagina;
        int hasta = Math.min(desde + tamanoPagina, totalNotaSalidas);
        List<NotaSalida> rolesPaginados = listaNotaSalidas.subList(desde, hasta);
        Page<NotaSalida> paginaNotaSalidas = new PageImpl<NotaSalida>(rolesPaginados, PageRequest.of(pagina, tamanoPagina), totalNotaSalidas);
        model.addAttribute("notasalidas", paginaNotaSalidas);
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", (totalNotaSalidas + tamanoPagina - 1) / tamanoPagina);
        model.addAttribute("mensajeAlert",mensajeAlert);
        model.addAttribute("mensajeError",mensajeError);
        

        return list_template;
    }
    
    
}
