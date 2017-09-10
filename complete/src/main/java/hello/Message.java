package hello;

public class Message {
    
	private String username;
	private String id;

	private Integer x;
    private Integer y;
    private boolean rightClick;
    private boolean welcome;

    public Message() {    }
    
    
    public boolean isWelcome() {
		return welcome;
	}
    
    public void setWelcome(boolean welcome) {
		this.welcome = welcome;
	}
    
    public boolean isRightClick() {
		return rightClick;
	}
    
    public void setRightClick(boolean rightClick) {
		this.rightClick = rightClick;
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
    
    public Integer getX() {
		return x;
	}
    
    public Integer  getY() {
		return y;
	}
    
    public void setX(Integer x) {
		this.x = x;
	}
    
    public void setY(Integer y) {
		this.y = y;
	}
    
    @Override
	public String toString() {
		return "Message [username=" + username + ", id=" + id + ", x=" + x + ", y=" + y + "]";
	}
}

   
