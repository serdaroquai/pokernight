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
				
				if (message.isWelcome()) {
					//send to latest status
				} else {
					gameState.update(message);
				}
		    	
				//send public state update
				template.convertAndSend("/topic/public", gameState.toMessage());

				//send private messages
				for (Player player : gameState.getPlayers().values()) {
					String name = player.getPlayerName();
					template.convertAndSendToUser(name, "/queue/private", gameState.toPrivateMessage(name));					
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
