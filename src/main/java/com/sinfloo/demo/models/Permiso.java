package com.sinfloo.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.sinfloo.demo.enums.PermisoNombre;
@Entity
public class Permiso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(unique =true)
	private PermisoNombre nombrePermiso;

	public Permiso() {
	}

	public Permiso(PermisoNombre nombrePermiso) {
		this.nombrePermiso = nombrePermiso;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PermisoNombre getNombrePermiso() {
		return nombrePermiso;
	}

	public void setNombrePermiso(PermisoNombre nombrePermiso) {
		this.nombrePermiso = nombrePermiso;
	}

}
