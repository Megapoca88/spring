package sv.edu.udb.www.utils.paginador;

import java.util.ArrayList; 
import java.util.List;

import org.springframework.data.domain.Page;

public class Paginador<T> {

	private String url;
	private Page<T> pagina;
	private int totalPagina;
	private int elementosPagina;
	private int paginaActual;
	private List<Item> paginas;

	public Paginador(String url, Page<T> pagina) {
		this.url = url;
		this.pagina = pagina;
		this.paginas = new ArrayList<Item>();

		elementosPagina = pagina.getSize();
		totalPagina = pagina.getTotalPages();
		paginaActual = pagina.getNumber() + 1;

		int desde, hasta;

		if (totalPagina <= elementosPagina) {
			desde = 1;
			hasta = totalPagina;

		} else {
			if (paginaActual <= elementosPagina / 2) {
				desde = 1;
				hasta = elementosPagina;

			} else if (paginaActual >= totalPagina - elementosPagina / 2) {
				desde = totalPagina - elementosPagina + 1;
				hasta = elementosPagina;
			} else {

				desde = paginaActual - elementosPagina / 2;
				hasta = elementosPagina;
			}

		}
		
		for (int i=0; i < hasta; i++) {
			
			paginas.add(new Item(desde + i, paginaActual == desde +i));
		}

	}
	
	  
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Page<T> getPagina() {
		return pagina;
	}

	public void setPagina(Page<T> pagina) {
		this.pagina = pagina;
	}

	public int getTotalPagina() {
		return totalPagina;
	}

	public void setTotalPagina(int totalPagina) {
		this.totalPagina = totalPagina;
	}

	public int getElementosPagina() {
		return elementosPagina;
	}

	public void setElementosPagina(int elementosPagina) {
		this.elementosPagina = elementosPagina;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(int paginaActual) {
		this.paginaActual = paginaActual;
	}

	public List<Item> getPaginas() {
		return paginas;
	}

	public void setPaginas(List<Item> paginas) {
		this.paginas = paginas;
	}
	
	public boolean primera()
	{
		return pagina.isFirst();
	}
	public boolean ultima()
	{
		return pagina.isLast();
	}
	
	public boolean siguiente()
	{
		return pagina.hasNext();
	}
	public boolean anterior()
	{
		return pagina.hasPrevious();
	}
	}


