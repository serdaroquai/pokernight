function GameObject(texture, id) {
	
	PIXI.Sprite.call(this, TextureCache[texture]);
  
	this.position.x = 0;
	this.position.y = 0;
	this.id = id;
}

GameObject.prototype = Object.create(PIXI.Sprite.prototype);