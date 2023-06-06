package com.sinfloo.demo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.models.Venta;
import com.sinfloo.demo.models.Rol;
import com.sinfloo.demo.models.Trabajador;
import com.sinfloo.demo.repositories.VentaRepository;


@Service
@Transactional
public class VentaService {
	@Autowired
	VentaRepository ventaRepository;

	public void save(Venta venta) {
		ventaRepository.save(venta);
	}
	
	public List<Venta> listarVentas(){
		return ventaRepository.findAll();
	}
	public boolean existsById(Integer id) {
		return ventaRepository.existsById(id);
	}
	
	public Venta get(Integer  id) {
        return ventaRepository.findById(id).get();
    }
	public void eliminar(Integer id, Boolean activo, Boolean eliminado) {
		ventaRepository.eliminar(id, activo,eliminado);
    }
	public void delete (Integer id) {
		Venta venta = ventaRepository.findById(id).orElse(null);
        if (venta != null) {
        	ventaRepository.delete(venta);
        }
	}
	
	
	
}
