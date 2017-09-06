package hello;

public class Sprite implements IsSprite{

	private String id;
	private int x;
	private int y;
	private String texture;
	
	public Sprite(String id, int x, int y, String texture) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.texture = texture;
	}
	
	public String getTexture() {
		return texture;
	}
	
	public void setTexture(String texture) {
		this.texture = texture;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public static Sprite from(IsSprite hasTexture, int x, int y) {
		return new Sprite(GameState.generateId(), x, y, hasTexture.getTexture());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sprite other = (Sprite) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
