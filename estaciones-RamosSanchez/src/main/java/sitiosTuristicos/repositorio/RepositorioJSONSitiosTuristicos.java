package sitiosTuristicos.repositorio;

import repositorio.RepositorioJSON;
import sitiosTuristicos.modelo.SitioTuristico;

public class RepositorioJSONSitiosTuristicos extends RepositorioJSON<SitioTuristico> {

	public RepositorioJSONSitiosTuristicos() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<SitioTuristico> getClase() {

		return SitioTuristico.class;
	}
}
