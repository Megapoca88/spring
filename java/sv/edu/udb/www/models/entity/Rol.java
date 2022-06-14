package sv.edu.udb.www.models.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="roles", uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","rol"})})
public class Rol implements Serializable{
 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	private String rol;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
	
}
