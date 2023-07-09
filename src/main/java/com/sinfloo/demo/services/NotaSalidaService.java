package com.sinfloo.demo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.models.NotaSalida;
import com.sinfloo.demo.models.Rol;
import com.sinfloo.demo.models.Trabajador;
import com.sinfloo.demo.models.Venta;
import com.sinfloo.demo.repositories.NotaSalidaRepository;


@Service
@Transactional
public class NotaSalidaService {
	@Autowired
	NotaSalidaRepository notasalidaRepository;

	public void save(NotaSalida notasalida) {
		notasalidaRepository.save(notasalida);
	}
	
	public List<NotaSalida> listarNotaSalidas(){
		return notasalidaRepository.findAll();
	}
	public boolean existsById(Integer id) {
		return notasalidaRepository.existsById(id);
	}
	
	public NotaSalida get(Integer  id) {
        return notasalidaRepository.findById(id).get();
    }
	public Optional<NotaSalida> getByVenta(Venta venta){
		return notasalidaRepository.findByVenta(venta);
	}
	
	public void delete (Integer id) {
		NotaSalida notasalida = notasalidaRepository.findById(id).orElse(null);
        if (notasalida != null) {
        	notasalidaRepository.delete(notasalida);
        }
	}
	
	
	
}
