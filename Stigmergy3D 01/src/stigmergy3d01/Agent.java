package stigmergy3d01;

import java.util.ArrayList;
import java.util.Iterator;
import processing.core.PApplet;
import toxi.geom.Vec3D;

public class Agent extends Vec3D {
	

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
	
	//TO FIX: mess with types - how to force inherited types to be recognised?
	ArrayList<Vec3D> points = null;

	Agent(Stigmergy3D01 p5, Vec3D pos) {
		super(pos.x, pos.y, pos.z);
		this.p5 = p5;
		this.pos = pos;
		vel = new Vec3D(p5.random(-1, 1), p5.random(-1, 1), p5.random(-1, 1));
		loc = pos.copy();
		p5.manager.agents.add(this);

		//control trails
		dropNum = 0;
		tr = new Trail(p5, this);;

	}

	// update the agent state (position, movement)
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

	// look for pheromones and follow (using octree)
	public Vec3D track(float s, float v) {
		// s = sight, v = view/2
		Vec3D vec = new Vec3D();
		float count = 0.0f;

		points = p5.manager.phOctree.getPointsWithinSphere(this, p5.manager.RADIUS);

		if (points != null) {
			Iterator<Vec3D> it = points.iterator();
			while (it.hasNext()) {
				Vec3D cur = it.next();

				Vec3D move = (cur).sub(this.pos);
				float agl = vel.angleBetween(move, true);
				if ((agl < v) && (agl > -v)) {
					vec.addSelf(cur);
					count++;
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

	// make a new pheromone deposit (using octree)
	public void make() {

		int t = (int) (p5.manager.interval / maxV);
		if (p5.frameCount % t == 0 && pos.distanceTo(loc) > p5.manager.interval) {
			Phero curPh = new Phero(p5, pos.copy());
			p5.manager.phOctree.addPoint(curPh);

		}
	}

	public void render() {
		tr.renderTrail(); // render the trail left by the agent
		//tr.renderAgent(); // render the agent itself
	}
}
