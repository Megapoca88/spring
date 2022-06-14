package sv.edu.udb.www.models.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ICargaArchivoService {

	public Resource cargar (String nombre)  throws MalformedURLException ;
	
	public String nombreUnico (MultipartFile archivo) throws IOException;
	
	public boolean eliminar (String nombre);
}
