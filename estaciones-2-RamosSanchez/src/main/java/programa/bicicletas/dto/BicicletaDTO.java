package programa.bicicletas.dto;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import programa.bicicletas.modelo.Bicicleta;

@Schema(description ="DTO de la entidad Bicicleta")
public class BicicletaDTO {
	
	@Schema(description ="Identificador de la bicicleta")
	@NotNull
	private String id;
	@Schema(description ="Modelo de la bicicleta")
	@NotNull
	private String modelo;
	@Schema(description ="Estación en la que se encuentra la bicicleta")
	private String idEstacion;
	
	public BicicletaDTO(String id, String modelo, String idEstacion) {
		this.id = id;
		this.modelo = modelo;
		this.idEstacion = idEstacion;
	}
	
	public String getId() {
		return id;
	}
	
	public String getModelo() {
		return modelo;
	}
	
	public String getIdEstacion() {
		return idEstacion;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public void setIdEstacion(String idEstacion) {
		this.idEstacion = idEstacion;
	}
	
	public static BicicletaDTO transformToDTO(Bicicleta bicicleta){
		BicicletaDTO bdto = new BicicletaDTO(bicicleta.getId(), bicicleta.getModelo(), bicicleta.getEstacionID());

		return bdto;
	}
}
