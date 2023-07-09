package com.sinfloo.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sinfloo.demo.models.NotaEntrada;

public interface NotaEntradaRepository extends JpaRepository<NotaEntrada, Integer>{
	 // Método para buscar una NotaEntrada por su id
    NotaEntrada findById(int id);

    // Método para guardar una NotaEntrada en la base de datos
    NotaEntrada save(NotaEntrada notaentrada);

    // Método para buscar todas las NotaEntradas en la base de datos
    List<NotaEntrada> findAll();

    // Método para buscar todas las NotaEntradas por un atributo específico (Ejemplo: nombre)
    //List<NotaEntrada> findByNombre(String nombre);
    
    @Modifying
    @Query("SELECT e FROM NotaEntrada e ORDER BY e.id DESC")
    List<NotaEntrada> obtenerUltimaNotaEntrada();
  
}
