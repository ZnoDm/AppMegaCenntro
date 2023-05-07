package com.sinfloo.demo.models;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.sinfloo.demo.enums.PermisoNombre;
import com.sinfloo.demo.enums.RolNombre;
@Component
public class RolPermiso {
	
	@NotNull
	private int idRol;
	
	@NotNull
	private RolNombre nombreRol;
	
	@NotNull
	private int idPermiso;
	@NotNull
	private PermisoNombre nombrePermiso;

	public RolPermiso() {
	}

	public RolPermiso(@NotNull int idRol, @NotNull RolNombre nombreRol, @NotNull int idPermiso,
			@NotNull PermisoNombre nombrePermiso) {
		this.idRol = idRol;
		this.nombreRol = nombreRol;
		this.idPermiso = idPermiso;
		this.nombrePermiso = nombrePermiso;
	}

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public RolNombre getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(RolNombre nombreRol) {
		this.nombreRol = nombreRol;
	}

	public int getIdPermiso() {
		return idPermiso;
	}

	public void setIdPermiso(int idPermiso) {
		this.idPermiso = idPermiso;
	}

	public PermisoNombre getNombrePermiso() {
		return nombrePermiso;
	}

	public void setNombrePermiso(PermisoNombre nombrePermiso) {
		this.nombrePermiso = nombrePermiso;
	}

	



}
