package hello;

import java.util.HashSet;
import java.util.Set;

public class Player implements IsSprite{

	public static int WIDTH = 66;
	public static int HEIGHT = 63;
	private int playerNo;
	private String playerName;
	private Sprite sprite;
	private Set<Card> cards = new HashSet<Card>();
	
	public Player(int no, String playerName) {
		this.playerNo = no;
		this.playerName = playerName;
	}
	
	public void removeCardAssociations() {
		this.cards = new HashSet<Card>();
	}
	
	public boolean hasCardWithId(String id) {
		for (Card card : cards) {
			if (card.getId().equals(id))
				return true;
		}
		 return false;
	}
	
	public void add(Card card) {
		cards.add(card);
	}
	
	public boolean remove(Card card) {
		return cards.remove(card);
	}
	
	public Set<Card> getCards() {
		return cards;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public int getPlayerNo() {
		return playerNo;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	@Override
	public int getX() {
		return sprite.getX();
	}

	@Override
	public int getY() {
		return sprite.getY();
	}

	@Override
	public String getId() {
		return sprite.getId();
	}

	@Override
	public String getTexture() {
		return String.format("hand%s.png", playerNo % GameState.NUMBER_OF_HAND_IMAGES);
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}
	
	@Override
	public int getHeight() {
		return HEIGHT;
	}
}
