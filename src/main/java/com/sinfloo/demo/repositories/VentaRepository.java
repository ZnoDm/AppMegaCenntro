package com.sinfloo.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sinfloo.demo.models.Venta;
import com.sinfloo.demo.models.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer>{
	 // Método para buscar una Venta por su id
    Venta findById(int id);

    // Método para guardar una Venta en la base de datos
    Venta save(Venta venta);

    // Método para buscar todas las Ventas en la base de datos
    List<Venta> findAll();

    // Método para buscar todas las Ventas por un atributo específico (Ejemplo: nombre)
    //List<Venta> findByNombre(String nombre);
    
    @Modifying
    @Query("SELECT e FROM Venta e ORDER BY e.id DESC")
    List<Venta> obtenerUltimaVenta();
  
}
