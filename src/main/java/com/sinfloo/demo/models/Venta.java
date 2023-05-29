package com.sinfloo.demo.models;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Table(name="venta")
public class Venta {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    private String codigo;
    private int correlativo;

	@ManyToOne
    @JoinColumn(name = "trabajador_id")
    private Trabajador trabajador;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fechaEmision;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fechaEntrega;

    private String tipoDocumento;
    private String tipoMoneda;
    private String estado;
    private Float tasaCambio;

    private Float subtotal;
    private Float descuento;
    private Float total;

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
    public Venta() {
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
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Date getFechaEmision() {
        return fechaEmision;
    }
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    public Date getFechaEntrega() {
        return fechaEntrega;
    }
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    public String getTipoDocumento() {
        return tipoDocumento;
    }
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    public String getTipoMoneda() {
        return tipoMoneda;
    }
    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Float getTasaCambio() {
        return tasaCambio;
    }
    public void setTasaCambio(Float tasaCambio) {
        this.tasaCambio = tasaCambio;
    }
    public Float getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }
    public Float getDescuento() {
        return descuento;
    }
    public void setDescuento(Float descuento) {
        this.descuento = descuento;
    }
    public Float getTotal() {
        return total;
    }
    public void setTotal(Float total) {
        this.total = total;
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
