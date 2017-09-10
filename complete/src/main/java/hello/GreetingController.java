package hello;

import java.security.Principal;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
	
    @MessageMapping("/message")
//    @SendTo("/topic/public")
    public void greeting(@Payload Message message, Principal principal) throws Exception {
        
    	//record who sends the message
    	message.setUsername(principal.getName());
    	messageQueue.put(message);
    	
    	System.out.println("Registered " + message);
    	
    }
    
//    @SubscribeMapping("/user/{userId}")
//    public void init(@DestinationVariable String userId) {
//    	System.out.println(userId); 
//    }

	@EventListener
    public void handleSubscribeEvent(SessionSubscribeEvent event) {
		
		// register new player by simple session id
//		String stompSessionId = (String) event.getMessage().getHeaders().get("simpSessionId");
//		String user = event.getUser().getName();
		
		//register new player
		gameState.registerPlayer(event.getUser().getName());
		
//		// send initial state of game board
//		template.convertAndSend("/topic/public", gameState.toMessage());
		
	}
	
}

