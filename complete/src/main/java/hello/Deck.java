package hello;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Deck implements IsSprite {

	private Queue<Card> deck;
	private Random random;
	private Sprite sprite;

	public Deck(Random random) {
		
		this.random = random;
		
		this.deck = new LinkedList<Card>();

		createCards();
		shuffle();
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	private void createCards() {
		for (int suit = 0; suit < 4 ; suit++) {
			for (int rank = 0; rank < 13; rank++) {
				deck.add(new Card(suit,rank));
			}
		}
	}
	
	public Card draw() {
		return deck.poll();
	}
	
	@SuppressWarnings("unchecked")
	public void shuffle() {
		Collections.shuffle((List<Card>) deck, random);		
	}
	
	public boolean isEmpty() {
		return deck.isEmpty();
	}
	
	@Override
	public String getTexture() {
		return isEmpty() ? "emptyDeck.png" : "card.png";
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
	
}
