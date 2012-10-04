package stigmergy3d01;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.collections.map.MultiValueMap;

import toxi.geom.Vec3D;

public class Manager {

	Stigmergy3D01 p5;

	ArrayList<Colony> colonys;
	ArrayList<Agent> agents;
	MultiValueMap pheros;

	int gridRes = 100;

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
		agents	= new ArrayList<Agent>();
		pheros = new MultiValueMap();

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
	public void runPhero() {
		Iterator<Phero> itP = pheros.values().iterator();
		while (itP.hasNext()) {
			Phero ph = itP.next();
			if (!ph.alive) {
				itP.remove();
			} else {
				//ph.run(); //this is needed to update th
				ph.decay();
				//ph.render();
			}
		}

	}


	// reset all---------------------------------------------------------

	public void reset() {
		colonys.clear();
		agents.clear();
		pheros.clear();
		for (int i = 0; i < spawnLocs; i++) {
			int x = (int) (p5.random(-(float) (p5.lmt[0] * 0.8f), (float) (p5.lmt[0] * 0.8f)));
			int y = (int) (p5.random(-(float) (p5.lmt[1] * 0.8f), (float) (p5.lmt[1] * 0.8f)));
			int z = (int) (p5.random(-(float) (p5.lmt[2] * 0.8f), (float) (p5.lmt[2] * 0.8f)));
			int n = (int) (p5.random(6, 10));
			colonys.add(new Colony(p5, new Vec3D(x, y, z), n));
		}
	}

}
