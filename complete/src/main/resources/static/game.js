//Aliases
var Container = PIXI.Container, autoDetectRenderer = PIXI.autoDetectRenderer, loader = PIXI.loader, resources = PIXI.loader.resources, Sprite = PIXI.Sprite;
TextureCache = PIXI.utils.TextureCache
Rectangle = PIXI.Rectangle

const ticker = new PIXI.ticker.Ticker();

var sprites = {}
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
	
	sprites[1] = createCard(1,1);
//	sprites[2] = createCard(2,1);
//	sprites[3] = createCard(3,1);
	
	// TODO add other cards
 
	stage.addChild(sprites[1]);
//	stage.addChild(sprites[2]);
//	stage.addChild(sprites[3]);

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

	texture = TextureCache["cards.png"];
	
	//set the bounds from texture atlas
	var rectangle = new Rectangle((value-1) * 44, (suit-1) * 63, 44, 63);
	texture.frame = rectangle;
	console.log(texture.frame);

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


	
	card
	 .on('mousedown', onDragStart)
	 .on('mouseup', onDragEnd)
	 .on('mouseupoutside', onDragEnd)
	 .on('mousemove', onDragMove);


	// move the sprite to its designated position
	card.x = randomInt(0,1000);
	card.y = randomInt(0,500);
	
	return card;
}

function createDragAndDropFor(target){  
	  target.interactive = true;
	  target.on("mousedown", function(e){
	    drag = target;
	  })
	  target.on("mouseup", function(e){
	    drag = false;
	  })
	  target.on("mousemove", function(e){
	    if(drag){
	      drag.position.x += e.data.originalEvent.movementX;
	      drag.position.y += e.data.originalEvent.movementY;
	    }
	  })
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
	
	var message = JSON.stringify({'id':1, 'x':finalPosition.x, 'y':finalPosition.y});
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

function updateGame(message) {
	message.sprites.forEach(function(entry) {
//		ticker.add(function() {
//
//			var id = entry.id;
//			
//			sprites[id].x += (entry.x - sprites[id].x) * 0.1;
//			sprites[id].y += (entry.y - sprites[id].y) * 0.1;
//
//		    if (Math.abs(sprites[entryid].x - entry.x) < 1) {
//		        reset();
//		    }
//		});
	    sprites[entry.id].x = entry.x;
	    sprites[entry.id].y = entry.y;
	});
}

//The `randomInt` helper function
function randomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}