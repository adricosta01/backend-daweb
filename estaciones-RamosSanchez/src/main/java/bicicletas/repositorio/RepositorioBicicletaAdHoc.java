package bicicletas.repositorio;

import java.util.List;
import bicicletas.modelo.Bicicleta;
import incidencias.modelo.Incidencia;
import repositorio.RepositorioException;
import repositorio.RepositorioString;

public interface RepositorioBicicletaAdHoc extends RepositorioString<Bicicleta>{

	public Bicicleta getByID(String Id) throws RepositorioException;
	
	public List<Incidencia> getBicicletasConIncidenciaAbierta() throws RepositorioException;
}
