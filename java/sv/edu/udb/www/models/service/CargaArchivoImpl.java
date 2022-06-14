package sv.edu.udb.www.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CargaArchivoImpl implements ICargaArchivoService {

	private final static String PERFIL = "perfil";

	//carga la foto
	@Override
	public Resource cargar(String nombre) throws MalformedURLException {
		Path foto = getPath(nombre);
		Resource recurso = null;

		recurso = new UrlResource(foto.toUri());
		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: nose puede cargar la imagen" + foto.toString());
		}

		return recurso;
	}

	//asigna nombre unico 
	@Override
	public String nombreUnico(MultipartFile foto) throws IOException {
		 
		String nombreUnico = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
		Path root = getPath(nombreUnico);

		 
		Files.copy(foto.getInputStream(), root);

		return nombreUnico;
	}

	//accede a la ruta y lo elimina
	@Override
	public boolean eliminar(String nombre) {
		Path ruta =  getPath(nombre);
		File archivo = ruta.toFile();
		if (archivo.exists() && archivo.canRead())
		{
			if(archivo.delete())
			{ 
			return true;	 
			}
					 
		}
		return false;
	}

	// retorna la ruta 
	public Path getPath(String archivo) {
		return Paths.get(PERFIL).resolve(archivo).toAbsolutePath();

	}
}
