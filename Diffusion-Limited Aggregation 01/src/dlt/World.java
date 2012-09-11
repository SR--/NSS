package dlt;

import processing.core.PImage;

public class World {

	Stig2D p5;
	PImage loadedImage; // image to store
	float[][] vals;
	int w, h;

	World(Stig2D p5, String toImg){

		this.p5 = p5;
		loadedImage = p5.loadImage(toImg); //load the source image
		loadedImage.resize(p5.width, p5.height);
		loadedImage.loadPixels(); //load the pixel array
		w = loadedImage.width;
		h = loadedImage.height;
		vals = new float[w][h];

		loadVals();
		//loadBlank(); //comment out to load the image
	}

	void loadVals(){
		//fill up the brightness values array
		for (int i = 0; i < w; i++){
			for (int j = 0; j < h; j++){
				vals[i][j] = p5.brightness(loadedImage.pixels[(j*w)+i]); 
			}
		} 
	}

	void loadBlank(){
		for (int i = 0;i<w;i++){
			for (int j = 0;j<h;j++){
				vals[i][j]=0; 
			}
		} 
	}

	//get a value from the image 
	float getAt(int x, int y){
		return vals[x][y];
	}

	//fade the image
	void fade(){
		for (int i = 0; i < w; i++){
			for (int j = 0; j < h; j++){
				if(vals[i][j] > p5.fadeSpeed) vals[i][j] = vals[i][j] - p5.fadeSpeed; 
			}
		}
	}


	//write a new value to the image
	void setAt(int x, int y, float v){
		v = Stig2D.constrain(v, 0, 255);
		vals[x][y] = v;
	}

	void modAt(int x, int y, float v){
		float mod = Stig2D.constrain(vals[x][y] + v, 0, 255);
		vals[x][y] = mod;
	}

	void drawImage(){
		p5.loadPixels();
		for (int i = 0; i < w; i++){
			for (int j = 0; j < h; j++){
				float b= Stig2D.constrain(vals[i][j], 0, 255);
				p5.pixels[(j*w)+i] = p5.color(b);
			}
		}
		p5.updatePixels();
	}

}
