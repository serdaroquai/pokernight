
function makeDraggable(target){  
	target.interactive = true;
	target.buttonMode = true;
	target.anchor.set(0.5);
	target.on('mousedown', onDragStart).on('mouseup', onDragEnd).on('mouseupoutside', onDragEnd).on('mousemove', onDragMove);	
}

function onDragStart(event) {
	// store a reference to the data
	// the reason for this is because of multitouch
	// we want to track the movement of this particular touch
	
	this.data = event.data;
	this.alpha = 0.5;
	this.dragging = true;
}

function onDragEnd() {
	
	this.alpha = 1;
	this.dragging = false;
	
	var finalPosition = this.data.getLocalPosition(this.parent);
	
	var message = JSON.stringify({'id':1, 'x':finalPosition.x, 'y':finalPosition.y});
	
	// send server wtf is going on
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