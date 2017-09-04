package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class MessageConsumer{

	@Autowired
	private BlockingQueue<Message> messageQueue;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private GameState gameState;

	@Async
	public void start() {
		while (true) {
			Message message;
			try {
				message = messageQueue.take();
				System.out.println("Consuming " + message);
				
		    	gameState.update(message);
		    	
				template.convertAndSend("/topic/greetings", gameState.toMessage());
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
