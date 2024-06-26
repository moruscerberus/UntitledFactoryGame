package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.security.Key;
import java.util.IllegalFormatCodePointException;

import static java.lang.Math.clamp;

public class Camera {

	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;

	private Vector3f position = new Vector3f(0,0,0);
	private float pitch = 20;
	private float yaw  = 0;
	private float roll;


	public Camera(){}
	
	public void move() {
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizonalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		// this.yaw = 180 - (getPosition().y + angleAroundPlayer);

		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z -= 0.2f;
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				position.z -= 0.2f * 2;
			}
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z += 0.2f;
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				position.z += 0.2f * 2;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x += 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x -= 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
			position.y += 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			position.y -= 0.2f;
		}

	}

	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
	}

	private void calculatePitch() {
		if(Mouse.isButtonDown(1)) {
			float PitchChange = Mouse.getDY() * 0.1f;
			pitch -= PitchChange;
		}
	}

	private void calculateAngleAroundPlayer() {
		if (Mouse.isButtonDown(0)) {
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}



	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}

	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(yaw)));
	}
	
	

}
