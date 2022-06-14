package sv.edu.udb.www.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component
public class loginSucces extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		SessionFlashMapManager sesion = new SessionFlashMapManager();
		FlashMap mensajes = new FlashMap();
		
		mensajes.put("exito", "Hola "+authentication.getName()+" , haz iniciado sesion con exito");
		sesion.saveOutputFlashMap(mensajes, request, response);
		
		if(authentication !=null)
		{
			logger.info("El usuario '"+authentication.getName()+"' Ha inciado sesion");
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	

}
