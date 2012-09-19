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

	Manager manager;

	public void setup() {
		size(900, 600, P3D);
		smooth();
		colorMode(HSB, 360, 100, 100);
		cam();
		
		manager = new Manager(this);
		manager.reset();
		
	}

	public void draw() {
		background(0, 0, 0);
		//renderBound();
		
		manager.makeColonies();
		manager.makeAgents();
		manager.makePhero();
		
	}
	
	// make and setup camera----------------------------------
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
		if (key == 'r') manager.reset();
		if (key == 'f') saveFrame("page-##");
	}
}
