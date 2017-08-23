//Aliases
var Container = PIXI.Container, autoDetectRenderer = PIXI.autoDetectRenderer, loader = PIXI.loader, resources = PIXI.loader.resources, Sprite = PIXI.Sprite;
TextureCache = PIXI.utils.TextureCache
Rectangle = PIXI.Rectangle

var hA, h2, h3, h4, h5, h6, h7, h8, h9, hT, hJ, hQ, hK;
var texture

// Create a Pixi stage and renderer and add the
// renderer.view to the DOM
var stage = new Container(), renderer = autoDetectRenderer(1024, 512);
$('#stage').append(renderer.view);

// load an image and run the `setup` function when it's done
loader.add("cards.png").load(setup);

function setup() {

	// Create the sprites, add it to the stage, and render it
	texture = TextureCache["cards.png"];
	
	hA=createCard(1,1);
	h2=createCard(8,1);
	h3=createCard(3,1);
	// TODO add other cards

	stage.addChild(hA);
	stage.addChild(h2);
	stage.addChild(h3);

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

function createCard(value, suit) {

	//set the bounds from texture atlas
	texture.frame = new Rectangle((value-1) * 44, (suit-1) * 63, 44, 63);

	// create our little card friend..
	var card = new Sprite(texture);

	// enable the card to be interactive... this will allow it to respond to
	// mouse and touch events
	card.interactive = true;

	// this button mode will mean the hand cursor appears when you roll over the
	// card with your mouse
	card.buttonMode = true;

	// center the card's anchor point
	card.anchor.set(0.5);


	// setup events for mouse + touch using
	// the pointer events
	card
//	.on('pointerdown', onDragStart)
//	.on('pointerup', onDragEnd)
//	.on('pointerupoutside', onDragEnd)
//	.on('pointermove', onDragMove);

	// For mouse-only events
	 .on('mousedown', onDragStart)
	 .on('mouseup', onDragEnd)
	 .on('mouseupoutside', onDragEnd)
	 .on('mousemove', onDragMove);

	// For touch-only events
	// .on('touchstart', onDragStart)
	// .on('touchend', onDragEnd)
	// .on('touchendoutside', onDragEnd)
	// .on('touchmove', onDragMove);

//	// move the sprite to its designated position
	card.x = randomInt(0,1000);
	card.y = randomInt(0,500);
	
	return card;
}

function onDragStart(event) {
	// store a reference to the data
	// the reason for this is because of multitouch
	// we want to track the movement of this particular touch
	
	console.log(event);
	
	this.data = event.data;
	this.alpha = 0.5;
	this.dragging = true;
}

function onDragEnd() {
	
	console.log(JSON.stringify(this.data));
	
	this.alpha = 1;
	this.dragging = false;

	
	var finalPosition = this.data.getLocalPosition(this.parent);
	
	var message = JSON.stringify({'id':1, 'x':50, 'y':50});
	sendAction(message);
	
	// set the interaction data to null
	this.data = null;
	
}


function onDragMove() {
	if (this.dragging) {
		var newPosition = this.data.getLocalPosition(this.parent);
		this.x = newPosition.x;
		this.y = newPosition.y;
	}
}

//The `randomInt` helper function
function randomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}