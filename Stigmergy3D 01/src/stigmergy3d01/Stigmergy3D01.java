package stigmergy3d01;

import java.util.List;

import processing.core.PApplet;
import peasy.*;
import toxi.geom.Vec3D;

public class Stigmergy3D01 extends PApplet {
	
	PeasyCam cam;
	int[] lmt = {500, 500, 500};

	Manager manager;
	
	//stats variables
	List<Vec3D> ocNodes;
	long t0;

	public void setup() {
		size(900, 600, OPENGL);
		textFont(createFont("SansSerif",18));
		smooth();
		colorMode(HSB, 360, 100, 100);
		cam();
		
		manager = new Manager(this);
		manager.reset();
	}

	public void draw() {
		background(0, 0, 0);
		//renderBound();

		t0 = System.nanoTime(); // note current time for the stats

		manager.runColonies();
		manager.runAgents();
		manager.runPhero();

		displayStats();
		println(frameRate);

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
	
	//screen stats-------------------------------------------
	public void displayStats(){
		
		float dt=(float)((System.nanoTime()-t0)*1e-6);
		cam.beginHUD();
		text("total agents: "+manager.agents.size(), 10, 30);

		ocNodes = manager.phOctree.getPoints();
		if(ocNodes != null) {
			int s = ocNodes.size();
			text("total octree nodes: "+s, 10, 70);
		}
		text("total pheromons: "+manager.pheros.size(), 10, 50);

		text("time: "+nf(dt,1,4)+"ms", 10, 90);
		cam.endHUD();
	}
}
