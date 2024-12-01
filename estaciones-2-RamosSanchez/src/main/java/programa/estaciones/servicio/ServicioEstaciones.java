package programa.estaciones.servicio;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import programa.bicicletas.modelo.Bicicleta;
import programa.bicicletas.repositorio.RepositorioBicicletas;
import programa.estaciones.modelo.Estacion;
import programa.estaciones.repositorio.RepositorioEstaciones;
import programa.eventos.bus.JSONRabbitMQ;
import programa.eventos.bus.PublicadorEventos;
import programa.historicos.modelo.Historico;
import programa.historicos.modelo.Registro;
import programa.historicos.repositorio.RepositorioHistoricos;
import programa.repositorio.EntidadNoEncontrada;
import programa.repositorio.RepositorioException;

@Service
@Transactional
public class ServicioEstaciones implements IServicioEstaciones {

	private RepositorioEstaciones repoEstaciones;
	private RepositorioHistoricos repoHistoricos;
	private RepositorioBicicletas repoBicicletas;
	private PublicadorEventos sender;

	@Autowired
	public ServicioEstaciones(RepositorioEstaciones repoEstaciones, RepositorioHistoricos repoHistoricos,
			RepositorioBicicletas repoBicicletas, PublicadorEventos sender) {
		this.repoEstaciones = repoEstaciones;
		this.repoHistoricos = repoHistoricos;
		this.repoBicicletas = repoBicicletas;
		this.sender = sender;
	}

	@Override
	public String altaEstacion(String nombre, int numPuestos, int direccionPostal, double latitud, double longitud)
			throws RepositorioException {

		// Control de integridad de los datos

		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("nombre: no debe ser nulo ni vacio");

		if (numPuestos <= 0)
			throw new IllegalArgumentException("numPuestos: no puede ser igual o menor que 0");

		if (direccionPostal <= 0)
			throw new IllegalArgumentException("direccionPostal: no puede ser igual o menor que 0");

		Estacion estacion = new Estacion(nombre, numPuestos, direccionPostal, latitud, longitud);

		estacion.setFechaAlta(LocalDate.now());

		String id = repoEstaciones.save(estacion).getId();

		return id;
	}

	@Override
	public String altaBici(String modelo, String idEstacion) throws RepositorioException, EntidadNoEncontrada {

		if (modelo == null || modelo.isEmpty())
			throw new IllegalArgumentException("Modelo: no debe ser nulo ni vacio");

		if (idEstacion == null || idEstacion.isEmpty())
			throw new IllegalArgumentException("idEstacion: no debe ser nulo ni vacio");

		Estacion estacion = repoEstaciones.findById(idEstacion).get();
		if (estacion.getMaxPuestosBicis() - estacion.getNumBicis() == 0)
			throw new IllegalArgumentException("No hay espacio para mas bicis.");

		Bicicleta bicicleta = new Bicicleta(modelo, LocalDate.now());
		repoBicicletas.save(bicicleta);
		estacion.addBici(bicicleta);
		repoEstaciones.save(estacion);
		bicicleta.setEstacion(idEstacion);
		repoBicicletas.save(bicicleta);

		Historico historico = new Historico(bicicleta.getId());
		Registro registro = new Registro(idEstacion, LocalDateTime.now());
		historico.addRegistro(registro);
		repoHistoricos.save(historico);
		return bicicleta.getId();
	}

	@Override
	public void bajaBicicleta(String idBici, String motivoBaja) throws RepositorioException, EntidadNoEncontrada {
		Bicicleta bicicleta = repoBicicletas.findById(idBici).get();
		String estacionID = bicicleta.getEstacionID();
		if (estacionID != null && !estacionID.isEmpty()) {
			Estacion estacion = repoEstaciones.findById(bicicleta.getEstacionID()).get();

			// Añadimos fecha fin
			Historico historico = repoHistoricos.findByidBicicleta(idBici);
			Registro registro = historico.getRegistroActual(bicicleta.getEstacionID());
			registro.setFechaFin(LocalDateTime.now());
			repoHistoricos.save(historico);

			estacion.remBici(bicicleta);
			bicicleta.eliminarEstacion();
			repoEstaciones.save(estacion);
			sender.bicicletaDesactivada(new JSONRabbitMQ(bicicleta.getId()));
		}

		bicicleta.setFechaBaja(LocalDate.now());
		bicicleta.setMotivoBaja(motivoBaja);
		bicicleta.setEstaDisponible(false);
		repoBicicletas.save(bicicleta);
	}

	@Override
	public Page<Bicicleta> getBicisEstacion(String idEstacion, Pageable pageable) throws RepositorioException, EntidadNoEncontrada {
		return repoBicicletas.findByestacionID(idEstacion, pageable);
	}

	@Override
	public Page<Estacion> getEstaciones(Pageable pageable) throws RepositorioException, EntidadNoEncontrada {		
		return repoEstaciones.findAll(pageable);
	}

	@Override
	public Estacion getEstacion(String id) throws RepositorioException, EntidadNoEncontrada {

		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");

		return repoEstaciones.findById(id).get();
	}

	@Override
	public Page<Bicicleta> getBicisDisponiblesEstacion(String idEstacion, Pageable pageable)
			throws RepositorioException, EntidadNoEncontrada {
		
		return repoBicicletas.findByEstacionIDAndDisponibleIsTrue(idEstacion, pageable);
	}

	@Override
	public void estacionarBici(String idBici, String idEstacion) throws RepositorioException, EntidadNoEncontrada {
		Bicicleta bicicleta = repoBicicletas.findById(idBici).get();
		Estacion estacion = repoEstaciones.findById(idEstacion).get();

		if (estacion.getMaxPuestosBicis() - estacion.getNumBicis() == 0)
			throw new RepositorioException("La estacion no tiene puesdtos libres.");

		estacion.addBici(bicicleta);
		repoEstaciones.save(estacion);
		bicicleta.setEstacion(idEstacion);
		repoBicicletas.save(bicicleta);

		// Modificamos el histórico
		Historico historico = repoHistoricos.findByidBicicleta(idBici);
		Registro registro = new Registro(idEstacion, LocalDateTime.now());
		historico.addRegistro(registro);
		repoHistoricos.save(historico);
	}
}
