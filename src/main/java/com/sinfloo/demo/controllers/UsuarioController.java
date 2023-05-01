package com.sinfloo.demo.controllers;

import com.sinfloo.demo.models.Rol;
import com.sinfloo.demo.models.Usuario;
import com.sinfloo.demo.enums.RolNombre;
import com.sinfloo.demo.services.RolService;
import com.sinfloo.demo.services.UsuarioService;

import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    
    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        model.addAttribute("usuarios", usuarios);

        return "/admin/usuario/listar";
    }
    
    @GetMapping("/registro")
    public String registro(){
        return "login/registro";
    }

    @PostMapping("/registrar")
    public ModelAndView registrar(String nombreUsuario, String password){
        ModelAndView mv = new ModelAndView();
        if(StringUtils.isBlank(nombreUsuario)){
            mv.setViewName("/login/registro");
            mv.addObject("error", "el nombre no puede estar vacío");
            return mv;
        }
        if(StringUtils.isBlank(password)){
            mv.setViewName("/login/registro");
            mv.addObject("error", "la contraseña no puede estar vacía");
            return mv;
        }
        if(usuarioService.existsByNombreUsuario(nombreUsuario)){
            mv.setViewName("/login/registro");
            mv.addObject("error", "ese nombre de usuario ya existe");
            return mv;
        }
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setPassword(passwordEncoder.encode(password));
        Rol rolUser = rolService.getByNombreRol(RolNombre.ROLE_USER).get();
        Set<Rol> roles = new HashSet<>();
        roles.add(rolUser);
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        mv.setViewName("/login/login");
        mv.addObject("registroOK", "Cuenta creada, " + usuario.getNombreUsuario() + ", ya puedes iniciar sesión");
        return mv;
    }



}
