package com.sinfloo.demo.models;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.stereotype.Component;
@Component
public class UsuarioForm extends Usuario{

    private String rol;

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
    // Getters y setters
}