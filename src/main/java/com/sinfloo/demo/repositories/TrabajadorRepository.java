package com.sinfloo.demo.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sinfloo.demo.models.*;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer>{
	
	 // Método para buscar una Trabajador por su id
    Trabajador findById(int id);

    // Método para guardar una Trabajador en la base de datos
    Trabajador save(Trabajador trabajador);

    // Método para buscar todas las Trabajadors en la base de datos
    List<Trabajador> findAll();

    // Método para buscar todas las Trabajadors por un atributo específico (Ejemplo: nombre)
    //List<Trabajador> findByNombre(String nombre);
    
    
    @Modifying
    @Query("UPDATE Trabajador e SET e.eliminado = :eliminado WHERE e.id = :id")
    void eliminar(@Param("id") Integer id, @Param("eliminado") Boolean eliminado);
}
