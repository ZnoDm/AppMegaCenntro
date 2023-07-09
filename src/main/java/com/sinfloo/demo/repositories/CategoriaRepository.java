package com.sinfloo.demo.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sinfloo.demo.models.*;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
	
	 // Método para buscar una Categoria por su id
    Categoria findById(int id);

    // Método para guardar una Categoria en la base de datos
    Categoria save(Categoria categoria);

    // Método para buscar todas las Categorias en la base de datos
    List<Categoria> findAll();

    // Método para buscar todas las Categorias por un atributo específico (Ejemplo: nombre)
    //List<Categoria> findByNombre(String nombre);
    
   
    
    Optional<Categoria> findByNombreCategoria(String nombreCategoria);
    
    boolean existsByNombreCategoria(String nombreCategoria);
}
