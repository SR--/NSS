package selfAvoidingWalk3D01;

public class Walker {

	SelfAvoidingWalk3D01 p5;  
	int x, y, px, py;
	int step;
	boolean alive, inScreen;

	Walker(SelfAvoidingWalk3D01 p5, int step){
		this.p5 = p5;
		this.step = step;
		px = x = p5.width/2;
		py = y = p5.height/2;
		p5.visited[x][y] = true;
		alive = true;
		inScreen = true;
	}

	//render the walker
	void render() {
		p5.stroke(0);
		p5.strokeWeight(1);
		p5.line(px, py, x, y);

		//update previous position
		px = x;
		py = y;
	}

	void run() {
		
		checkBoundary();
		if (alive && inScreen) {
			walk();
			//checkPixels();
			render();
		}
	}
	// Check for self-intersection and move one step
	void walk() {

		// try until you find an available move
		while (true) {
			float r = p5.random(1);
			if (r < 0.25f && !p5.visited[x-step][y]) {
				x -= step; 
				break;
			}

			else if (r < 0.50f && !p5.visited[x+step][y]) {
				x += step; 

				break;
			}
			else if (r < 0.75f && !p5.visited[x][y-step]) { 
				y -= step; 
				break;
			}

			else if (r < 1.00f && !p5.visited[x][y+step]) { 
				y += step; 
				break;
			}
		}
		p5.visited[x][y] = true;

		//if can't find a position die
	}

	//check if the walker reached the screen boundary
	void checkBoundary() {
		if (x <= 0.0f || x >= p5.width-step || y <= 0.0f || y >= p5.height-step) {

			inScreen = false;
		}
		else {
			inScreen = true;
		}
	}

}

