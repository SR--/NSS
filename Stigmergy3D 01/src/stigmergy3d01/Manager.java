package stigmergy3d01;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import toxi.geom.PointOctree;
import toxi.geom.Vec3D;

public class Manager {

	Stigmergy3D01 p5;

	ArrayList<Colony> colonys;
	ArrayList<Agent> agents;

	// octree dimensions
	float DIM = 500;
	float DIM2 = DIM/2;
	
	// sphere clip radius
	float RADIUS = 40;
	
	PointOctree phOctree;
	
	// ---------*Colony*---------------------------------------------//

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

	Manager(Stigmergy3D01 p5) {
		
		this.p5 = p5;
		colonys = new ArrayList<Colony>();
		agents = new ArrayList<Agent>();
		
		phOctree = new PointOctree(new Vec3D(-1,-1,-1).scaleSelf(DIM2),DIM);
		phOctree.setTreeAutoReduction(true); //Enables/disables auto reduction of branches after points have been deleted

	}

	// -----------------------------------colony function--------------//

	public void runColonies() {
		for (int i = 0; i < colonys.size(); i++) {
			Colony c = colonys.get(i);
			c.update();
			//c.render();
		}
	}

	// -----------------------------------agent function-----------------//

	public void runAgents() {
		for (int i = 0; i < agents.size(); i++) {
			Agent a = agents.get(i);
			if (!a.alive) agents.remove(i);
			else {
				a.update();
				a.make();
				a.render();
			}
		}
	}

	// -------------------------------pheromone function---------------//
	// TO FIX: mess with the types: Octree does nto want to return inheritated objects
//	public void runPhero() {
//		
//		Iterator<Vec3D> itP = phOctree.getPoints().iterator();
//		while (itP.hasNext()) {
//			Vec3D ph = itP.next();
//			if (!ph.alive) {
//				phOctree.remove(ph);
//				itP.remove();
//			} else {
//				ph.decay();
//				ph.render();
//			}
//		}
//	}
	
	// reset all---------------------------------------------------------
	
	public void reset() {
		colonys.clear();
		agents.clear();
		phOctree.empty();
		for (int i = 0; i < spawnLocs; i++) {
			int x = (int) (p5.random(-(float) (p5.lmt[0] * 0.8f), (float) (p5.lmt[0] * 0.8f)));
			int y = (int) (p5.random(-(float) (p5.lmt[1] * 0.8f), (float) (p5.lmt[1] * 0.8f)));
			int z = (int) (p5.random(-(float) (p5.lmt[2] * 0.8f), (float) (p5.lmt[2] * 0.8f)));
			int n = (int) (p5.random(6, 10));
			colonys.add(new Colony(p5, new Vec3D(x, y, z), n));
		}
	}

}
