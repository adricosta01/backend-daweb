package citybike.web.incidenciasabiertas;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import bicicletas.dto.BicicletaDTO;
import estaciones.repositorio.NoHayEspacioBiciException;
import estaciones.servicio.IServicioEstaciones;
import incidencias.dto.IncidenciaDTO;
import incidencias.modelo.EstadoIncidencia;
import incidencias.servicio.IServicioIncidencias;
import incidencias.servicio.IncidenciaExistenteException;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

@Named
@javax.enterprise.context.SessionScoped
public class OperacionesIncidenciaAbiertaWeb implements Serializable {
	private List<IncidenciaDTO> idtos;
	private IncidenciaDTO incidenciaSeleccionada;
	private IServicioIncidencias servicioIncidencias;
	private String descripcion;

	@Inject
	protected FacesContext facesContext;

	public OperacionesIncidenciaAbiertaWeb() {
		servicioIncidencias = FactoriaServicios.getServicio(IServicioIncidencias.class);
	}

	public List<IncidenciaDTO> getIdtos() {
		return idtos;
	}

	public IncidenciaDTO getIncidenciaSeleccionada() {
		return incidenciaSeleccionada;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setIdtos(List<IncidenciaDTO> idtos) {
		this.idtos = idtos;
	}

	public void setIncidenciaSeleccionada(IncidenciaDTO incidenciaSeleccionada) {
		this.incidenciaSeleccionada = incidenciaSeleccionada;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void recuperarIncidenciasAbiertas() throws RepositorioException, EntidadNoEncontrada {
		try {
			this.idtos = servicioIncidencias.recuperarIncidenciasAbiertasDTO();
		} catch (NumberFormatException e) {
			FacesMessage mensajeError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Formato de los parametros establecidos incorrecto.");
			FacesContext.getCurrentInstance().addMessage("form:messages", mensajeError);
		}
	}

	public void onRowSelect(SelectEvent<IncidenciaDTO> event) {
		FacesMessage msg = new FacesMessage("Customer Selected", String.valueOf(event.getObject().getId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void cambiarACancelada() {
		if (this.incidenciaSeleccionada.getEstado().equals(EstadoIncidencia.PENDIENTE)) {
			try {
				this.incidenciaSeleccionada.setEstado(EstadoIncidencia.CANCELADA);
				this.descripcion = "";
				servicioIncidencias.gestionarIncidencias(this.incidenciaSeleccionada.getIdBici(), EstadoIncidencia.CANCELADA, this.descripcion);
			} catch (RepositorioException | EntidadNoEncontrada e) {
				e.printStackTrace();
			}
		} else {
			FacesMessage mensajeError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Acción no valida.");
			FacesContext.getCurrentInstance().addMessage("form:messages", mensajeError);
		}

	}

	public void cambiarAAsignada() {
		if (this.incidenciaSeleccionada.getEstado().equals(EstadoIncidencia.PENDIENTE)) {
			try {
				this.incidenciaSeleccionada.setEstado(EstadoIncidencia.ASIGNADA);
				this.descripcion = "";
				servicioIncidencias.gestionarIncidencias(this.incidenciaSeleccionada.getIdBici(), EstadoIncidencia.ASIGNADA, this.descripcion);
			} catch (RepositorioException | EntidadNoEncontrada e) {
				e.printStackTrace();
			}
		} else {
			FacesMessage mensajeError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Acción no valida.");
			FacesContext.getCurrentInstance().addMessage("form:messages", mensajeError);
		}
	}

	public void cambiarAResuelta() {
		if (this.incidenciaSeleccionada.getEstado().equals(EstadoIncidencia.ASIGNADA)) {
			try {
				this.incidenciaSeleccionada.setEstado(EstadoIncidencia.RESUELTA);
				this.descripcion = "";
				servicioIncidencias.gestionarIncidencias(this.incidenciaSeleccionada.getIdBici(), this.descripcion, true);
			} catch (RepositorioException | EntidadNoEncontrada | NoHayEspacioBiciException e) {
				e.printStackTrace();
			}
		} else {
			FacesMessage mensajeError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Acción no valida.");
			FacesContext.getCurrentInstance().addMessage("form:messages", mensajeError);
		}
	}

	public void cambiarANoResuelta() {
		if (this.incidenciaSeleccionada.getEstado().equals(EstadoIncidencia.ASIGNADA)) {
			try {
				this.incidenciaSeleccionada.setEstado(EstadoIncidencia.RESUELTA);
				this.descripcion = "";
				servicioIncidencias.gestionarIncidencias(this.incidenciaSeleccionada.getIdBici(), this.descripcion, false);
			} catch (RepositorioException | EntidadNoEncontrada | NoHayEspacioBiciException e) {
				e.printStackTrace();
			}
		} else {
			FacesMessage mensajeError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Acción no valida.");
			FacesContext.getCurrentInstance().addMessage("form:messages", mensajeError);
		}
	}

}
