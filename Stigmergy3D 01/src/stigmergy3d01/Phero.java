package stigmergy3d01;

import toxi.geom.Vec3D;

public class Phero {
	
	Stigmergy3D01 p5;
	
	Vec3D	pos;
	float	mag		= 100f;
	boolean	alive	= true;

	Phero(Stigmergy3D01 p5, Vec3D pos) {
		this.p5 = p5;
		this.pos = pos;
	}

	public void decay() {
		mag -= p5.decayRate;
		if (mag < 0) alive = false;
	}

	public void render() {
		float B = mag;
		p5.stroke(0, 0, B);
		p5.strokeWeight(1);
		p5.point(pos.x, pos.y, pos.z);
	}

}
