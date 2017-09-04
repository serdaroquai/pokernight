package hello;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Controller
public class GreetingController {

	
	@Autowired
	private BlockingQueue<Message> messageQueue;
	
	@Autowired
	private MessageConsumer messageConsumer;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private GameState gameState;

	
	@PostConstruct
	private void initialize() {
		
		// start the message consumer
		messageConsumer.start();
		
	}
	
    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
    public void greeting(Message message) throws Exception {
        
    	messageQueue.put(message);
    	System.out.println("Registering " + message);
    	
    }

	@EventListener
    public void handleSubscribeEvent(SessionSubscribeEvent event) {
		// send initial state of game board
		template.convertAndSend("/topic/greetings", gameState.toMessage());
	}
}

