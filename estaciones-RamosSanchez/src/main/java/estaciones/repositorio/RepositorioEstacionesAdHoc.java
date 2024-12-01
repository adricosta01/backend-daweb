package estaciones.repositorio;

import java.util.List;
import java.util.Set;

import bicicletas.modelo.Bicicleta;
import estaciones.modelo.Estacion;
import repositorio.RepositorioString;

public interface RepositorioEstacionesAdHoc extends RepositorioString<Estacion> {
	public Estacion getEstacionPuestosLibres() throws NoHayEspacioBiciException;
	
	public List<Estacion> getBicisEnEstacionesCercanas(double longitud, double latitud);
	
	public Set<Estacion> getEstacionesPorSitiosTuristicos();
}
