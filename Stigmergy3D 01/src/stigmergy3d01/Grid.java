package stigmergy3d01;

import java.util.ArrayList;

import toxi.geom.Vec3D;

public class Grid {

	Stigmergy3D01 p5;

	ArrayList<Vec3D> all;
	ArrayList<Vec3D> [][][] grid;

	int size = 10; //size of each bin
	int cols, rows, slices;

	Grid(Stigmergy3D01 p5) {

		this.p5 = p5;
		all = new ArrayList<Vec3D>();  // Create the list
		cols = p5.lmt[0]/size;     // Calculate cols, rows and slices
		rows = p5.lmt[1]/size;
		slices = p5.lmt[2]/size;

		grid = new ArrayList[cols][rows][slices];
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				for (int k = 0; k < slices; k++) {
					grid[i][j][k] = new ArrayList<Vec3D>();
				}

			}

		}

	}

	//clear the bins (typically, at every frame)
	public void clear() {
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				for (int k = 0; k < slices; k++) {
					grid[i][j][k].clear();
				}
			}
		}
	}

	//add all objects to the bins according to their locations (typically, at every frame)
	public void update() {
		for (Vec3D t : all) {
			int x = (int) (t.x / size); 
			int y = (int) (t.y / size);
			int z = (int) (t.z / size);
			// It goes in 27 cells, every Object is tested against other Objects in their cells
			// as well as in the neighbouring cells
			for (int n = -1; n <= 1; n++) {
				for (int m = -1; m <= 1; m++) {
					for (int p = -1; p <= 1; p++) {
						if (x + n >= 0 && x + n < cols && y + m >= 0 && y + m < rows && z + p >= 0 && z + p < slices)
							grid[x + n][y + m][z + p].add(t);
					}
				}
			}
		}
	}
}

