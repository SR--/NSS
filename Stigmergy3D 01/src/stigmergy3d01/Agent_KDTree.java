// KDtree implementation by Simon D. Levy
// For the library and usage examples see:
// http://home.wlu.edu/~levys/software/kd/


package stigmergy3d01;

import edu.wlu.cs.levy.CG.KDTree;
import java.util.List;

public class Agent_KDTree {

	Stigmergy3D01 p5;

	//supply KDTree arguments
	int kd_gsize = 24;
	int kd_xrad = 15;
	int kd_yrad = 15;
	int kd_zrad = 15;

	KDTree<Integer> kd;
	List<Integer> o;

	Agent_KDTree(Stigmergy3D01 p5){

		this.p5 = p5;

		// make a KD-tree
		kd = new KDTree<Integer>(3);

	}
	
	// add an agent to the tree
	public void put(double [] key, int n) {
		try {
			kd.insert(key, n);
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}
	
	// return the list of Agents within range
	public List<Integer> get() {
		try {
			// get objects in range of center
			double [] lo = {kd_gsize/2 - kd_xrad, kd_gsize/2 - kd_yrad};
			double [] hi = {kd_gsize/2 + kd_xrad, kd_gsize/2 + kd_yrad};
			o = kd.range(lo, hi);

			// dump them to stdout (for debugging, comment this out)
			for (int i : o) {
				System.out.println(i);
			}
		}
		catch (Exception e) {
			System.err.println(e);
		}
		return o;
	}
}
