package com.sinfloo.demo.config;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.enums.Genero;
import com.sinfloo.demo.enums.PermisoNombre;
import com.sinfloo.demo.enums.RolNombre;
import com.sinfloo.demo.enums.TipoDocumento;
import com.sinfloo.demo.models.*;
import com.sinfloo.demo.services.*;

@Service
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;
    
    @Autowired
    PermisoService permisoService;
    
    @Autowired
    TrabajadorService trabajadorService;
    
    @Autowired
    CategoriaService categoriaService;
    
    @Autowired
    ClienteService clienteService; 
    
    @Autowired
    ProductoService productoService;
    
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
    	
    	Rol rolAdmin1 = new Rol(RolNombre.ROLE_ADMIN.toString());
        Rol rolUser1 = new Rol(RolNombre.ROLE_USER.toString());
        rolService.save(rolAdmin1);
        rolService.save(rolUser1);
         
    	Permiso permiso_eliminar = new Permiso(PermisoNombre.PERMISO_ELIMINAR.toString());
        Permiso permiso_crear = new Permiso(PermisoNombre.PERMISO_CREAR.toString());
        

        permisoService.save(permiso_eliminar);
        permisoService.save(permiso_crear);
        
        Set<Permiso> permisos = new HashSet<>();
        permisos.add(permiso_eliminar);
        permisos.add(permiso_crear);
    	
       
        rolAdmin1.setPermisos(permisos);
        rolUser1.setPermisos(permisos);
        rolService.save(rolAdmin1);
        rolService.save(rolUser1);
        
        
        Usuario usuario = new Usuario();
        String passwordEncoded = passwordEncoder.encode("123");
        usuario.setNombreUsuario("admin");
        usuario.setPassword(passwordEncoded);
        Rol rolAdmin = rolService.getByNombreRol(RolNombre.ROLE_ADMIN.toString()).get();
        Set<Rol> roles = new HashSet<>();
        roles.add(rolAdmin);
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        
        Categoria categoria = new Categoria();
        categoria.setNombreCategoria("Polos");
        categoria.setDescripcionCategoria("Polos");
        categoria.setActivo(true);
        categoria.setEliminado(false);
        categoriaService.save(categoria);
        
        Categoria categoria2 = new Categoria();
        categoria2.setNombreCategoria("Perfumes");
        categoria2.setDescripcionCategoria("Perfumes");
        categoria2.setActivo(true);
        categoria2.setEliminado(false);
        categoriaService.save(categoria2);
        
        
        
       
        
        Trabajador trabajador = new Trabajador();
        trabajador.setNombres("Nelson");
        trabajador.setApellidos("Angeles");
        trabajador.setDireccion("El Recreo 230");
        trabajador.setTipoDocumentoIdentidad(TipoDocumento.DNI);
        trabajador.setDocumentoIdentidad("70671747");
        trabajador.setEmail("n.aangelesp@gmail.com");
        trabajador.setGenero(Genero.MASCULINO);
        trabajador.setActivo(true);
        trabajador.setEliminado(false);
        trabajador.setUsuario(usuario);
        trabajadorService.save(trabajador);
        
       Cliente cliente = new Cliente();
       cliente.setNombres("Nelson");
       cliente.setTipoDocumentoIdentidad(TipoDocumento.DNI);
       cliente.setDocumentoIdentidad("70671747");
        clienteService.save(cliente);
        
        
        Producto producto = new Producto();       
        producto.setNombreProducto("Polo A");
        producto.setDescripcionProducto("Polo A");
        producto.setCodigo("P-001");
        producto.setMarca("NIKE");
        producto.setModelo("A");
        producto.setStock(130);
        producto.setPrecioUnitario(129.99);
        producto.setUrlImagen("https://firebasestorage.googleapis.com/v0/b/nelsontest-e2954.appspot.com/o/megacentro%2Fproductos%2FpoloA.jpg?alt=media&token=ab0a6040-cea7-4007-b624-0740a81f19ce&_gl=1*dppk99*_ga*NTU4NzEyNTUuMTY4MzM5NzU2Mw..*_ga_CW55HF8NVT*MTY4NjAwNTA4My4xOC4xLjE2ODYwMDU1NTAuMC4wLjA.");
        producto.setCategoria(categoria);
        producto.setActivo(true);
        producto.setEliminado(false);
        productoService.save(producto);
        
        Producto producto2 = new Producto();
        producto2.setNombreProducto("CCORI");
        producto2.setDescripcionProducto("CCORI");
        producto2.setCodigo("P-002");
        producto2.setMarca("UNIQUE");
        producto2.setModelo("LE PERFUME");
        producto2.setStock(100);
        producto2.setPrecioUnitario(99.99);
        producto2.setUrlImagen("https://firebasestorage.googleapis.com/v0/b/nelsontest-e2954.appspot.com/o/megacentro%2Fproductos%2Fperfume-ccori.jpg?alt=media&token=3e4b3b89-8c0b-4eae-8845-ada54b605686&_gl=1*1kf8wpt*_ga*NTU4NzEyNTUuMTY4MzM5NzU2Mw..*_ga_CW55HF8NVT*MTY4NjAwNTA4My4xOC4xLjE2ODYwMDU2MjUuMC4wLjA.");
        producto2.setCategoria(categoria2);
        producto2.setActivo(true);
        producto2.setEliminado(false);
        productoService.save(producto2);
    }
}
