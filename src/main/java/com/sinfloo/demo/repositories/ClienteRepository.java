package com.sinfloo.demo.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sinfloo.demo.models.*;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	// Método para buscar una Cliente por su id
    Cliente findById(int id);

    // Método para guardar una Cliente en la base de datos
    Cliente save(Cliente cliente);

    // Método para buscar todas las Clientes en la base de datos
    List<Cliente> findAll();

    // Método para buscar todas las Clientes por un atributo específico (Ejemplo: nombre)
    boolean existsByDocumentoIdentidad(String documentoIdentidad);
    

	Optional<Cliente> findByDocumentoIdentidad(String documentoIdentidad);
	
    @Modifying
    @Query("select p from Cliente p where p.documentoIdentidad like %:term%")
    List<Cliente> listarByDocumentoIdentidad(@Param("term") String term);
    
}
