package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class GameState {

	private static int BOARD_WIDTH = 512;
	private static int BOARD_HEIGHT = 512;
	private static int CARD_WIDTH = 44;
	private static int CARD_HEIGHT = 63;
	private static int HAND_WIDTH = 66;
	private static int HAND_HEIGHT = 63;
	private static int BUTTON_WIDTH = 63;
	private static int BUTTON_HEIGHT = 63;
	public static int NUMBER_OF_HAND_IMAGES = 4;
	
	// all the sprites on the board
	private Map<String,Sprite> sprites;
	private Map<String,Player> players;
	
	private AtomicInteger playerCount;
	
	private Random random;
	private Deck deck;
	private Button resetButton;

	@PostConstruct
	private void initialize() {
		
		this.random = new Random();
		players = new HashMap<String,Player>();
		playerCount = new AtomicInteger(0);
		
		// initialize game
		initializeGame();
		
	}	
	
	public static String generateId() {
		return UUID.randomUUID().toString();
	}
	
	public void initializeGame() {
		
		sprites = new HashMap<String,Sprite>();
		
		this.deck = new Deck(random);
		deck.setSprite(Sprite.from(deck, 
				BOARD_WIDTH - CARD_WIDTH - 10, 
				BOARD_HEIGHT - CARD_HEIGHT - 10));
		
		// add the deck sprite
		sprites.put(deck.getId(), deck.getSprite());
		
		this.resetButton = new Button();
		resetButton.setSprite(Sprite.from(resetButton, 
				BOARD_WIDTH - BUTTON_WIDTH - 10, 
				BUTTON_HEIGHT + 10));
		
		// add the reset button sprite
		sprites.put(resetButton.getId(), resetButton.getSprite());
		
		// add the player sprites
		for (Player player : players.values()) {
			sprites.put(player.getId(), player.getSprite());
		}
		
		
//		sprites = new HashMap<Integer,Sprite>();
//		for (int i=1;i<4;i++) {
//			Sprite sprite = new Sprite(i, i*50, i*50, false);
//			sprites.put(sprite.getId(), sprite);
//		}
	}
	
	public StateUpdateMessage toMessage() {
		    	
    	StateUpdateMessage stateUpdateMessage = new StateUpdateMessage();
    	
    	stateUpdateMessage.setSprites(new ArrayList<Sprite>(sprites.values()));
    	
    	// add player sprites
    	for (Player player : players.values()) {
    		stateUpdateMessage.getSprites().add(player.getSprite());
    	}
    	
    	return stateUpdateMessage;
	}
	
	public void update(Message message) {

		
		Sprite targetSprite = sprites.get(message.getId());
		
		// todo proper validations
		
		if (message.isRightClick()) {
			Object gameObject = targetSprite.getGameObject();
			if (gameObject instanceof Card) {
				// right click a card
				Card card = (Card) targetSprite.getGameObject();
				card.flip();
			}
			if (gameObject instanceof Button) {
				//reset the game
				initializeGame();
			}
//			} else if (gameObject instanceof Deck) {
//				//do nothing
//			} else if (gameObject instanceof Player) {
//				// do nothing
//			}
			
		} else if (deck.getSprite().equals(targetSprite)) {
			// drawing a card from deck
			
			Card card = deck.draw();
			if (card != null) {
				card.setSprite(Sprite.from(card, message.getX(), message.getY()));
				sprites.put(card.getSprite().getId(), card.getSprite());
			}
			
		} else {
						
			// move the game object
			targetSprite.setX(message.getX());
			targetSprite.setY(message.getY());
		}
		
	}
	
	public void registerPlayer(String userName) {
		// TODO add a private player area sprite moveable only by player
		if (!players.containsKey(userName)) {
			
			// create the player
			Player player = new Player(playerCount.incrementAndGet(),userName);
			Sprite playerSprite = Sprite.from(player, player.getPlayerNo()*HAND_WIDTH + 20, player.getPlayerNo() * HAND_HEIGHT + 20);
			player.setSprite(playerSprite);
			
			//add player to player and sprite list
			players.put(userName, player);
			sprites.put(player.getId(), playerSprite);
			
		}
		
	}
	
	public void unregisterPlayer(String userName) {
		// TODO remove private player area sprite 
	}
}
