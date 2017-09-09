package hello;

public class Button implements IsSprite{

	private Sprite sprite;
	
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
		return "button.png";
	}
}
