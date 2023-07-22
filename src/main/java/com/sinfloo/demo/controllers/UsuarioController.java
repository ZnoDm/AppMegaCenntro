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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    PasswordEncoder passwordEncoder;
    
        
    private String edit_template ="admin/usuario/editar";
    private String add_template ="admin/usuario/nuevo";
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
        	model.addAttribute("mensajeError","Llene todos los campos");
        	model.addAttribute("roles",roles);
            return add_template;
        }
        if(usuarioService.existsByNombreUsuario(usuarioform.getNombreUsuario())) {
        	List<Rol> roles = rolService.listarRoles();
        	model.addAttribute("mensajeError","El Nombre de usuario ya existe");
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
        return list_redirect+ "?mensajeAlert=Usuario creado.";
    }
	
	 
	@GetMapping("/edit/{id}")
    public String editUsuario(@PathVariable("id") Integer id, Model model){
		Usuario usuario = usuarioService.get(id);
		
		
        UsuarioForm usuarioform =  new UsuarioForm();
        usuarioform.setId(usuario.getId());
        usuarioform.setNombreUsuario(usuario.getNombreUsuario());
        usuarioform.setRol(usuario.getRoles().iterator().next().getNombreRol());
        model.addAttribute("usuarioform", usuarioform);
        List<Rol> roles = rolService.listarRoles();
    	model.addAttribute("roles",roles);
        return edit_template;
    }
		
	@PostMapping("/editar")
    public String editarUsuario(@Valid @ModelAttribute("usuarioform") UsuarioForm usuarioform, 
    		BindingResult result, Model model){
		if(result.hasErrors() && usuarioform.getPassword() == null){
			List<Rol> roles = rolService.listarRoles();
			model.addAttribute("roles",roles);
	        model.addAttribute("mensajeError","Llene todos los campos");
	       return edit_template;
        }
        
	        
        Usuario usuario = usuarioService.get(usuarioform.getId());
        if(usuarioService.existsByNombreUsuario(usuarioform.getNombreUsuario())) {
        System.out.println(usuarioService.getByNombreUsuario(usuarioform.getNombreUsuario()).get().getId());
        if(usuarioService.getByNombreUsuario(usuarioform.getNombreUsuario()).get().getId() != usuarioform.getId()) {
        	model.addAttribute("mensajeError","El Usuario ya existe");
        	List<Rol> roles = rolService.listarRoles();
			model.addAttribute("roles",roles);
			return edit_template;
        }
        }
        
        
	     
        
        usuario.getRoles().clear();
        
        Rol rol= rolService.getByNombreRol(usuarioform.getRol()).get();
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        usuario.setRoles(roles);
        usuario.setNombreUsuario(usuarioform.getNombreUsuario());
        usuarioService.save(usuario);
        
        return list_redirect+ "?mensajeAlert=Usuario actualizado.";
    }
	
	
	@GetMapping("/delete/{id}")
    public String deleteUsuario(@PathVariable("id") Integer id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
	     // Verificar si el usuario está autenticado
	     if (authentication != null && authentication.isAuthenticated()) {
	         // Obtener el nombre de usuario
	         String username = authentication.getName();
	         
	         // Hacer algo con el nombre de usuario
	         System.out.println("Usuario autenticado: " + username);
	         System.out.println(usuarioService.getByIdUsuario(id).get().getNombreUsuario());
	         if(usuarioService.getByIdUsuario(id).get().getNombreUsuario().equals(username)){
	         	List<Rol> roles = rolService.listarRoles();
	 			model.addAttribute("roles",roles);
	 			return list_redirect+ "?mensajeError=No te puede eliminar a ti mismo, estas logeado."; 
	         }
	     }
	     usuarioService.delete(id);
	     return list_redirect+ "?mensajeAlert=Usuario eliminado.";
    }
    
    @GetMapping("/listar")
    public String listarUsuarios(@RequestParam(defaultValue = "0") int pagina,Model model,
    		@RequestParam(required = false) String mensajeAlert,
    		@RequestParam(required = false) String mensajeError) {
    	int tamanoPagina = 5;
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        int totalUsuarios = usuarios.size();
        int desde = pagina * tamanoPagina;
        int hasta = Math.min(desde + tamanoPagina, totalUsuarios);
        List<Usuario> usuariosPaginados = usuarios.subList(desde, hasta);
        Page<Usuario> paginaUsuarios = new PageImpl<Usuario>(usuariosPaginados, PageRequest.of(pagina, tamanoPagina), totalUsuarios);
        model.addAttribute("usuarios", paginaUsuarios);
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", (totalUsuarios + tamanoPagina - 1) / tamanoPagina);
        model.addAttribute("mensajeAlert",mensajeAlert);
        model.addAttribute("mensajeError",mensajeError);
        return "admin/usuario/listar";
    }
    
    @GetMapping("/registro")
    public String registro(){
        return "login/registro";
    }

    @PostMapping("/registrar")
    public ModelAndView registrar(String nombreUsuario, String password){
        ModelAndView mv = new ModelAndView();
        if(StringUtils.isBlank(nombreUsuario)){
            mv.setViewName("login/registro");
            mv.addObject("error", "el nombre no puede estar vacío");
            return mv;
        }
        if(StringUtils.isBlank(password)){
            mv.setViewName("login/registro");
            mv.addObject("error", "la contraseña no puede estar vacía");
            return mv;
        }
        if(usuarioService.existsByNombreUsuario(nombreUsuario)){
            mv.setViewName("login/registro");
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
        mv.setViewName("login/login");
        mv.addObject("registroOK", "Cuenta creada, " + usuario.getNombreUsuario() + ", ya puedes iniciar sesión");
        return mv;
    }



}
