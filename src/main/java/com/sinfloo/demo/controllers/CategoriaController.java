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

import com.sinfloo.demo.models.Categoria;
import com.sinfloo.demo.services.CategoriaService;



@Controller
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	CategoriaService categoriaService;
	
	private String edit_template ="/admin/categoria/editar";
	private String add_template ="/admin/categoria/nuevo";
    private String list_template ="/admin/categoria/listar";
    private String list_redirect ="redirect:/categoria/listar";
	    
	@GetMapping("/add")
    public String addCategoria(Categoria categoria, Model model){
		
        model.addAttribute("categoria",categoria);

        return add_template;
    }


    @GetMapping("/edit/{id}")
    public String editCategoria(@PathVariable("id") Integer id, Model model){
        Categoria categoria = categoriaService.get(id);
        model.addAttribute("categoria", categoria);

        return edit_template;
    }
    @PostMapping("/save")
    public String saveCategoria(@Valid @ModelAttribute("categoria") Categoria categoria, 
    		BindingResult result, Model model){
    	
    	System.out.println(categoria.getActivo());

        if(result.hasErrors()){
            return add_template;
        }
        System.out.println(result.hasErrors());
        categoriaService.save(categoria);
        return list_redirect;
    }



    @GetMapping("/delete/{id}")
    public String deleteCategoria(@PathVariable("id") Integer id, Model model) {
        categoriaService.eliminar(id,false);
    
        return list_redirect;
    }

    @GetMapping("/listar")
    public String listCategoria(@RequestParam(defaultValue = "0") int pagina,Model model) {
    	int tamanoPagina = 5;
    	List<Categoria> listaCategorias = categoriaService.listarCategorias();
        int totalCategorias = listaCategorias.size();
        int desde = pagina * tamanoPagina;
        int hasta = Math.min(desde + tamanoPagina, totalCategorias);
        List<Categoria> rolesPaginados = listaCategorias.subList(desde, hasta);
        Page<Categoria> paginaCategorias = new PageImpl<Categoria>(rolesPaginados, PageRequest.of(pagina, tamanoPagina), totalCategorias);
        model.addAttribute("categorias", paginaCategorias);
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", (totalCategorias + tamanoPagina - 1) / tamanoPagina);
        

        return list_template;
    }
    
}
