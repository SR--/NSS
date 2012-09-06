package selfAvoidingWalk01;

import java.util.ArrayList;
import processing.core.PApplet;


public class SelfAvoidingWalk01 extends PApplet {
	
	ArrayList<Walker> walkers = new ArrayList<Walker>(); //collection of Walkers
	
	int step;
	int numWalkers = 1;
	boolean[][] visited; //collection of all visited places, shared between all walkers

	public void setup() {
		
		  background(255);
		  size(300, 300);
		  visited = new boolean[width][height];
		  
		  frameRate(20);
		  step = 15;

		  // Create a walker object and put into an ArrayList
		  for (int i = 0; i < numWalkers; i++){
		  walkers.add(new Walker(this, step));
		  }
		
	}

	public void draw() {
		
		for (int i = walkers.size()-1; i >= 0 ; i--){
		Walker w = walkers.get(i);
		w.run();
		}
	}
}
