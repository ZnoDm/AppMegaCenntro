package com.sinfloo.demo.controllers;

import com.sinfloo.demo.models.Rol;
import com.sinfloo.demo.services.RolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/rol")
public class RolController {

    @Autowired
    RolService rolService;

    @GetMapping("/listar")
    public String listarRoles(@RequestParam(defaultValue = "0") int pagina,Model model) {
    	int tamanoPagina = 2;
        List<Rol> roles = rolService.listarRoles();
        int totalRoles = roles.size();
        int desde = pagina * tamanoPagina;
        int hasta = Math.min(desde + tamanoPagina, totalRoles);
        List<Rol> rolesPaginados = roles.subList(desde, hasta);
        Page<Rol> paginaRoles = new PageImpl<Rol>(rolesPaginados, PageRequest.of(pagina, tamanoPagina), totalRoles);
        model.addAttribute("roles", paginaRoles);
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", (totalRoles + tamanoPagina - 1) / tamanoPagina);

        return "/admin/rol/listar";
    }

}
