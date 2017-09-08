package hello;

public class Card implements IsSprite{

	private int suit;
	private int rank;
	private boolean faceDown;
	private Sprite sprite;
	
	private static String[] suits = {"h","s","d","c"};
	private static String[] ranks = {"A","2","3","4","5","6","7","8","9","T","J","Q","K"};
	
	public Card(int suit, int rank) {
		
		this.suit = suit;
		this.rank = rank;
		faceDown = true;
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public boolean isFaceDown() {
		return faceDown;
	}
	
	@Override
	public String toString() {
		return String.format("Card[%s%s],isFaceDown:%s", ranks[rank], suits[suit], faceDown);
	}

	@Override
	public String getTexture() {
		return isFaceDown() ? "card.png" : String.format("%s%s.png", ranks[rank], suits[suit]);
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
	
	public void flip() {
		faceDown = !faceDown;
	}
}
