package com.sinfloo.demo.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.sinfloo.demo.enums.RolNombre;

@Entity
public class Rol {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(unique =true)
	private RolNombre nombreRol;
	
	
	@ManyToMany(fetch =  FetchType.EAGER)
	@JoinTable(name = "rol_permiso", 
				joinColumns = @JoinColumn(name= "rol_id"),
				inverseJoinColumns = @JoinColumn(name= "permiso_id") )
	private Set<Permiso> permisos = new HashSet<>();
	
	
	
	public Rol() {
	}

	public Rol(RolNombre nombreRol) {
		this.nombreRol = nombreRol;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RolNombre getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(RolNombre nombreRol) {
		this.nombreRol = nombreRol;
	}

	public Set<Permiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(Set<Permiso> permisos) {
		this.permisos = permisos;
	}

	
	
}
