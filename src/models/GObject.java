package models;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import com.jogamp.opengl.GL3;

public class GObject {
	private String objectName = null;
	ArrayList<VertexPoint> vertices = null;
	ArrayList<FaceObject> faceArray = null;
	public int[] VAO = new int[1];
	
	public GObject(String givenName) {
		objectName = givenName;
		vertices = new ArrayList<>();
		faceArray =  new ArrayList<>();
	}

	public int faceIndexCount(){
		return faceArray.size();
	}
	
	public void addVertex(VertexPoint vertex) {
		vertices.add(vertex);
	}

	public void addFaces(FaceObject newFace) {
		faceArray.add(newFace);
		
	}

	public void prepareSelf(GL3 gl) {
		float[] vertexData = {0.5f,0.0f,0.0f, 0.0f,0.5f,0.0f, -0.5f,0.0f,0.0f};
		
		int[] indicesData = {0,1,2};
		
		gl.glGenVertexArrays(1, VAO,0);
		int[] VBO = new int[1];
		int[] EBO = new int[1];
		gl.glGenBuffers(1, VBO,0);
		gl.glGenBuffers(1, EBO,0);
		gl.glBindVertexArray(VAO[0]);
		
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, VBO[0]);
		gl.glBufferData(GL3.GL_ARRAY_BUFFER, vertices.size()*12, FloatBuffer.wrap(vertexData), GL3.GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, EBO[0]);
		
		gl.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, indicesData.length*4, IntBuffer.wrap(indicesData), GL3.GL_STATIC_DRAW);
		
		gl.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
		gl.glBindVertexArray(0);
	}
	

}
