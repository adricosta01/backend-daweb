package programa.estaciones.rest;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import programa.bicicletas.dto.BicicletaDTO;
import programa.bicicletas.modelo.Bicicleta;
import programa.estaciones.dto.EstacionDTO;
import programa.estaciones.modelo.Estacion;
import programa.estaciones.servicio.IServicioEstaciones;
import programa.repositorio.EntidadNoEncontrada;
import programa.repositorio.RepositorioException;

@RestController
@RequestMapping("/estaciones")
@Tag(name ="Estaciones", description ="Aplicación de estaciones")
public class EstacionesControladorRest {

	private IServicioEstaciones servicioEstaciones;

	@Autowired
	private PagedResourcesAssembler<EstacionDTO> pagedResourcesAssembler;
	@Autowired
	private PagedResourcesAssembler<BicicletaDTO> pagedResourcesAssemblerBicis;
	
	@Autowired
	public EstacionesControladorRest(IServicioEstaciones servicioEstaciones) {
		this.servicioEstaciones = servicioEstaciones;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAuthority('gestor')")
	@Operation(summary ="Crear estación",description ="Crea una estacion")
	public ResponseEntity<Void> createEstacion(@Valid @RequestBody EstacionDTO estacionDTO) throws Exception {
		String id = this.servicioEstaciones.altaEstacion(estacionDTO.getNombre(), estacionDTO.getMaxPuestosBicis(),
				estacionDTO.getCodigoPostal(), estacionDTO.getLatitud(), estacionDTO.getLongitud());

		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

		return ResponseEntity.created(nuevaURL).build();
	}

	@PostMapping(value = "/bicicletas", consumes = { MediaType.APPLICATION_JSON_VALUE })
	//@PreAuthorize("hasAuthority('gestor')")
	@Operation(summary ="Crear bicicleta",description ="Crea una bicicleta")
	public ResponseEntity<Void> createBicicleta(@Valid @RequestBody BicicletaDTO bicicletaDTO)
			throws RepositorioException, EntidadNoEncontrada {
		String id = this.servicioEstaciones.altaBici(bicicletaDTO.getModelo(), bicicletaDTO.getIdEstacion());

		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

		return ResponseEntity.created(nuevaURL).build();
	}

	@PutMapping(value = "/bicicletas/{idBici}")
	//@PreAuthorize("hasAuthority('gestor')")
	@Operation(summary ="Dar de baja una bicicleta",description ="Da de baja una bicicleta, la cual pasa a no estar disponible.")
	public ResponseEntity<Void> bajaBicicleta(@PathVariable String idBici, @RequestParam String motivoBaja) {
		try {
			servicioEstaciones.bajaBicicleta(idBici, motivoBaja);
			return ResponseEntity.noContent().build();
		} catch (RepositorioException | EntidadNoEncontrada e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(value = "{idEstacion}/bicicletas", produces = { MediaType.APPLICATION_JSON_VALUE})
	//@PreAuthorize("hasAuthority('gestor')")
	@Operation(summary ="Obtener bicicletas",description ="Obtiene las bicicletas que se encuentran estacionadas en una estación.")
	public Page<EntityModel<BicicletaDTO>> getBicisByIdEstacion(@PathVariable String idEstacion, @RequestParam int page, @RequestParam int size) throws Exception {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("modelo").ascending());
		
		Page<Bicicleta> bicicletas = servicioEstaciones.getBicisEstacion(idEstacion, paginacion);
		Page<BicicletaDTO> bicicletasDTO =  bicicletas.map(bicicleta ->{
			return BicicletaDTO.transformToDTO(bicicleta);
        });
		
		return bicicletasDTO.map(bicicleta ->{
			EntityModel<BicicletaDTO> model = EntityModel.of(bicicleta);
			String urlEliminar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
					.methodOn(EstacionesControladorRest.class)
					.bajaBicicleta(bicicleta.getId(), "Motivo baja"))
					.toUri()
					.toString();
					model.add(Link.of(urlEliminar,"bajaBici"));
					
			return model;
		});
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary ="Obtener estaciones",description ="Obtiene todas las estaciones.")
	public PagedModel<EntityModel<EstacionDTO>> getEstaciones(@RequestParam int page, @RequestParam int size) throws Exception{
		Pageable paginacion = PageRequest.of(page, size, Sort.by("nombre").ascending());

		Page<Estacion> estaciones = this.servicioEstaciones.getEstaciones(paginacion);
		Page<EstacionDTO> estacionesDTO =  estaciones.map(bicicleta ->{
			return EstacionDTO.transformToDTO(bicicleta);
        });
		return this.pagedResourcesAssembler.toModel(estacionesDTO);
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary ="Obtener estación",description ="Obtiene una estación por su id.")
	public EntityModel<EstacionDTO> getEstacionById(@PathVariable String id) throws Exception {		
		Estacion estacion = this.servicioEstaciones.getEstacion(id);
		
		EntityModel<EstacionDTO> model = EntityModel.of(EstacionDTO.transformToDTO(estacion));
		model.add(
		WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(EstacionesControladorRest.class)
		.getEstacionById(id))
		.withSelfRel());
		
		return model;
	}
	
	@GetMapping(value = "{idEstacion}/bicicletas/disponibles", produces = { MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary ="Obtener bicicletas disponibles",description ="Obtiene las bicicletas disponibles que se encuentran estacionadas en una estación.")
    public PagedModel<EntityModel<BicicletaDTO>> getBicisDisponiblesEstacion(@PathVariable String idEstacion, @RequestParam int page, @RequestParam int size) throws Exception {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("modelo").ascending());
		
        Page<Bicicleta> bicicletasDisponibles = servicioEstaciones.getBicisDisponiblesEstacion(idEstacion, paginacion);
        Page<BicicletaDTO> bicicletasDTO =  bicicletasDisponibles.map(bicicleta ->{
			return BicicletaDTO.transformToDTO(bicicleta);
        });
                
        return pagedResourcesAssemblerBicis.toModel(bicicletasDTO);
    }
	
	@PostMapping(value = "{idEstacion}/bicicletas/{idBici}/estacionar")
	@Operation(summary ="Estacionar una bicicleta.",description ="Estaciona una bicicleta en una estación.")
    public ResponseEntity<Void> estacionarBici(
    		@PathVariable String idEstacion,
            @PathVariable String idBici) {
        try {
            servicioEstaciones.estacionarBici(idBici, idEstacion);
            return ResponseEntity.noContent().build();
        } catch (RepositorioException | EntidadNoEncontrada e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
