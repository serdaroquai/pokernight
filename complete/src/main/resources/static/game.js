var app = new PIXI.Application(800, 600, {backgroundColor : 0x119911});
$('#stage').append(app.view);

//disable right click context menu
$('body').on('contextmenu', '#stage', function(e){ return false; });

//Aliases
const TextureCache = PIXI.utils.TextureCache


var sprites = {};
var privateSprites = {};
var texture;

PIXI.loader.add("sprites.json").load(onAssetsLoaded);

function onAssetsLoaded() {
	
	// connect to server
	connect();
	
}


function updateGame(message) {
	
	// iterate through the existing sprites to get rid of outdated ones,
	// todo open for improvement
	
	if (message.privateMessage) {
		
		var tempPrivateSprites = {};
		for (var key in privateSprites) {
			// remove if it does not exist in message
			if (message.sprites[key] == null) {
				app.stage.removeChild(privateSprites[key]);	
			} else {
				tempPrivateSprites[key] = privateSprites[key];
			}
		}
		
		privateSprites = tempPrivateSprites;

		//now create and update the existing
		
		for (var key in message.sprites) {
			var entry = message.sprites[key];

			if (privateSprites[entry.id] == null) {
				//create sprite if it does not exist
				var gameObject = new GameObject(entry.texture,entry.id);
				makeDraggable(gameObject);
				privateSprites[entry.id] = gameObject;
				app.stage.addChild(gameObject);
			}
			
			// and update their location and texture
			privateSprites[entry.id].position.x = entry.x;
			privateSprites[entry.id].position.y = entry.y;
			privateSprites[entry.id].texture = TextureCache[entry.texture];
		}
		
	} else {
		
		var tempSprites = {};
		for (var key in sprites) {
			// remove if it does not exist in message
			if (message.sprites[key] == null) {
				app.stage.removeChild(sprites[key]);				
			} else {
				tempSprites[key] = sprites[key];
			}
		}
		
		sprites = tempSprites;
		
		for (var key in message.sprites) {
			
			var entry = message.sprites[key];
			
			if (sprites[entry.id] == null) {
				//create sprite if it does not exist
				var gameObject = new GameObject(entry.texture,entry.id);
				makeDraggable(gameObject);
				sprites[entry.id] = gameObject;
				app.stage.addChild(gameObject);
			}
			
			sprites[entry.id].position.x = entry.x;
			sprites[entry.id].position.y = entry.y;
			sprites[entry.id].texture = TextureCache[entry.texture];

//			app.ticker.add(function () {
//				
//				var position = sprites[entry.id].position;
//				
//				position.x += (entry.x - position.x) * 0.1;
//				position.y += (entry.y - position.y) * 0.1;
//
//			    if (Math.abs(entry.x - position.x) < 1 
//			    		&& Math.abs(entry.y - position.y) < 1 ) {
//			    	position.x = entry.x;
//			    	position.y = entry.y;
//			    }
//				
//			});
			
		}
			
	}
	
	
	
}

