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
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinfloo.demo.models.Categoria;
import com.sinfloo.demo.models.Producto;
import com.sinfloo.demo.services.CategoriaService;
import com.sinfloo.demo.services.ProductoService;



@Controller
@RequestMapping("producto")
public class ProductoController {
	
	@Autowired
	ProductoService productoService;
	
	@Autowired
	CategoriaService categoriaService;
	
	private String edit_template ="admin/producto/editar";
	private String add_template ="admin/producto/nuevo";
    private String list_template ="admin/producto/listar";
    private String list_redirect ="redirect:/producto/listar";
	    
	@GetMapping("/add")
    public String addProducto(Producto producto, Model model){
		
        producto.setUrlImagen("https://firebasestorage.googleapis.com/v0/b/nelsontest-e2954.appspot.com/o/megacentro%2Fsin-imagen.webp?alt=media&token=c2f898b5-6aa0-4a2d-b6b2-155826879a84");
        model.addAttribute("producto",producto);
        List<Categoria> categorias = categoriaService.listarCategorias();
        model.addAttribute("categorias",categorias);
        return add_template;
    }


    @GetMapping("/edit/{id}")
    public String editProducto(@PathVariable("id") Integer id, Model model){
        Producto producto = productoService.get(id);
        List<Categoria> categorias = categoriaService.listarCategorias();
        model.addAttribute("categorias",categorias);
        model.addAttribute("producto", producto);

        return edit_template;
    }
    @PostMapping("/save")
    public String saveProducto(@Valid @ModelAttribute("producto") Producto producto, 
    		BindingResult result, Model model){

        if(result.hasErrors()){
        	 List<Categoria> categorias = categoriaService.listarCategorias();
             model.addAttribute("categorias",categorias);
             model.addAttribute("mensajeError","Llene todos los campos");
            return add_template;
        }
        
        if(productoService.existsByNombreProducto(producto.getNombreProducto())) {
        	 List<Categoria> categorias = categoriaService.listarCategorias();
             model.addAttribute("categorias",categorias);
             model.addAttribute("mensajeError","El producto ya existe");
             return edit_template;
        }
        
        System.out.println(result.hasErrors());
        productoService.save(producto);
        String mensajeAlert = "";
        mensajeAlert = "?mensajeAlert=Producto registrado.";
        return list_redirect + mensajeAlert;
    }
    
    @PostMapping("/edit")
    public String editProducto(@Valid @ModelAttribute("producto") Producto producto, 
    		BindingResult result, Model model){

        if(result.hasErrors()){
        	 List<Categoria> categorias = categoriaService.listarCategorias();
             model.addAttribute("categorias",categorias);
             model.addAttribute("mensajeError","Llene todos los campos");
            return edit_template;
        }
        if(productoService.existsByNombreProducto(producto.getNombreProducto())) {
	        if(productoService.getByNombreProducto(producto.getNombreProducto()).get().getId() != producto.getId() ) {
	    		model.addAttribute("mensajeError","El producto ya existe");
	    		List<Categoria> categorias = categoriaService.listarCategorias();
	    		model.addAttribute("categorias",categorias);
	        	return edit_template;
	        }}
        
        System.out.println(result.hasErrors());
        productoService.save(producto);
        String mensajeAlert = "";
        mensajeAlert = "?mensajeAlert=Producto actualizado.";
        
        return list_redirect + mensajeAlert;
    }

    @GetMapping("/enabledDisabled/{id}/{estado}")
    public String enabledDisabledProducto(@PathVariable("id") Integer id,
    		@PathVariable("estado") Boolean estado, Model model) {
    	String mensajeAlert = "";
    	if(estado == true) {
            productoService.eliminar(id,true,false);
            mensajeAlert = "?mensajeAlert=Producto habilitado.";
    	}
    	else {
    		productoService.eliminar(id,false,true);
            mensajeAlert = "?mensajeAlert=Producto deshabilitado.";
    	}
    		
        return list_redirect+ mensajeAlert;
    }

    @GetMapping("/delete/{id}")
    public String deleteProducto(@PathVariable("id") Integer id, Model model) {
        productoService.delete(id);
    
        return list_redirect+ "?mensajeAlert=Producto eliminado.";
    }

    @GetMapping("/listar")
    public String listProducto(@RequestParam(defaultValue = "0") int pagina,Model model,
    		@RequestParam(required = false) String mensajeAlert,
    		@RequestParam(required = false) String mensajeError) {
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
        model.addAttribute("mensajeAlert",mensajeAlert);
        model.addAttribute("mensajeError",mensajeError);

        return list_template;
    }
    
    @GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
    @ResponseBody 
	public List<Producto> cargarProductos(@PathVariable String term) {
		return productoService.listarByNombreProducto(term);
	}
    
}
