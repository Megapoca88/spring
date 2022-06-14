package sv.edu.udb.www.utils.paginador;

public class Item {

	private int numero ;
	private boolean actual;
	
	public Item(int numero, boolean actual) {
		super();
		this.numero = numero;
		this.actual = actual;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean isActual() {
		return actual;
	}

	public void setActual(boolean actual) {
		this.actual = actual;
	}
	
	
}
