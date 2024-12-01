package programa.eventos.bus;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


import programa.bicicletas.modelo.Bicicleta;
import programa.bicicletas.repositorio.RepositorioBicicletas;
import programa.eventos.config.RabbitMQConfig;

@Component
public class EventListener {
	
	@Autowired
	private RepositorioBicicletas repoBicicletas;
	
	@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME_CONSUMIDOR)
	public void handleEvent(Message evento) {
		ObjectMapper objectMapper = new ObjectMapper();
        try {
			JSONRabbitMQ bicicletaDTO = objectMapper.readValue(new String(evento.getBody()), JSONRabbitMQ.class);
			String idBicicleta = bicicletaDTO.getId();
			String routingKey = evento.getMessageProperties().getReceivedRoutingKey();
			if(routingKey.equals("citybike.alquileres.bicicleta-alquilada")) {
				Bicicleta bicicleta = repoBicicletas.findById(idBicicleta).get();
				bicicleta.setEstaDisponible(false);
				repoBicicletas.save(bicicleta);
			} else {
				Bicicleta bicicleta = repoBicicletas.findById(idBicicleta).get();
				bicicleta.setEstaDisponible(true);
				repoBicicletas.save(bicicleta);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
