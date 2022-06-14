package sv.edu.udb.www.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sv.edu.udb.www.models.dao.IUsuarioDao;
import sv.edu.udb.www.models.entity.Rol;
import sv.edu.udb.www.models.entity.Usuario;
 
@Service("jpaUserDetailsService")
public class JpaUserDetailsService  implements UserDetailsService {

	@Autowired
	private IUsuarioDao usuarioDao;
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Usuario usuario = usuarioDao.findByUsername(username);
		
		 if(usuario==null){
			 logger.error("Error login: no existe el usuario '"+username+"'");
			 throw new UsernameNotFoundException("Usuario "+username+ " no existe en el sistema");
		 }
		 
		 List<GrantedAuthority> rol = new ArrayList<GrantedAuthority>();
		 
		 for(Rol roles: usuario.getRol()){
			 logger.info("Rol : ".concat(roles.getRol()));
			 rol.add(new SimpleGrantedAuthority(roles.getRol()));
			 
		 }
		 
		 if(rol.isEmpty()) {
			 logger.error("Error login: no existe el usuario '"+username+"' no tiene roles asignados");
			 throw new UsernameNotFoundException("Usuario "+username+ " no tiene roles asignados");
		 }
		 
		 return new User(usuario.getNombre(), usuario.getContrasena(), usuario.getActivo(), true, true, true, rol);
	}

}
 