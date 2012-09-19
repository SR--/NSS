package stigmergy3d01;

import java.util.ArrayList;

import toxi.geom.Vec3D;

public class Trail {

	Stigmergy3D01 p5;
	Agent a;
	ArrayList<Vec3D> trail;

	Trail (Stigmergy3D01 p5, Agent a) {
		this.p5 = p5;
		this.a = a;
		trail = new ArrayList<Vec3D>();
	}
	
	public void update() {
		//update the trail geom every 5 moves
		if(a.dropNum % p5.dropSpacing == 0){
			if(trail.size() < p5.trailLength){
				trail.add(new Vec3D(a.pos.x, a.pos.y, a.pos.z));
			}else{
				trail.remove(0);
			}
			a.dropNum=0;
		}
		a.dropNum++;
		
	}

	//render agent trail as trails
	public void renderTrail() {
			
			Vec3D ls = null;
			for (Vec3D l : trail){
				if(ls !=null){
					p5.strokeWeight(1);
					p5.stroke(255);
					p5.line (l.x, l.y, l.z, ls.x, ls.y, ls.z);
				}
				ls = l;
			}
		}
	
	//render agent trail as points
	public void renderAgent() {
		if (!a.free) p5.stroke(15, 100, 80);
		if (a.free) p5.stroke(310, 100, 100);
		p5.strokeWeight(3);
		p5.point(a.pos.x, a.pos.y, a.pos.z);
	}

}


