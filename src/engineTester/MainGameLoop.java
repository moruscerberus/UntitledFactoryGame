package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;
import toolbox.Logger;

public class MainGameLoop {

	public static void main(String[] args) throws LWJGLException {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		
		RawModel model = OBJLoader.loadObjModel("tree", loader);;
		
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));

		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), new ModelTexture(loader.loadTexture("fern")));
		fern.getTexture().setHasTransparency();
		fern.getTexture().setUseFakeLighting(true);

		TexturedModel ironbar = new TexturedModel(OBJLoader.loadObjModel("ironbar", loader), new ModelTexture(loader.loadTexture("ironbar")));
		ironbar.getTexture().setReflectivity(.5f);
		ironbar.getTexture().setShineDamper(0.2f);
		ironbar.getTexture().setUseFakeLighting(false);

		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for(int i = 0; i < 500; i++){
			entities.add(new Entity(staticModel, new Vector3f(random.nextFloat() * 1200 - 400,0,random.nextFloat() * +600),0,0,0,3));
		}
		for(int i = 0; i < 50; i++){
			entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 1200 - 400,0,random.nextFloat() * +600),0,0,0,1));
		}
		for(int i = 0; i < 50; i++){
			entities.add(new Entity(ironbar, new Vector3f(random.nextFloat() * 1200 - 400,0,random.nextFloat() * +600),0,0,0,1));
		}




		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass2")));
		// Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("grass")));
		
		Camera camera = new Camera();	
		MasterRenderer renderer = new MasterRenderer();

		Logger.warning("MainGameLoop", "The Game Has Started");
		while(!Display.isCloseRequested()){
			camera.move();


			renderer.processTerrain(terrain);
			// renderer.processTerrain(terrain2);
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
