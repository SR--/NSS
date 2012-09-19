package stigmergy3d01;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import processing.core.PApplet;
import peasy.*;
import toxi.geom.Vec3D;

public class Stigmergy3D01 extends PApplet {
	
	PeasyCam cam;
	int[] lmt = {600, 500, 400};

	ArrayList<Colony> colonys = new ArrayList<Colony>();
	ArrayList<Agent> agents	= new ArrayList<Agent>();
	LinkedList<Phero> pheros = new LinkedList<Phero>();
	
	int spawnLocs = 70; //how many locations to initiate agents from?
	
	// ---------*Agent*---------------------------------------------//
	
	float sight	= 60f;
	float view = 2.2f;
	
	//agent trail parameters
	int dropSpacing = 2; //distance between trail points
	int trailLength  = 200;
	
	// ---------*Pheromone*-----------------------------------------//
	
	float decayRate	= 1.0f;
	float interval = 25f;

	public void setup() {
		size(900, 600, P3D);
		smooth();
		colorMode(HSB, 360, 100, 100);
		cam();
		reset();
	}

	public void draw() {
		background(0, 0, 0);
		//renderBound();
		
		// -----------------------------------colony function--------------//
		for (int i = 0; i < colonys.size(); i++) {
			Colony c = colonys.get(i);
			c.update();
			//c.render();
		}
		
		// -----------------------------------agent function-----------------//
		for (int i = 0; i < agents.size(); i++) {
			Agent a = agents.get(i);
			if (!a.alive) agents.remove(i);
			else {
				a.update();
				a.make();
				a.render();
			}
		}
		
		// -------------------------------pheromone function---------------//
		Iterator<Phero> itP = pheros.iterator();
		while (itP.hasNext()) {
			Phero ph = itP.next();
			if (!ph.alive) {
				itP.remove();
			} else {
				ph.decay();
				//ph.render();
			}
		}
	}
	
	public void reset() {
		colonys.clear();
		agents.clear();
		pheros.clear();
		for (int i = 0; i < spawnLocs; i++) {
			int x = (int) (random(-(float) (lmt[0] * 0.8f), (float) (lmt[0] * 0.8f)));
			int y = (int) (random(-(float) (lmt[1] * 0.8f), (float) (lmt[1] * 0.8f)));
			int z = (int) (random(-(float) (lmt[2] * 0.8f), (float) (lmt[2] * 0.8f)));
			int n = (int) (random(6, 10));
			colonys.add(new Colony(this, new Vec3D(x, y, z), n));
		}
	}
	
	public void cam() {
		cam = new PeasyCam(this, 0, 0, 0, 1200);
		cam.setMinimumDistance(1);
		cam.setMaximumDistance(4000);
		cam.setRotations(-0.73f, -0.27f, 0.30f);
	}

	private void renderBound() {
		noFill();
		stroke(210, 10, 30);
		strokeWeight(1);
		box(2 * lmt[0], 2 * lmt[1], 2 * lmt[2]);

		boolean ucs = false;
		if (ucs) {
			int t = 205;
			strokeWeight(1);
			stroke(0, 100, 80, t);
			line(0, 0, 0, height / 18, 0, 0); // x
			stroke(120, 100, 80, t);
			line(0, 0, 0, 0, height / 18, 0); // y
			stroke(240, 100, 80, t);
			line(0, 0, 0, 0, 0, height / 18); // z
		}
	}

	public void keyPressed() {
		if (key == 'r') reset();
		if (key == 'f') saveFrame("page-##");
	}
}
