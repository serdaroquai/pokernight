package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

	Map<Integer,Sprite> sprites;
	
	@Autowired
	private BlockingQueue<Message> messageQueue;
	
	@Autowired
	private MessageConsumer messageConsumer;
	
	@PostConstruct
	private void initialize() {
		
		// start the message consumer
		messageConsumer.start();
		
		// initialize game
		sprites = new HashMap<Integer,Sprite>();
		for (int i=1;i<4;i++) {
			Sprite sprite = new Sprite(i, i*50, i*50, false);
			sprites.put(sprite.getId(), sprite);
		}
	}
	
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public StateUpdateMessage greeting(Message message) throws Exception {
        
    	messageQueue.put(message);
    	System.out.println(message);
    	
    	synchronized (sprites) {
    		Sprite sprite = sprites.get(message.getId());
    		sprite.setX(message.getX());
    		sprite.setY(message.getY());
		}
    	
    	StateUpdateMessage stateUpdateMessage = new StateUpdateMessage();
    	stateUpdateMessage.setSprites(new ArrayList<Sprite>(sprites.values()));
    	return stateUpdateMessage;
    }

}

