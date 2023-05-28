package com.sinfloo.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinfloo.demo.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	 // Método para buscar una Cliente por su id
  Cliente findById(int id);

  // Método para guardar una Cliente en la base de datos
  Cliente save(Cliente producto);

  // Método para buscar todas las Clientes en la base de datos
  List<Cliente> findAll();

  // Método para buscar todas las Clientes por un atributo específico (Ejemplo: nombre)
  //List<Cliente> findByNombre(String nombre);
}
