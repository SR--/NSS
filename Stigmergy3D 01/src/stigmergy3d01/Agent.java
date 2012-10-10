package stigmergy3d01;

import java.util.ArrayList;
import java.util.Iterator;
import processing.core.PApplet;
import toxi.geom.Vec3D;

public class Agent {

	Stigmergy3D01 p5;
	boolean	alive = true;
	Vec3D pos;
	Vec3D vel;
	Vec3D loc;
	float maxV = 3.2f;
	float maxF = 0.2f;
	int	dir;
	boolean	free = false;
	float freetime = 0;
	float dropNum;
	Trail tr;

	Agent(Stigmergy3D01 p5, Vec3D pos) {
		this.p5 = p5;
		this.pos = pos;
		vel = new Vec3D(p5.random(-1, 1), p5.random(-1, 1), p5.random(-1, 1));
		loc = pos.copy();
		p5.manager.agents.add(this);
		
		//control trails
		dropNum = 0;
		tr = new Trail(p5, this);;
	}

	void update() {
		if (PApplet.abs(pos.x) > p5.lmt[0] || PApplet.abs(pos.y) > p5.lmt[1] || PApplet.abs(pos.z) > p5.lmt[2]) alive = false;

		Vec3D acc = new Vec3D();
		Vec3D trac = new Vec3D();
		Vec3D wand = new Vec3D();
		wand = wander();
		if (p5.random(1) < 0.01f) freetime += p5.random(10, 20);
		if (freetime > 0) {
			free = true;
			freetime--;
		} else {
			free = false;
			trac = track(p5.manager.sight, p5.manager.view / 2);
		}

		trac.scaleSelf(3);
		wand.scaleSelf(1);
		acc.addSelf(trac);
		acc.addSelf(wand);
		vel.addSelf(acc);
		vel.limit(maxV);
		pos.addSelf(vel);

		tr.update();
	}
	
	public Vec3D track (float s, float v) {
		Vec3D vec = new Vec3D();
		String tMapCoord = getCoord(pos);
		float count = 0.0f;
		if (p5.manager.pheros.getCollection(tMapCoord) != null) {
			for (Phero p: (ArrayList<Phero>)p5.manager.pheros.getCollection(tMapCoord)) {
				float d = pos.distanceTo(p.pos);    // control length of perception
													// by Euclidean distance as
													// well as by the grid size,
													// optional
				if (d < s) {
					Vec3D move = (p.pos).sub(this.pos);
					float agl = vel.angleBetween(move, true);
					if ((agl < v) && (agl > -v)) {
						vec.addSelf(p);
						count++;
					}
				}
			}
			if (count > 0) {
				vec.scaleSelf(1 / count);
				vec.subSelf(this.pos);
				vec.limit(maxF);
			}
		}
		return vec;
	}
        	  

	public Vec3D wander() {
		Vec3D wand = new Vec3D(p5.random(-1, 1), p5.random(-1, 1), p5.random(-1, 1));
		wand.limit(maxF);
		return wand;
	}

	public void make() {
		int t = (int) (p5.manager.interval / maxV);
		if (p5.frameCount % t == 0 && pos.distanceTo(loc) > p5.manager.interval) {
			Vec3D tPos = pos.copy();
			String tCoord = getCoord(tPos);
			Phero p = new Phero(tPos, p5, tCoord);
			p5.manager.pheros.put(tCoord, p);
		}
	}

	public void render() {
		tr.renderTrail();
		//tr.renderAgent();

	}
	
	String getCoord(Vec3D p){
		String s = ""+PApplet.floor(p.x/p5.manager.gridRes)+PApplet.floor(p.y/p5.manager.gridRes)+PApplet.floor(p.z/p5.manager.gridRes);
		return s;
	}
}
