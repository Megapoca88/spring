package sv.edu.udb.www;
  

import java.util.Locale;

 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver; 
//import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
//configuraciones del proyecrto
public class Config implements WebMvcConfigurer{

public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/error_404").setViewName("error_404");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean(name = DispatcherServlet.LOCALE_RESOLVER_BEAN_NAME)
	public LocaleResolver localResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
	    localeResolver.setDefaultLocale(Locale.getDefault());
	    return localeResolver;
	  }

	  @Bean
	  public LocaleChangeInterceptor interceptor() {
	    LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
	    localeInterceptor.setIgnoreInvalidLocale(true);
	    localeInterceptor.setParamName("idioma");
	    return localeInterceptor;
	  }

	@Override
	public void addInterceptors(InterceptorRegistry registry) { 
		registry.addInterceptor(interceptor());
	}
	
	

}
