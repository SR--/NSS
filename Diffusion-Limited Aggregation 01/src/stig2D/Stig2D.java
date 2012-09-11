package stig2D;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;


public class Stig2D extends PApplet {

	World world;
	ArrayList<Mover> pop = new ArrayList<Mover>();

	//Mover global properties
	int numMovers = 5000;
	int searchRad = 5;
	float maxSpeed = 1.0f;

	//World global properties
	float fadeSpeed = 0.5f;

	public void setup() {

		size(800,800);
		smooth();

		world = new World(this, "mapi.png");

		//populate the sketch
		for (int i = 0; i < numMovers; i++){
			Mover m = new Mover(this, new PVector(random(width), random(height)), new PVector(random(-maxSpeed, maxSpeed),random(-maxSpeed, maxSpeed)), 1);
			pop.add(m); 
		}
	}

	public void draw() {

		background(255);
		world.drawImage();
		world.fade();
		//run the pop
		for (Mover m:pop){
			m.run(); 
		}
	}
}
