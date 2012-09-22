package stigmergy3d01;

import toxi.geom.Vec3D;
import wblut.geom.core.WB_Point3d;

public class Phero extends WB_Point3d {

	Stigmergy3D01 p5;

	Vec3D pos;
	float mag = 100f;
	boolean alive = true;

	Phero(Stigmergy3D01 p5, Vec3D pos) {
		super(pos.x, pos.y, pos.z);
		this.p5 = p5;
		this.pos = pos;
	}

	public void run() {
		decay();
		render();
	}

	public void decay() {
		if (p5.frameCount % 10 == 0) {
			mag -= p5.manager.decayRate;
			if (mag < 0)
				alive = false;
		}
	}

	public void render() {
		float B = mag;
		p5.stroke(0, 0, B);
		p5.strokeWeight(1);
		p5.point(pos.x, pos.y, pos.z);
	}

}
