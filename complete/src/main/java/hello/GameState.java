package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class GameState {

	private static int BOARD_WIDTH = 512;
	private static int BOARD_HEIGHT = 512;
	private static int CARD_WIDTH = 44;
	private static int CARD_HEIGHT = 63;
	
	// all the sprites on the board
	private Map<String,Sprite> sprites;
	
	private Random random;
	private Deck deck;

	@PostConstruct
	private void initialize() {
		
		this.random = new Random();
		sprites = new HashMap<String,Sprite>();
		
		// initialize game
		initializeGame();
		
	}	
	
	public static String generateId() {
		return UUID.randomUUID().toString();
	}
	
	public void initializeGame() {
		
		
		
		this.deck = new Deck(random);
		deck.setSprite(Sprite.from(deck, 
				BOARD_WIDTH - CARD_WIDTH - 10, 
				BOARD_HEIGHT - CARD_HEIGHT - 10));
		
		sprites.put(deck.getSprite().getId(), deck.getSprite());
		
		
		
//		sprites = new HashMap<Integer,Sprite>();
//		for (int i=1;i<4;i++) {
//			Sprite sprite = new Sprite(i, i*50, i*50, false);
//			sprites.put(sprite.getId(), sprite);
//		}
	}
	
	public StateUpdateMessage toMessage() {
		    	
    	StateUpdateMessage stateUpdateMessage = new StateUpdateMessage();
    	
    	stateUpdateMessage.setSprites(new ArrayList<Sprite>(sprites.values()));
    	
    	return stateUpdateMessage;
	}
	
	public void update(Message message) {

		// todo proper validations
		
		Sprite targetSprite = sprites.get(message.getId());
		
		if (message.isRightClick() && !deck.getSprite().equals(targetSprite)) {
			// right click a card
			Card card = (Card) targetSprite.getGameObject();
			card.flip();
			
		} else if (deck.getSprite().equals(targetSprite)) {
			// drawing a card from deck
			
			Card card = deck.draw();
			if (card != null) {
				card.setSprite(Sprite.from(card, message.getX(), message.getY()));
				sprites.put(card.getSprite().getId(), card.getSprite());
			}
			
		} else {
			// TODO is ctrl clicked (faceUp face down)
			
			// move a card
			targetSprite.setX(message.getX());
			targetSprite.setY(message.getY());
		}
		
//		synchronized (sprites) {
//    		IsSprite sprite = sprites.get(message.getId());
//    		IsSprite.setX(message.getX());
//    		IsSprite.setY(message.getY());
//		}
	}
	
	public void registerPlayer(String sessionId) {
		// TODO add a private player area sprite moveable only by player
		
	}
	
	public void unregisterPlayer(String sessionId) {
		// TODO remove private player area sprite 
	}
}
