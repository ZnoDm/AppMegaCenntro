package com.sinfloo.demo.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.models.Cliente;
import com.sinfloo.demo.models.Producto;
import com.sinfloo.demo.repositories.ClienteRepository;


@Service
@Transactional
public class ClienteService {
	@Autowired
	ClienteRepository clienteRepository;

	public void save(Cliente cliente) {
		clienteRepository.save(cliente);
	}
	
	public List<Cliente> listarClientes(){
		return clienteRepository.findAll();
	}
	public List<Cliente> listarByDocumentoIdentidad(String term){
		return clienteRepository.listarByDocumentoIdentidad(term);
	}
	
	public boolean existsById(Integer id) {
		return clienteRepository.existsById(id);
	}
	public Cliente get(Integer  id) {
        return clienteRepository.findById(id).get();
    }
	public void eliminar(Integer id, Boolean eliminado) {
		clienteRepository.eliminar(id, eliminado);
    }
	
	
}
