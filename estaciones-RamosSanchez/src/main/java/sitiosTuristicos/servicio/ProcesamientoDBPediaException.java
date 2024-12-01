package sitiosTuristicos.servicio;

@SuppressWarnings("serial")
public class ProcesamientoDBPediaException extends Exception {
	
	public ProcesamientoDBPediaException(String msg) {
		super(msg);
	}
	
	public ProcesamientoDBPediaException(String msg, Throwable causa) {
		super(msg, causa);
	}
	
}
