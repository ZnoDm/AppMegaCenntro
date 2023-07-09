package com.sinfloo.demo.models;
import java.util.Date;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@NotEmpty
	private String nombreUsuario;
	
	@NotNull
	@NotEmpty
	private String password;
	
	@ManyToMany(fetch =  FetchType.EAGER)
	@JoinTable(name = "usuario_rol", 
				joinColumns = @JoinColumn(name= "usuario_id"),
				inverseJoinColumns = @JoinColumn(name= "rol_id") )
	private Set<Rol> roles = new HashSet<>();

	
	@OneToOne(mappedBy = "usuario")
    private Trabajador trabajador;
	
	
	public Usuario() {
	}

	public Usuario(String nombreUsuario, String password) {
		this.nombreUsuario = nombreUsuario;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
	
	
	
	
}
