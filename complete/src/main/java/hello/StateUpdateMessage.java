package hello;

import java.util.Map;


public class StateUpdateMessage {

	private boolean privateMessage = false;
	private String content;
    private Map<String,Sprite> sprites;

    public StateUpdateMessage() {
    }

    public void setSprites(Map<String,Sprite> sprites) {
		this.sprites = sprites;
	}
    
    public Map<String,Sprite> getSprites() {
		return sprites;
	}
    
    public void setContent(String content) {
		this.content = content;
	}
    
    public String getContent() {
		return content;
	}
    
    public void setPrivateMessage(boolean privateMessage) {
		this.privateMessage = privateMessage;
	}
    
    public boolean isPrivateMessage() {
		return privateMessage;
	}

}
