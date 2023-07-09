package com.sinfloo.demo.models;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.sinfloo.demo.enums.EstadoVenta;

@Entity
public class NotaEntrada {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    private String codigo;
    private int correlativo;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trabajador_id")
    private Trabajador trabajador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;
    
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "notaentrada_id")
	private List<DetalleNotaEntrada> items;
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fechaRecepcion;

    private String tipoDocumento;
    
    @Enumerated(EnumType.STRING)
    private EstadoVenta estado;


	private String usuarioRegistro;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fechaRegistro;
	
	private String usuarioModificacion;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fechaModificacion;

	private String usuarioEliminacion;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fechaEliminacion;
	
    public NotaEntrada() {
    	this.items = new ArrayList<DetalleNotaEntrada>();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public int getCorrelativo() {
        return correlativo;
    }
    public void setCorrelativo(int correlativo) {
        this.correlativo = correlativo;
    }
    public Trabajador getTrabajador() {
        return trabajador;
    }
    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }
  
    public String getTipoDocumento() {
        return tipoDocumento;
    }
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
   
    public EstadoVenta getEstado() {
        return estado;
    }
    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
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
    
	public List<DetalleNotaEntrada> getItems() {
		return items;
	}
	public void setItems(List<DetalleNotaEntrada> items) {
		this.items = items;
	}
	
	public void addItemDetalleNotaEntrada(DetalleNotaEntrada item) {
		this.items.add(item);
	}
    public Proveedor getProveedor() {
        return proveedor;
    }
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }
    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }
	

}
