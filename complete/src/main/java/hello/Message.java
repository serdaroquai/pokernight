package hello;

public class Message {
    
	private String username;
	private int id;

	private int x;
    private int y;

    public Message() {    }
    
    
    public String getUsername() {
		return username;
	}
    
    public void setUsername(String username) {
		this.username = username;
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
    
    public int getY() {
		return y;
	}
    
    public void setX(int x) {
		this.x = x;
	}
    
    public void setY(int y) {
		this.y = y;
	}
    
    @Override
	public String toString() {
		return "Message [username=" + username + ", id=" + id + ", x=" + x + ", y=" + y + "]";
	}
}

   
