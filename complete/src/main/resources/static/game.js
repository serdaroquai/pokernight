//Aliases
var Container = PIXI.Container, autoDetectRenderer = PIXI.autoDetectRenderer, loader = PIXI.loader, resources = PIXI.loader.resources, Sprite = PIXI.Sprite;
TextureCache = PIXI.utils.TextureCache
Rectangle = PIXI.Rectangle

const ticker = new PIXI.ticker.Ticker();

var sprites = {}
var texture

// Create a Pixi stage and renderer and add the
// renderer.view to the DOM
var stage = new Container(), renderer = autoDetectRenderer(512, 512);
$('#stage').append(renderer.view);

// load an image and run the `setup` function when it's done
loader.add("cards.png").load(setup);

function setup() {
	

	// Create the sprites, add it to the stage, and render it
	texture = TextureCache["cards.png"];
	var rectangle = new Rectangle(0, 0, 44, 63);
	texture.frame = rectangle;

	// connect to server
	connect();
	
	// start the game loop
	gameLoop();
}

// Set the game's current state to `play`:
var state = play;

function gameLoop() {

	// Loop this function at 60 frames per second
	requestAnimationFrame(gameLoop);

	// Update the current game state:
	state();

	// Render the stage
	renderer.render(stage);

}

function play() {

	
}

function updateGame(message) {
	// todo destory non existent sprites
	
	message.sprites.forEach(function(entry) {
		
		if (sprites[entry.id] == null) {
			//create sprite if it does not exist
			var card = new GameObject(texture,entry.id);
			makeDraggable(card);
			sprites[entry.id] = card;
			stage.addChild(card);
		}
		
		// and update their location
		sprites[entry.id].position.x = entry.x;
		sprites[entry.id].position.y = entry.y;
		
		
	});
}

//The `randomInt` helper function
function randomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}
