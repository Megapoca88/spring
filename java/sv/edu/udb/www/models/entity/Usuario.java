package sv.edu.udb.www.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=30,unique=true)
	private String nombre;
	
	@Column(length=60)
	private String contrasena;
	 
	private Boolean activo;
	
	@OneToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="usuario_id")
	private List<Rol> rol;
	
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

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public List<Rol> getRol() {
		return rol;
	}

	public void setRol(List<Rol> rol) {
		this.rol = rol;
	}
	

	
	
}
