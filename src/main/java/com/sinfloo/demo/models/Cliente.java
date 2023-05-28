package com.sinfloo.demo.models;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	private String nombres;
	
	@NotNull
	private String apellidos;
	
	
	private String direccion;
	private String email;
	private String telefono;
    
	@NotNull
	private String tipoDocumentoIdentidad;
	@NotNull
    private String documentoIdentidad;
    
	private Boolean activo;
	private Boolean eliminado;
	
	private String usuarioRegistro;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fechaRegistro;
	
	private String usuarioModificacion;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fechaModificacion;

	private String usuarioEliminacion;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fechaEliminacion;
	
	
	
	public Cliente() {
	}



	public Cliente(int id, @NotNull String nombres, @NotNull String apellidos, String direccion, String email,
			String telefono, @NotNull String tipoDocumentoIdentidad, @NotNull String documentoIdentidad, Boolean activo,
			Boolean eliminado, String usuarioRegistro, Date fechaRegistro, String usuarioModificacion,
			Date fechaModificacion, String usuarioEliminacion, Date fechaEliminacion) {
		this.id = id;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.email = email;
		this.telefono = telefono;
		this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
		this.documentoIdentidad = documentoIdentidad;
		this.activo = activo;
		this.eliminado = eliminado;
		this.usuarioRegistro = usuarioRegistro;
		this.fechaRegistro = fechaRegistro;
		this.usuarioModificacion = usuarioModificacion;
		this.fechaModificacion = fechaModificacion;
		this.usuarioEliminacion = usuarioEliminacion;
		this.fechaEliminacion = fechaEliminacion;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getNombres() {
		return nombres;
	}



	public void setNombres(String nombres) {
		this.nombres = nombres;
	}



	public String getApellidos() {
		return apellidos;
	}



	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}



	public String getDireccion() {
		return direccion;
	}



	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getTelefono() {
		return telefono;
	}



	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	public String getTipoDocumentoIdentidad() {
		return tipoDocumentoIdentidad;
	}



	public void setTipoDocumentoIdentidad(String tipoDocumentoIdentidad) {
		this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
	}



	public String getDocumentoIdentidad() {
		return documentoIdentidad;
	}



	public void setDocumentoIdentidad(String documentoIdentidad) {
		this.documentoIdentidad = documentoIdentidad;
	}



	public Boolean getActivo() {
		return activo;
	}



	public void setActivo(Boolean activo) {
		this.activo = activo;
	}



	public Boolean getEliminado() {
		return eliminado;
	}



	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}



	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}



	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}



	public Date getFechaRegistro() {
		return fechaRegistro;
	}



	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}



	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}



	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}



	public Date getFechaModificacion() {
		return fechaModificacion;
	}



	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}



	public String getUsuarioEliminacion() {
		return usuarioEliminacion;
	}



	public void setUsuarioEliminacion(String usuarioEliminacion) {
		this.usuarioEliminacion = usuarioEliminacion;
	}



	public Date getFechaEliminacion() {
		return fechaEliminacion;
	}



	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}
	
	
	
	
}
