package hello;

import java.util.ArrayList;


public class StateUpdateMessage {

	private String content;
    private ArrayList<Sprite> sprites;

    public StateUpdateMessage() {
    }

    public void setSprites(ArrayList<Sprite> sprites) {
		this.sprites = sprites;
	}
    
    public ArrayList<Sprite> getSprites() {
		return sprites;
	}
    
    public void setContent(String content) {
		this.content = content;
	}
    
    public String getContent() {
		return content;
	}

}
