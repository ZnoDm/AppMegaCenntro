package com.sinfloo.demo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinfloo.demo.models.NotaEntrada;
import com.sinfloo.demo.repositories.NotaEntradaRepository;


@Service
@Transactional
public class NotaEntradaService {
	@Autowired
	NotaEntradaRepository notaentradaRepository;

	public void save(NotaEntrada notaentrada) {
		notaentradaRepository.save(notaentrada);
	}
	
	public List<NotaEntrada> listarNotaEntradas(){
		return notaentradaRepository.findAll();
	}
	public boolean existsById(Integer id) {
		return notaentradaRepository.existsById(id);
	}
	
	public NotaEntrada get(Integer  id) {
        return notaentradaRepository.findById(id).get();
    }
	
	public void delete (Integer id) {
		NotaEntrada notaentrada = notaentradaRepository.findById(id).orElse(null);
        if (notaentrada != null) {
        	notaentradaRepository.delete(notaentrada);
        }
	}
	
	
	
}
