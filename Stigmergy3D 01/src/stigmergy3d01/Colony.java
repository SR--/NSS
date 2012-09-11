package stigmergy3d01;

import java.util.ArrayList;
import toxi.geom.Vec3D;

public class Colony {

	Stigmergy3D01 p5;
	Vec3D pos;
	int count;
	ArrayList<Agent> agentList = new ArrayList<Agent>();

	Colony(Stigmergy3D01 p5, Vec3D pos, int n) {
		this.p5 = p5;
		this.pos = pos;
		count = n;
	}

	public void update() {
		for (int i = 0; i < agentList.size(); i++) {
			if (agentList.get(i).alive == false) agentList.remove(i);
		}
		if (agentList.size() < count) agentList.add(new Agent(p5, pos.copy()));
	}

	public void render() {
		p5.stroke(220, 20, 60);
		p5.strokeWeight(4);
		p5.point(pos.x, pos.y, pos.z);
	}
}


