package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class GameState {

	public static int BOARD_WIDTH = 512;
	public static int BOARD_HEIGHT = 512;
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
	
	public Map<String, Player> getPlayers() {
		return players;
	}
	
	public static String generateId() {
		return UUID.randomUUID().toString();
	}
	
	public void initializeGame() {
		
		sprites = new HashMap<String,Sprite>();
		
		this.deck = new Deck(random);
		deck.setSprite(Sprite.from(deck, 
				BOARD_WIDTH - Deck.WIDTH - 10, 
				BOARD_HEIGHT - Deck.HEIGHT - 10));
		
		// add the deck sprite
		sprites.put(deck.getId(), deck.getSprite());
		
		this.resetButton = new Button();
		resetButton.setSprite(Sprite.from(resetButton, 
				BOARD_WIDTH - Button.WIDTH - 10, 
				Button.HEIGHT + 10));
		
		// add the reset button sprite
		sprites.put(resetButton.getId(), resetButton.getSprite());
		
		// add the player sprites and reset their card associations
		for (Player player : players.values()) {
			player.removeCardAssociations();
			sprites.put(player.getId(), player.getSprite());
		}
		
	}
	
	public StateUpdateMessage toPrivateMessage(String userName) {
    	
    	StateUpdateMessage stateUpdateMessage = new StateUpdateMessage();
    	stateUpdateMessage.setPrivateMessage(true);
    	stateUpdateMessage.setSprites(new HashMap<String,Sprite>());
    	
    	Player player = players.get(userName);
    	for (Card card : player.getCards()) {
    		stateUpdateMessage.getSprites().put(card.getId(),card.getSprite());
    	}

    	return stateUpdateMessage;
	}
	
	public StateUpdateMessage toMessage() {
		    	
    	StateUpdateMessage stateUpdateMessage = new StateUpdateMessage();
    	
    	Map<String, Sprite> spriteMap = new HashMap<String,Sprite>();
    	
    	for (Entry<String, Sprite> entry : sprites.entrySet()) {
    		spriteMap.put(entry.getKey(), entry.getValue());
    	}
    	
    	// add player sprites and note the cards that are private
    	List<String> privateCardIdList = new ArrayList<String>();
    	for (Player player : players.values()) {
    		spriteMap.put(player.getId(), player.getSprite());
    		
    		for (Card card : player.getCards()) {
    			privateCardIdList.add(card.getId());
    		}
    	}
    	stateUpdateMessage.setSprites(new HashMap<String,Sprite>());
    	
    	//dont add the cards that are private in the message
    	Iterator<Entry<String,Sprite>> it = spriteMap.entrySet().iterator();
    	
    	while (it.hasNext()) {
    		Entry<String,Sprite> entry = it.next();
    		if (!privateCardIdList.contains(entry.getKey())) {
    			stateUpdateMessage.getSprites().put(entry.getKey(), entry.getValue());
    		}
    	}
    	
    	return stateUpdateMessage;
	}
	
	public void update(Message message) {

		
		Sprite targetSprite = sprites.get(message.getId());
		Object gameObject = targetSprite.getGameObject();

		
		if (message.isRightClick()) {
			if (gameObject instanceof Card) {
				// flip a card
				
				Card card = (Card) targetSprite.getGameObject();
				if (!doesCardBelongToOTherPlayer(message.getUsername(), card)) {
					card.flip();					
				}
			}
			if (gameObject instanceof Button) {
				//reset the game
				initializeGame();
			}
			
		} else if (deck.getSprite().equals(targetSprite)) {
			// draw a card from deck
			
			Card card = deck.draw();
			if (card != null) {
				card.setSprite(Sprite.from(card, message.getX(), message.getY()));
				sprites.put(card.getSprite().getId(), card.getSprite());
				
				//check if it is handed to a player
				shouldHandToPlayer(card);
			}
			
		} else {
				
			// drag a player 
			
			if (gameObject instanceof Player) {
				
				if (!((Player) gameObject).getPlayerName().equals(message.getUsername())) {
					//only the owner can move its own hand
					return;
				}
				
				for (Player player : players.values()) {
					if (!player.getPlayerName().equals(message.getUsername())) {
						
						if (collidesWith(player, message.getX(), Player.WIDTH, message.getY(), Player.HEIGHT)) {
							// cant put your hand on another hand
							return;
						}
					}
				}
				
				
				// drop the old cards
				Player player = (Player) targetSprite.getGameObject();
				player.removeCardAssociations();
				
				// drag the player
				targetSprite.setX(message.getX());
				targetSprite.setY(message.getY());
				
				// iterate all cards and see if there is a collision at the new position
				// adopt the collision cards if any
				for (Sprite sprite : sprites.values()) {
					if (sprite.getGameObject() instanceof Card 
							&& !doesCardBelongToOTherPlayer(player.getPlayerName(), (Card) sprite.getGameObject()) 
							&& collidesWith(targetSprite, sprite)) {
						player.add((Card) sprite.getGameObject());
					}
				}
				
			}
			
			// drag a card
//			if (targetSprite.getX() != message.getxInitial() || targetSprite.getY() != message.getyInitial()) {
//				// outdated move message
//				return;
//			}
			
			if (gameObject instanceof Card) {

				Card card = (Card) gameObject;
				// move the game object
				if (!doesCardBelongToOTherPlayer(message.getUsername(), card)) {
					targetSprite.setX(message.getX());
					targetSprite.setY(message.getY());				
				}
				
				// hand to a player if necessary
				shouldHandToPlayer(card);
			}
			
		}
		
	}
	
	private boolean doesCardBelongToOTherPlayer(String playerName, Card card) {
		for (Player player : players.values()) {
			if (!player.getPlayerName().equals(playerName)) {
				if (player.hasCardWithId(card.getId())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void shouldHandToPlayer(Card card) {
		boolean isHanded = false;
		for (Player player : players.values()) {
			
			// first remove the existing relation if any
			player.remove(card); 
			
			// add upto date relation
			if (!isHanded && collidesWith(player, card)) {
				player.add(card);
				isHanded = true; // only hand to one player
				
				//TODO improve here, player 1 has the advantage
			}
		}
	}
	
	public void registerPlayer(String userName) {
		// TODO add a private player area sprite moveable only by player
		if (!players.containsKey(userName)) {
			
			// create the player
			Player player = new Player(playerCount.incrementAndGet(),userName);
			Sprite playerSprite = Sprite.from(player, player.getPlayerNo()*Player.WIDTH + 20, player.getPlayerNo() * Player.HEIGHT + 20);
			player.setSprite(playerSprite);
			
			//add player to player and sprite list
			players.put(userName, player);
			sprites.put(player.getId(), playerSprite);
			
		}
		
	}
	
	private boolean collidesWith(IsSprite r1, IsSprite r2) {
		// simple collision check for axis aligned rectangles 
		return collidesWith(r1, r2.getX(), r2.getWidth(), r2.getY(), r2.getHeight()); 
	}
	
	private boolean collidesWith(IsSprite r1, int x2, int x2Width, int y2, int y2Height){
		// we have anchors in the middle of the objects
		return !(x2 > (r1.getX() + r1.getWidth()) || 

		           (x2 + x2Width) < r1.getX() || 

		           y2 > (r1.getY() + r1.getHeight()) ||

		           (y2 + y2Height) < r1.getY());

	}
	
	public void unregisterPlayer(String userName) {
		// TODO remove private player area sprite 
	}
}
