package com.sinfloo.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;

import com.sinfloo.demo.models.DetalleVenta;
import com.sinfloo.demo.models.Permiso;
import com.sinfloo.demo.models.Producto;
import com.sinfloo.demo.models.Rol;
import com.sinfloo.demo.models.Trabajador;
import com.sinfloo.demo.models.Venta;
import com.sinfloo.demo.services.NotaEntradaService;
import com.sinfloo.demo.services.PermisoService;
import com.sinfloo.demo.services.RolService;
import com.sinfloo.demo.services.VentaService;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class JsonController {
    
    @Autowired
    RolService rolService;

    @Autowired
    PermisoService permisoService;
    
    @Autowired
    VentaService ventaService;
    
    @Autowired
    NotaEntradaService notaaEntradaService;
    
    @GetMapping("/api/get_countVentas")
    @ResponseBody
    public Integer get_countVentas() {
    	return  ventaService.listarVentas().size();
    }
    
    @GetMapping("/api/get_countCompras")
    @ResponseBody
    public Integer get_countCompras() {
    	return   notaaEntradaService.listarNotaEntradas().size();	
    }
    
    @GetMapping("/api/get_ventaPorTrabajador")
    @ResponseBody
    public List<Map<String, Object>> dataVentaPorTrabajador() {
    	
    	List<Map<String, Object>> resultado = new ArrayList<>();
    	List<Venta> ventas = ventaService.listarVentas();
    	Map<Trabajador, Integer> ventasPorTrabajador = new HashMap<>();

    	for (Venta venta : ventas) {
    	    Trabajador trabajador = venta.getTrabajador();
    	    ventasPorTrabajador.merge(trabajador, 1, Integer::sum);
    	}

    	int count = 0;
    	for (Map.Entry<Trabajador, Integer> entry : ventasPorTrabajador.entrySet()) {
    	    if (count >= 5) {
    	        break; // Detener el bucle después de los primeros 5 trabajadores
    	    }

    	    Trabajador trabajador = entry.getKey();
    	    int totalVentas = entry.getValue();

    	    Map<String, Object> objeto = new HashMap<>();
    	    objeto.put("trabajador", trabajador.getNombres());
    	    objeto.put("cantidadTotal", totalVentas);
    	    resultado.add(objeto);

    	    count++;
    	}

         return resultado;
    	
    }
    
    @GetMapping("/api/get_dataProductosMasVendidos")
    @ResponseBody
    public List<Map<String, Object>> dataProductosMasVendidos() {
    	
    	List<Map<String, Object>> resultado = new ArrayList<>();
    	List<Venta> ventas = ventaService.listarVentas();
    	Map<Producto, Integer> cantidadVendidaPorProducto = new HashMap<>();

    	for (Venta venta : ventas) {
    	    for (DetalleVenta detalle : venta.getItems()) {
    	        Producto producto = detalle.getProducto();
    	        int cantidad = detalle.getCantidad();

    	        cantidadVendidaPorProducto.merge(producto, cantidad, Integer::sum);
    	    }
    	}

    	int count = 0;
    	for (Map.Entry<Producto, Integer> entry : cantidadVendidaPorProducto.entrySet()) {
    	    if (count >= 5) {
    	        break;
    	    }

    	    Producto producto = entry.getKey();
    	    int cantidadTotal = entry.getValue();

    	    Map<String, Object> objeto = new HashMap<>();
    	    objeto.put("producto", producto.getNombreProducto());
    	    objeto.put("cantidadTotal", cantidadTotal);

    	    resultado.add(objeto);

    	    count++;
    	}

         
         return resultado;
    	
    }
    
    
    
    
    @GetMapping("/listadoRolPermisos")
    @ResponseBody
    public List<Object> listadoRolPermisos(@RequestParam() int idRol) {
        List<Permiso> rolPermisos = new ArrayList<>(rolService.get(idRol).getPermisos());
        List<Permiso> permisos = permisoService.listarPermisos();
         // Crear un arreglo genérico de objetos
        List<Object> resultado = new ArrayList<>();

        for (Permiso permiso : permisos) {
            
            if (rolPermisos.contains(permiso)) {
                Map<String, Object> objetoCoincidente = new HashMap<>();
                objetoCoincidente.put("id", permiso.getId());
                objetoCoincidente.put("nombre", permiso.getNombrePermiso());
                objetoCoincidente.put("activo", true);
                resultado.add(objetoCoincidente);
            }else{
                Map<String, Object> objetoCoincidente = new HashMap<>();
                objetoCoincidente.put("id", permiso.getId());
                objetoCoincidente.put("nombre", permiso.getNombrePermiso());
                objetoCoincidente.put("activo", false);
                resultado.add(objetoCoincidente);
            }
        }
        
        return resultado;
    }
    /*http://localhost:4403/listadoRolPermisos?idRol=1*/
    /*http://localhost:4403/actualizarRolPermisos?idRol=1&idPermiso=3&valor=1*/
    @GetMapping("/actualizarRolPermisos")
    @ResponseBody
    public Object actualizarRolPermisos(@RequestParam() int idRol,@RequestParam() int idPermiso,@RequestParam()int valor) {
    	Rol rol = rolService.get(idRol);
    	if(valor == 1) {
    		boolean permisoExistente = rol.getPermisos().contains(permisoService.get(idPermiso));
    		if (!permisoExistente) {
    		    Permiso permiso = permisoService.get(idPermiso);
    		    rol.getPermisos().add(permiso);
    		    rolService.save(rol);
    		}
    		Map<String, Object> resultado = new HashMap<>();
        	resultado.put("Success", true);
        	resultado.put("Mensaje", "Se activó correctamente");
            return resultado;
    	}else {
    		rol.getPermisos().removeIf(permiso -> permiso.getId() == idPermiso);
    		rolService.save(rol);
    		Map<String, Object> resultado = new HashMap<>();
        	resultado.put("Success", true);
        	resultado.put("Mensaje", "Se desactivó correctamente");
            return resultado;
    	}
    	
    	
    }
    
}
