package com.sinfloo.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sinfloo.demo.models.Cliente;
import com.sinfloo.demo.models.NotaSalida;
import com.sinfloo.demo.models.Venta;

public interface NotaSalidaRepository extends JpaRepository<NotaSalida, Integer>{
	 // Método para buscar una NotaSalida por su id
    NotaSalida findById(int id);

    // Método para guardar una NotaSalida en la base de datos
    NotaSalida save(NotaSalida notasalida);

    // Método para buscar todas las NotaSalidas en la base de datos
    List<NotaSalida> findAll();

    // Método para buscar todas las NotaSalidas por un atributo específico (Ejemplo: nombre)
    //List<NotaSalida> findByNombre(String nombre);
    
    Optional<NotaSalida> findByVenta(Venta venta);
    
    @Modifying
    @Query("SELECT e FROM NotaSalida e ORDER BY e.id DESC")
    List<NotaSalida> obtenerUltimaNotaSalida();
  
}
