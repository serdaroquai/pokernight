package hello;

import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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
		    	
				//send public state update
				template.convertAndSend("/topic/public", gameState.toMessage());

				//send private message to original sender
				template.convertAndSendToUser(message.getUsername(), "/queue/private", gameState.toMessage());
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
