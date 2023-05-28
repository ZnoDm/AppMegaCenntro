package com.sinfloo.demo.controllers;

import com.sinfloo.demo.models.Rol;
import com.sinfloo.demo.models.Usuario;
import com.sinfloo.demo.models.UsuarioForm;
import com.sinfloo.demo.enums.RolNombre;
import com.sinfloo.demo.services.RolService;
import com.sinfloo.demo.services.UsuarioService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    PasswordEncoder passwordEncoder;
    
        
    private String edit_template ="/admin/usuario/editar";
    private String add_template ="/admin/usuario/nuevo";
    private String list_template ="/admin/usuario/listar";
    private String list_redirect ="redirect:/usuario/listar";

    @GetMapping("/add")
    public String addUsuario(UsuarioForm usuarioform, Model model){
    	List<Rol> roles = rolService.listarRoles();
    	model.addAttribute("roles",roles);
        model.addAttribute("usuarioform",usuarioform);

        return add_template;
    }
	
	@PostMapping("/save")
    public String saveUsuario(@Valid @ModelAttribute("usuarioform") UsuarioForm usuarioform, 
    		BindingResult result, Model model){

        if(result.hasErrors()){
        	List<Rol> roles = rolService.listarRoles();
        	model.addAttribute("roles",roles);
            return add_template;
        }
        if(!rolService.existsByNombreRol(usuarioform.getRol())) {
        	List<Rol> roles = rolService.listarRoles();
        	model.addAttribute("roles",roles);
            return add_template;
        }
        
        Usuario usuario = new Usuario();
        String passwordEncoded = passwordEncoder.encode(usuarioform.getPassword());
        Rol rolAdmin = rolService.getByNombreRol(usuarioform.getRol()).get();
        Set<Rol> roles = new HashSet<>();
        roles.add(rolAdmin);
        usuario.setRoles(roles);
        usuario.setNombreUsuario(usuarioform.getNombreUsuario());
        usuario.setPassword(passwordEncoded);
        usuarioService.save(usuario);
        return list_redirect;
    }
	
	 
	@GetMapping("/edit/{id}")
    public String editUsuario(@PathVariable("id") Integer id, Model model){
		Usuario usuario = new Usuario();
		usuario.setId(id);
		
        UsuarioForm usuarioform =  new UsuarioForm();
        model.addAttribute("usuarioform", usuarioform);
        List<Rol> roles = rolService.listarRoles();
    	model.addAttribute("roles",roles);
        return edit_template;
    }
		
	@PostMapping("/editar")
    public String editarUsuario(@Valid @ModelAttribute("usuarioform") UsuarioForm usuarioform, 
    		BindingResult result, Model model){

        if(result.hasErrors()){
        	List<Rol> roles = rolService.listarRoles();
        	model.addAttribute("roles",roles);
            return edit_template;
        }
        if(usuarioService.existsByNombreUsuario(usuarioform.getNombreUsuario())) {
        	List<Rol> roles = rolService.listarRoles();
        	model.addAttribute("roles",roles);
        	 return edit_template;
        }
        System.out.println(result.hasErrors());
        
        Usuario usuario = usuarioService.get(usuarioform.getId());
        usuario.getRoles().clear();
        
        String passwordEncoded = passwordEncoder.encode(usuarioform.getPassword());
        Rol rolAdmin = rolService.getByNombreRol(usuarioform.getRol()).get();
        Set<Rol> roles = new HashSet<>();
        roles.add(rolAdmin);
        usuario.setRoles(roles);
        usuario.setNombreUsuario(usuarioform.getNombreUsuario());
        usuario.setPassword(passwordEncoded);
        usuarioService.save(usuario);
        
        return list_redirect;
    }
	
	
	@GetMapping("/delete/{id}")
    public String deleteUsuario(@PathVariable("id") Integer id, Model model) {
       return list_redirect;
    }
    
    @GetMapping("/listar")
    public String listarUsuarios(@RequestParam(defaultValue = "0") int pagina,Model model) {
    	int tamanoPagina = 2;
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        int totalUsuarios = usuarios.size();
        int desde = pagina * tamanoPagina;
        int hasta = Math.min(desde + tamanoPagina, totalUsuarios);
        List<Usuario> usuariosPaginados = usuarios.subList(desde, hasta);
        Page<Usuario> paginaUsuarios = new PageImpl<Usuario>(usuariosPaginados, PageRequest.of(pagina, tamanoPagina), totalUsuarios);
        model.addAttribute("usuarios", paginaUsuarios);
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", (totalUsuarios + tamanoPagina - 1) / tamanoPagina);

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
        Rol rolUser = rolService.getByNombreRol(RolNombre.ROLE_USER.toString()).get();
        Set<Rol> roles = new HashSet<>();
        roles.add(rolUser);
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        mv.setViewName("/login/login");
        mv.addObject("registroOK", "Cuenta creada, " + usuario.getNombreUsuario() + ", ya puedes iniciar sesión");
        return mv;
    }



}
