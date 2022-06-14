package sv.edu.udb.www.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date; 
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty; 

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String descripcion;
	
	private String observacion;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	// lazy trae los datos cuando se le llama
	//many to one es para decir que un cliente tiene muchas facturas
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	//aca se tiene la relacion de detallefactura con factura
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_factura")//mira cual es la llave foranea en la tabla detallefactura
	//esto se hace por que es unidireccional
	public List<DetalleFactura> detalles;

	//para crear la fecha
	@PrePersist
	public void asignarFecha() {
		fecha = new Date();
	}

	public Factura() {
		this.detalles = new ArrayList<DetalleFactura>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<DetalleFactura> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleFactura> detalles) {
		this.detalles = detalles;
	}
	//METODO ADD
	public void agregarFactura(DetalleFactura detalle) {
		this.detalles.add(detalle);
	}
	//para calcular el total
	public Double getTotal() {
		Double total =0.0;
		
		int tamano = detalles.size();
		
		for (int i = 0; i < tamano; i++) {
			total +=detalles.get(i).calcularImporte();
			
		}
		return total;
	}
	
	

}
