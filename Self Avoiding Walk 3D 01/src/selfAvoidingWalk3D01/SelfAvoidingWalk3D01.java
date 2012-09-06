package selfAvoidingWalk3D01;

import java.util.ArrayList;
import processing.core.PApplet;
import peasy.*;


public class SelfAvoidingWalk3D01 extends PApplet {
	
	PeasyCam cam;
	ArrayList<Walker> walkers = new ArrayList<Walker>(); //collection of Walkers
	
	int step;
	int numWalkers = 1;
	boolean[][] visited; //collection of all visited places, shared between all walkers

	public void setup() {
		
		  
		
		  background(255);
		  noFill();
		  size(400, 400, P3D);
		  
		  cam = new PeasyCam(this, 100);
		  
		  visited = new boolean[width][height];
		  
		  frameRate(20);
		  step = 15;

		  // Create a walker object and put into an ArrayList
		  for (int i = 0; i < numWalkers; i++){
		  walkers.add(new Walker(this, step));
		  }
		  rect(0, 0, 100, 200);
		
	}

	public void draw() {
		
		for (int i = walkers.size()-1; i >= 0 ; i--){
		Walker w = walkers.get(i);
		w.run();
		}
	}
}
