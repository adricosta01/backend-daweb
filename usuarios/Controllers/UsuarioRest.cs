

using Codigos.Modelo;
using Microsoft.AspNetCore.Mvc;
using Usuarios.DTO;
using Usuarios.Modelo;
using Usuarios.Servicio;

namespace Usuarios.Controllers{
    
    [Route("api/usuarios")]
    [ApiController]
    public class UsuarioController : ControllerBase{

        private readonly IServicioUsuarios _servicio;

        public UsuarioController(IServicioUsuarios servicio){
            this._servicio = servicio;
        }

        [HttpGet]
        public ActionResult<List<Usuario>> Get() => _servicio.GetUsuarios();

        [HttpGet("{id}", Name ="GetUsuario")]
        public ActionResult<Usuario> Get(string id){
            var entidad = _servicio.Get(id);
            if(entidad == null){
                return NotFound();
            }
            
            return entidad;
        }

        [HttpPost]
        public ActionResult<UsuarioDTO> AltaUsuario(UsuarioDTO usuarioDTO){
            _servicio.AltaUsuario(usuarioDTO);
            
            return CreatedAtRoute("GetUsuario", new {id = usuarioDTO.Id}, usuarioDTO);
        }

        [HttpDelete("{id}")]
        public IActionResult BajaUsuario(string id)
        {
            var actividad = _servicio.Get(id);
            if (actividad == null)
            {
                return NotFound();
            }
            _servicio.BajaUsuario(id);

            return NoContent();
        }

        [HttpPost("codigo")]
        public ActionResult<string> SolicitadCodigoActivacion([FromForm] string id)
        {
           string idCodigo = _servicio.SolicitadCodigoActivacion(id);

            return Ok(new { id = idCodigo.ToString()});
        }

        [HttpPost("verificar")]
        public IActionResult VerificarCredenciales([FromForm] string nombreUsuario, [FromForm] string password)
        {
            try
            {
                var claims = _servicio.VerificarCredenciales(nombreUsuario, password);
                if (claims != null)
                {
                    return Ok(claims);
                }
                else
                {
                    return NotFound("Usuario no encontrado o credenciales incorrectas");
                }
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error interno del servidor: {ex.Message}");
            }
        }

        [HttpPost("verificar/oauth2")]
        public IActionResult VerificarUsuarioOAuth2([FromForm] string OAuth2Id)
        {
            try
            {
                var claims = _servicio.VerificarUsuarioOAuth2(OAuth2Id);
                if (claims != null)
                {
                    return Ok(claims);
                }
                else
                {
                    return Ok(null);
                }
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error interno del servidor: {ex.Message}");
            }
        }

    }
}