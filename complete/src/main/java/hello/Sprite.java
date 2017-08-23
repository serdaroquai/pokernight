package hello;

public class Sprite {

	private int id;
	private int x;
	private int y;
	private boolean faceDown;
	
	
	public Sprite() {
		// TODO Auto-generated constructor stub
	}
	
	public Sprite(int id, int x, int y, boolean faceDown) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.faceDown = faceDown;
	}




	public int getId() {
		return id;
	}


	public void setId(int id) {
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


	public boolean isFaceDown() {
		return faceDown;
	}


	public void setFaceDown(boolean faceDown) {
		this.faceDown = faceDown;
	}
	
	
}
