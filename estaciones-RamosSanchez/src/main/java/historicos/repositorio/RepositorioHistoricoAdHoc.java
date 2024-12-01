package historicos.repositorio;

import historicos.modelo.Historico;
import repositorio.RepositorioString;

public interface RepositorioHistoricoAdHoc extends RepositorioString<Historico>{
	public Historico getByBicleta(String idBicicleta);
}
