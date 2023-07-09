package com.sinfloo.demo.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sinfloo.demo.models.*;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer>{
	
	 // Método para buscar una Proveedor por su id
    Proveedor findById(int id);

    // Método para guardar una Proveedor en la base de datos
    Proveedor save(Proveedor proveedor);

    // Método para buscar todas las Proveedors en la base de datos
    List<Proveedor> findAll();

    // Método para buscar todas las Proveedors por un atributo específico (Ejemplo: nombre)
    //List<Proveedor> findByNombre(String nombre);
    
    boolean existsByDocumentoIdentidad(String documentoIdentidad);
    
    @Modifying
    @Query("select p from Proveedor p where p.documentoIdentidad like %:term%")
    List<Proveedor> listarByDocumentoIdentidad(@Param("term") String term);
    
   	Optional<Proveedor> findByDocumentoIdentidad(String documentoIdentidad);
   	
    @Modifying
    @Query("UPDATE Proveedor e SET e.activo = :activo, e.eliminado = :eliminado WHERE e.id = :id")
    void eliminar(@Param("id") Integer id, @Param("activo") Boolean activo,@Param("eliminado") Boolean eliminado);
}
