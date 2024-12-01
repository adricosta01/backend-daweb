package citybike.web.incidencia;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import bicicletas.dto.BicicletaDTO;
import citybike.web.incidenciasabiertas.OperacionesIncidenciaAbiertaWeb;
import estaciones.servicio.IServicioEstaciones;
import incidencias.servicio.IServicioIncidencias;
import incidencias.servicio.IncidenciaExistenteException;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

@Named
@javax.enterprise.context.SessionScoped
public class OperacionesIncidenciaWeb implements Serializable {

	private BicicletaDTO bicicleta;
	private String descripcionIncidencia;
	private IServicioIncidencias servicioIncidencias;

	@Inject
	protected FacesContext facesContext;
	@Inject
	private OperacionesIncidenciaAbiertaWeb operacionesIncidenciaGestorWeb;

	public OperacionesIncidenciaWeb() {
		servicioIncidencias = FactoriaServicios.getServicio(IServicioIncidencias.class);
	}

	public BicicletaDTO getBicicleta() {
		return bicicleta;
	}

	public String getDescripcionIncidencia() {
		return descripcionIncidencia;
	}

	public void setBicicleta(BicicletaDTO bicicleta) {
		this.bicicleta = bicicleta;
	}

	public void setDescripcionIncidencia(String descripcionIncidencia) {
		this.descripcionIncidencia = descripcionIncidencia;
	}

	public void goToCrearIncidencia() {
		if (this.bicicleta != null) {
			try {
				facesContext.getExternalContext().redirect("crearIncidencia.xhtml");
			} catch (IOException e) {

				facesContext.addMessage(null,

						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se ha podido navegar"));

				e.printStackTrace();

			}
		} else {
			FacesMessage mensajeError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Tienes que seleccionar una bicicleta antes.");
			FacesContext.getCurrentInstance().addMessage("form:messages", mensajeError);
		}
	}

	public void goToGestionarIncidencia() {
		if (operacionesIncidenciaGestorWeb.getIncidenciaSeleccionada() != null) {

			try {
				facesContext.getExternalContext().redirect("gestionarIncidencia.xhtml");
			} catch (IOException e) {

				facesContext.addMessage(null,

						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se ha podido navegar"));

				e.printStackTrace();

			}
		}
		else {
			FacesMessage mensajeError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Tienes que seleccionar una incidencia antes.");
			FacesContext.getCurrentInstance().addMessage("form:messages", mensajeError);
		}
	}

	public void goToIncidenciasAbiertas() {
		try {
			facesContext.getExternalContext().redirect("incidenciasAbiertas.xhtml");
		} catch (IOException e) {

			facesContext.addMessage(null,

					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se ha podido navegar"));

			e.printStackTrace();

		}
	}

	public void crearIncidencia() {
		try {
			servicioIncidencias.crearIncidencia(this.bicicleta.getId(), this.descripcionIncidencia);
			try {
				facesContext.getExternalContext().redirect("inicioCliente.xhtml");
				this.bicicleta = null;
			} catch (IOException e) {
				facesContext.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se ha podido navegar"));
				e.printStackTrace();
			}
		} catch (RepositorioException | EntidadNoEncontrada e) {
			FacesMessage mensajeError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Ha ocurrido un error al crear la incidencia. Intentalo mas tarde.");
			FacesContext.getCurrentInstance().addMessage("form:messages", mensajeError);

			e.printStackTrace();
		} catch (IncidenciaExistenteException e) {
			FacesMessage mensajeError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"No puede crear una incidencia para una bicicleta que ya tiene una abierta.");
			FacesContext.getCurrentInstance().addMessage("form:messages", mensajeError);
		}
	}

}
