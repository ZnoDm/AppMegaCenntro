package com.sinfloo.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sinfloo.demo.models.Categoria;
import com.sinfloo.demo.models.Permiso;
import com.sinfloo.demo.models.Producto;

public interface ProductoRepostory extends JpaRepository<Producto, Integer>{
	
	 // Método para buscar una Producto por su id
   Producto findById(int id);

   // Método para guardar una Producto en la base de datos
   Producto save(Producto producto);

   // Método para buscar todas las Productos en la base de datos
   List<Producto> findAll();

   // Método para buscar todas las Productos por un atributo específico (Ejemplo: nombre)
   //List<Producto> findByNombre(String nombre);
   
   @Modifying
   @Query("UPDATE Producto e SET e.activo = :activo, e.eliminado = :eliminado WHERE e.id = :id")
   void eliminar(@Param("id") Integer id, @Param("activo") Boolean activo,@Param("eliminado") Boolean eliminado);
   
   @Modifying
   @Query("select p from Producto p where p.nombreProducto like %:term%")
   List<Producto> listarByNombreProducto(@Param("term") String term);
	
	
   Optional<Producto> findByNombreProducto(String nombreProducto);
   
   boolean existsByNombreProducto(String nombreProducto);
	// Método para guardar una Categoria en la base de datos
	
}
