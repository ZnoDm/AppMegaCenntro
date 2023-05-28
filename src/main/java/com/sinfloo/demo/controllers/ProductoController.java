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

import com.sinfloo.demo.models.Producto;
import com.sinfloo.demo.services.ProductoService;



@Controller
@RequestMapping("/producto")
public class ProductoController {
	
	@Autowired
	ProductoService productoService;
	
	private String edit_template ="/admin/producto/editar";
	private String add_template ="/admin/producto/nuevo";
    private String list_template ="/admin/producto/listar";
    private String list_redirect ="redirect:/producto/listar";
	    
	@GetMapping("/add")
    public String addProducto(Producto producto, Model model){
		
        List<Producto> productos = productoService.listarProductos();
        model.addAttribute("productos",productos);

        return add_template;
    }


    @GetMapping("/edit/{id}")
    public String editProducto(@PathVariable("id") Integer id, Model model){
        Producto producto = productoService.get(id);
        model.addAttribute("producto", producto);

        return edit_template;
    }
    @PostMapping("/save")
    public String saveProducto(@Valid @ModelAttribute("producto") Producto producto, 
    		BindingResult result, Model model){
    	
    	System.out.println(producto.getActivo());

        if(result.hasErrors()){
            return add_template;
        }
        System.out.println(result.hasErrors());
        productoService.save(producto);
        return list_redirect;
    }



    @GetMapping("/delete/{id}")
    public String deleteProducto(@PathVariable("id") Integer id, Model model) {
        productoService.eliminar(id,false);
    
        return list_redirect;
    }

    @GetMapping("/listar")
    public String listProducto(@RequestParam(defaultValue = "0") int pagina,Model model) {
    	int tamanoPagina = 5;
    	List<Producto> listaProductos = productoService.listarProductos();
        int totalProductos = listaProductos.size();
        int desde = pagina * tamanoPagina;
        int hasta = Math.min(desde + tamanoPagina, totalProductos);
        List<Producto> rolesPaginados = listaProductos.subList(desde, hasta);
        Page<Producto> paginaProductos = new PageImpl<Producto>(rolesPaginados, PageRequest.of(pagina, tamanoPagina), totalProductos);
        model.addAttribute("productos", paginaProductos);
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", (totalProductos + tamanoPagina - 1) / tamanoPagina);
        

        return list_template;
    }
    
}
