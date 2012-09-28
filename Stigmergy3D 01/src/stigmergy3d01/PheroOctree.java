//CURRENTLY UNUSED
//package stigmergy3d01;
//
//import java.util.ArrayList;
//
//import toxi.geom.PointOctree;
//import toxi.geom.Vec3D;
//
//public class PheroOctree extends PointOctree{
//
//	Stigmergy3D01 p5;
//	ArrayList<Agent> pop;
//	int c;
//
//	PheroOctree(Vec3D o, float d, Stigmergy3D01 p5) {
//		super(o,d);
//		pop = new ArrayList<Agent>();
//		this.p5 = p5;
//		c = 0;
//	}
//
//	void run() {
//		//drawNode(this);
//		updateAgents();
//		updateTree();
//	}
//
//	void addAgent(Agent a){
//		addPoint(a);
//		pop.add(a);
//	}
//
//	void updateAgents(){
//		for (Agent a: pop){
//			a.run();
//		}
//	}
//
//	void updateTree(){
//		c++;
//		if(c%5==0){
//			empty();
//			for (Agent a: pop){
//				addPoint(a);
//			}
//		}
//	}
//
//	void drawNode(PointOctree n) {
//		if (n.getNumChildren() > 0) {
//			noFill();
//			stroke(n.getDepth(), 20);
//			pushMatrix(); 
//			translate(n.x, n.y, n.z);
//			box(n.getNodeSize());
//			popMatrix();
//			PointOctree[] childNodes=n.getChildren();
//			for (int i = 0; i < 8; i++) {
//				if(childNodes[i] != null) drawNode(childNodes[i]); 
//			}
//		}
//	}
//
//}
