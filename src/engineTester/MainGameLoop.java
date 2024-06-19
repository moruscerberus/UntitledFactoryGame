package engineTester;

import entities.Light;
import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.*;
import shaders.StaticShader;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.Sys.getTime;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		MasterRenderer renderer = new MasterRenderer();
		List<Entity> allCubes = new ArrayList<Entity>();

		Random random = new Random();
		RawModel model = OBJLoader.loadObjModel("cube", loader);
		TexturedModel cubeModel = new TexturedModel(model, new ModelTexture(
				loader.loadTexture("no_texture")));


		Light light = new Light(new Vector3f(3000, 2000, 3000), new Vector3f(1, 1, 1));

		for (int i = 0; i < 200; i++) {
			float x = random.nextFloat() * 100 - 50;
			float y = random.nextFloat() * 100 - 50;
			float z = random.nextFloat() * -300;
			allCubes.add(new Entity(cubeModel, new Vector3f(x, y, z), random.nextFloat() * 180f, random.nextFloat() * 180f, 0f, 1f));
		}

		Camera camera = new Camera();



		while(!Display.isCloseRequested()){
			camera.move();

			for (Entity cube : allCubes) {
				renderer.processEntity(cube);
			}

			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
