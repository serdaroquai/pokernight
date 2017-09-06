package hello;

public class Message {
    
	private String username;
	private String id;

	private int x;
    private int y;
    private boolean ctrlDown;

    public Message() {    }
    
    public boolean isCtrlDown() {
		return ctrlDown;
	}
    
    public void setCtrlDown(boolean ctrlDown) {
		this.ctrlDown = ctrlDown;
	}
    
    public String getUsername() {
		return username;
	}
    
    public void setUsername(String username) {
		this.username = username;
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

   
