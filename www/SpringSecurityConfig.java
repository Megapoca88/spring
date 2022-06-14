package sv.edu.udb.www;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; 
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 

import sv.edu.udb.www.auth.handler.loginSucces;
import sv.edu.udb.www.models.service.JpaUserDetailsService;
 
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public BCryptPasswordEncoder contrasenas(){	
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private loginSucces lS;
	//rutas de acceso
	
	@Autowired
	private JpaUserDetailsService usuarioService;
	
	@Autowired
	private BCryptPasswordEncoder password;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 
		 http.authorizeRequests().antMatchers("/","/css/**","/js/**","/images/**","/listar","/locale","/vendor/**").permitAll() 

		 .anyRequest() .authenticated()
		 .and()
		 .formLogin()
		 .successHandler(lS)
		 .loginPage("/sistema/login")
		 .permitAll()
		 .and()
		 .logout().permitAll()
		 .and()
		 .exceptionHandling().
		 accessDeniedPage("/error_404"); 
	}




	@Autowired
	public void configuracionGobal(AuthenticationManagerBuilder builder) throws Exception
	{
		builder.userDetailsService(usuarioService)
		.passwordEncoder(password);
	}
	
}
