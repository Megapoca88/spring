package sv.edu.udb.www.view.pdf;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;


import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


import sv.edu.udb.www.models.entity.DetalleFactura;
import sv.edu.udb.www.models.entity.Factura;

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Factura factura = (Factura) model.get("factura");
	
		MessageSourceAccessor mensajes =  getMessageSourceAccessor();
		
		PdfPTable tabla = new PdfPTable(1);
		tabla.setSpacingAfter(20);
		
		PdfPCell cell = null;
		
		cell = new PdfPCell(new Phrase(mensajes.getMessage("text.factura.ver.datos.cliente")));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		tabla.addCell(cell);
		
		
		tabla.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		tabla.addCell(factura.getCliente().getEmail());
		
		PdfPTable tabla2 = new PdfPTable(1);
		tabla2.setSpacingAfter(20);
		
		
		cell = new PdfPCell(new Phrase(mensajes.getMessage("text.factura.ver.datos.factura")));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);
		
		tabla2.addCell(cell);
		tabla2.addCell(mensajes.getMessage("text.cliente.factura.folio") + ": " +  factura.getId());
		tabla2.addCell(mensajes.getMessage("text.cliente.factura.descripcion") + ": " + factura.getDescripcion());
		tabla2.addCell(mensajes.getMessage("text.cliente.factura.fecha") + ": " + factura.getFecha());
		
		document.add(tabla);
		document.add(tabla2);
		
		PdfPTable tabla3 = new PdfPTable(4);
		tabla3.setWidths(new float [] {3.5f, 1, 1, 1});
		tabla3.addCell(mensajes.getMessage("text.factura.form.item.nombre"));
		tabla3.addCell(mensajes.getMessage("text.factura.form.item.precio"));
		tabla3.addCell(mensajes.getMessage("text.factura.form.item.cantidad"));
		tabla3.addCell(mensajes.getMessage("text.factura.form.item.total"));
		
		for(DetalleFactura item: factura.getDetalles()) {
			tabla3.addCell(item.getProducto().getNombre());
			tabla3.addCell(item.getProducto().getPrecio().toString());
			
			cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			tabla3.addCell(cell);
			tabla3.addCell(item.calcularImporte().toString());
		}
		
	    cell = new PdfPCell(new Phrase(mensajes.getMessage("text.factura.form.total")));
	    cell.setColspan(3);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	    tabla3.addCell(cell);
	    tabla3.addCell(factura.getTotal().toString());
	    
	    document.add(tabla3);
	}

}
