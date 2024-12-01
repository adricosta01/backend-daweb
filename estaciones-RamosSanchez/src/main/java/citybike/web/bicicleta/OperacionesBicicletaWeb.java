package citybike.web.bicicleta;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import bicicletas.dto.BicicletaDTO;
import citybike.web.incidencia.OperacionesIncidenciaWeb;
import estaciones.modelo.Estacion;
import estaciones.servicio.IServicioEstaciones;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named
@ViewScoped
public class OperacionesBicicletaWeb implements Serializable {

	private String longitud;
	private String latitud;
	private List<BicicletaDTO> bdtos;
	private BicicletaDTO bicicletaSeleccionada;
	private IServicioEstaciones servicioEstaciones;

	@Inject
	protected FacesContext facesContext;
	
	@Inject
    private OperacionesIncidenciaWeb operacionesIncidenciaWeb;

	public OperacionesBicicletaWeb() {
		servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
		this.bdtos = new LinkedList<BicicletaDTO>();
	}

	public String getLongitud() {
		return longitud;
	}

	public String getLatitud() {
		return latitud;
	}

	public List<BicicletaDTO> getBdtos() {
		return bdtos;
	}

	public BicicletaDTO getbicicletaSeleccionada() {
		return bicicletaSeleccionada;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public void setBdtos(List<BicicletaDTO> bdtos) {
		this.bdtos = bdtos;
	}

	public void setBicicletaSeleccionada(BicicletaDTO bicicletaSeleccionada) {
		this.bicicletaSeleccionada = bicicletaSeleccionada;
	}

	public void goToBuscadorBicis() {
		try {
			facesContext.getExternalContext().redirect("buscadorBicis.xhtml");
		} catch (IOException e) {

			facesContext.addMessage(null,

					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se ha podido navegar"));

			e.printStackTrace();

		}
	}

	public void buscarBicicletasCercanas() throws RepositorioException, EntidadNoEncontrada {		
		if (longitud.equals("") || latitud.equals("")) {
			FacesMessage mensajeError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Por favor, completa los campos de longitud y/o latitud.");
			FacesContext.getCurrentInstance().addMessage("form:messages", mensajeError);
		} else {
			try {
				this.bdtos = servicioEstaciones.recuperarBicisCercanas(Double.parseDouble(longitud), Double.parseDouble(latitud));
			}catch(NumberFormatException e) {
				FacesMessage mensajeError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"Formato de los parametros establecidos incorrecto.");
				FacesContext.getCurrentInstance().addMessage("form:messages", mensajeError);
			}
		}
	}
	
	public void getBicicletaParaIncidencia() {
		operacionesIncidenciaWeb.setBicicleta(this.bicicletaSeleccionada);
	}
	

	public void onRowSelect(SelectEvent<BicicletaDTO> event) {
		FacesMessage msg = new FacesMessage("Customer Selected", String.valueOf(event.getObject().getId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
