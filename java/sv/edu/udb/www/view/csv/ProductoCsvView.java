package sv.edu.udb.www.view.csv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import sv.edu.udb.www.models.entity.Producto;


@Component("Producto/listar.csv")
public class ProductoCsvView extends AbstractView{

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setHeader("Content-Disposition", "attachment; filename=\"productos.csv\"");
		response.setContentType(getContentType());
		
		
		Page<Producto> productos = (Page<Producto>) model.get("productos");
		
		ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(),  CsvPreference.STANDARD_PREFERENCE);
		
		String[] header = {"id", "nombre", "marca", "descripcion", "precio" , "cantidad"};
		beanWriter.writeHeader(header);
		
		for(Producto producto: productos) {
			beanWriter.write(producto, header);
		}
		beanWriter.close();
	}
	public ProductoCsvView() {
		setContentType("text/csv");
	}
		@Override
		protected boolean generatesDownloadContent() {
			// TODO Auto-generated method stub
			return true ;
		}

}
