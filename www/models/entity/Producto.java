package sv.edu.udb.www.models.entity;

import java.io.Serializable; 

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; 
import javax.persistence.ManyToOne; 
import javax.persistence.Table; 
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
 
@Entity
@Table(name="productos")
public class Producto implements Serializable{ 
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String marca;
	
	@NotEmpty
	private String descripcion; 
	
	@NotNull
	@Min(1)
	@Max(5000)
	private Double precio; 
	
	@NotNull
	@Min(0)
	@Max(5000)
	private int cantidad;
	
	@JsonIgnore 
	@ManyToOne(fetch = FetchType.LAZY) 
	private Categoria categoria;
	
	@JsonIgnore 
	@ManyToOne(fetch = FetchType.LAZY) 
	private Proveedor proveedor;
	
	public Long getId() {
		return id;
	} 
	public void setId(Long id) {
		this.id = id;
	} 
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
 

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Proveedor getProveedor() {
		return proveedor;
	}
	
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	} 
	
	
}
