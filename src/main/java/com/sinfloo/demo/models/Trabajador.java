package com.sinfloo.demo.models;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.sinfloo.demo.enums.Genero;
import com.sinfloo.demo.enums.TipoDocumento;
@Entity
@Table(name="trabajador")
public class Trabajador {
    

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@NotEmpty
	private String nombres;
	
	@NotNull
	@NotEmpty
	private String apellidos;

   
	private String direccion;


    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumentoIdentidad;

    @NotNull
	@NotEmpty
    private String documentoIdentidad;

	

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }


    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    @NotNull
	@NotEmpty
    private String email;

    private String telefono;

     @Enumerated(EnumType.STRING)
    private Genero genero;

    @OneToMany(mappedBy = "trabajador")
    private List<Venta> ventas;

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


    public Genero getGenero() {
        return genero;
    }


    public void setGenero(Genero genero) {
        this.genero = genero;
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


    public Trabajador() {
	}


    public List<Venta> getVentas() {
        return ventas;
    }


    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }


    public TipoDocumento getTipoDocumentoIdentidad() {
        return tipoDocumentoIdentidad;
    }


    public void setTipoDocumentoIdentidad(TipoDocumento tipoDocumentoIdentidad) {
        this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
    }

}
