package dlt;

import processing.core.PVector;

public class Mover {

	Stig2D p5;
	PVector location;
	PVector velocity;
	PVector acceleration;
	PVector lastPos;
	float mass;
	int c;

	Mover(Stig2D p5, PVector location, PVector velocity, float mass){

		this.p5 = p5;
		this.mass = mass;
		this.location = location;
		this.velocity = velocity;
		acceleration = new PVector(0, 0);
		lastPos = location;
		c = 0;
	}

	void run(){

		update();
		checkEdges();
		checkWorld();
		modWorld();
		// display();
	}

	//move the object
	void update() {
		velocity.add(acceleration);
		velocity.limit(p5.maxSpeed);
		location.add(velocity);
		acceleration.mult(0);
		if (c % p5.searchRad ==0) lastPos = new PVector(location.x,location.y,location.z); 
	}

	//draw the object
	void display() {
		p5.ellipse(location.x, location.y, 2, 2);
	}

	void applyForce(PVector force) {
		PVector f = PVector.div(force,mass);
		acceleration.add(f);
	}

	//check the edge of the screen
	void checkEdges() {

		if (location.x > p5.width) {
			location.x = 0;
		} 
		else if (location.x < 0) {
			location.x = p5.width;
		}

		if (location.y > p5.height) {
			location.y = 0;
		} 
		else if (location.y < 0) {
			location.y = p5.height;
		}
	}

	//check the environment around the object
	void checkWorld(){
		PVector toBest = new PVector(); //create a temp vector to keep track of the direction of the best condition
		float maxV = 0;

		//loop through pixels
		for (int i = -p5.searchRad; i <= p5.searchRad; i++){
			for (int j = -p5.searchRad; j <= p5.searchRad; j++){
				if(!(i == 0 && j == 0)){
					//checks for edges
					int x = Stig2D.floor(location.x)+i;
					x = Stig2D.constrain(x, 0, p5.width-1);
					int y = Stig2D.floor(location.y)+j;
					y = Stig2D.constrain(y, 0, p5.height-1);

					//check to see if this is the smallest current value
					//scale by the distance to the value
					float v = p5.world.getAt(x, y);
					PVector toV = new PVector(i, j);

					//limit the angle of vision
					if(PVector.angleBetween(toV,velocity)<Stig2D.PI/2){

						//check to see if the current value is larger than 
						//the current best
						if((v) > maxV){

							//reset all our variables that keep track of the best option
							float d = toV.mag();
							toV.mult(1/d);

							toBest = toV;
							maxV = v;
						}
					}
				}
			}
		}

		//only effect if finding something above a tolerance
		if(maxV>20){
			applyForce(toBest);
		}
	}

	void modWorld(){
		//checks for edges
		if(lastPos.x < p5.width-1 && lastPos.y < p5.height-1 && lastPos.x > 0 && lastPos.y > 0) p5.world.modAt(Stig2D.floor(lastPos.x), Stig2D.floor(lastPos.y), 50);
	}

}


