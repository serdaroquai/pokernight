package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class GameState {

	
	Map<Integer,Sprite> sprites;

	@PostConstruct
	private void initialize() {
		
		// initialize game
		sprites = new HashMap<Integer,Sprite>();
		for (int i=1;i<4;i++) {
			Sprite sprite = new Sprite(i, i*50, i*50, false);
			sprites.put(sprite.getId(), sprite);
		}
	}	
	
	public StateUpdateMessage toMessage() {
		    	
    	StateUpdateMessage stateUpdateMessage = new StateUpdateMessage();
    	stateUpdateMessage.setSprites(new ArrayList<Sprite>(sprites.values()));
    	return stateUpdateMessage;
	}
	
	public void update(Message message) {

		// todo proper validations
		
		synchronized (sprites) {
    		Sprite sprite = sprites.get(message.getId());
    		sprite.setX(message.getX());
    		sprite.setY(message.getY());
		}
	}
	
	public void registerPlayer(String sessionId) {
		// todo add a private player area sprite moveable only by player
		
	}
	
	public void unregisterPlayer(String sessionId) {
		// todo remove private player area sprite 
	}
}
