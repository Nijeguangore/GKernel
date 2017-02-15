package main;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.math.Quaternion;

import models.GObject;

public class GLDraw implements GLEventListener {

	private String[] vertSrc = {
			"#version 330\n",
			"layout (location = 0) in vec3 position;\n",
			"uniform mat4 translation;\n",
			"void main(){\n",
			"gl_Position = translation * vec4(position,1.0f);\n",
			"}\n"
	};
	private String[] fragSrc = {
			"#version 330\n",
			"out vec4 color;\n",
			"void main(){\n",
			"color = vec4(1.0f,1.0f,1.0f,1.0f);\n",
			"}\n"
	};
	ArrayList<GObject> objectsToRender = null;
	
	int programID = -1;
	int[] VAO = new int[1];
	@Override
	public void init(GLAutoDrawable drawable) {
		GL3 gl = drawable.getGL().getGL3();
		gl.glClearColor(0.0f, 0.24f, 0.23f, 1.0f);
		
		int vertShader = gl.glCreateShader(GL3.GL_VERTEX_SHADER);
		gl.glShaderSource(vertShader, vertSrc.length, vertSrc, null);
		gl.glCompileShader(vertShader);
		
		int fragShader = gl.glCreateShader(GL3.GL_FRAGMENT_SHADER);
		gl.glShaderSource(fragShader, fragSrc.length, fragSrc, null);
		gl.glCompileShader(fragShader);
		
		IntBuffer success = IntBuffer.allocate(1);
		ByteBuffer log = ByteBuffer.allocate(512);
		
		gl.glGetShaderiv(vertShader, GL3.GL_COMPILE_STATUS, success);
		if(success.get(0) == 0){
			gl.glGetShaderInfoLog(vertShader, 512, null, log);
			System.out.println(new String(log.array(),Charset.forName("UTF-8")));
		}
		else{
			System.out.println("Swing");
		}
		
		success.rewind();log.rewind();
		gl.glGetShaderiv(fragShader, GL3.GL_COMPILE_STATUS, success);
		if(success.get(0) == 0){
			gl.glGetShaderInfoLog(fragShader, 512, null, log);
			System.out.println(new String(log.array(),Charset.forName("UTF-8")));
		}
		else{
			System.out.println("Hit");
		}
		
		programID = gl.glCreateProgram();
		gl.glAttachShader(programID, vertShader);
		gl.glAttachShader(programID, fragShader);
		gl.glLinkProgram(programID);
		success.rewind();log.rewind();
		
		gl.glGetProgramiv(programID, GL3.GL_LINK_STATUS, success);
		if(success.get(0) == 0){
			gl.glGetProgramInfoLog(programID, 512, null, log);
			System.out.println(new String(log.array(),Charset.forName("UTF-8")));
		}
		else{
			System.out.println("Link Success");
		}
		
		prepObjects(gl);
		
		float[] triangle = {0.5f,0.0f,0.0f, 0.0f,0.5f,0.0f, -0.5f,0.0f,0.0f};
		int[] VBO = new int[1];
		gl.glGenVertexArrays(1, VAO,0);
		gl.glBindVertexArray(VAO[0]);
		gl.glGenBuffers(1, VBO,0);
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, VBO[0]);
		gl.glBufferData(GL3.GL_ARRAY_BUFFER, triangle.length*4, FloatBuffer.wrap(triangle), GL3.GL_STATIC_DRAW);
		gl.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
		gl.glBindVertexArray(0);
	}

	private void prepObjects(GL3 gl) {
		for(GObject gO : objectsToRender){
			gO.prepareSelf(gl);
		}
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL3 gl = drawable.getGL().getGL3();
		gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
		
		
		gl.glUseProgram(programID);
		
		float[] axis = new float[16];
		FloatUtil.makeIdentity(axis);
		int location = gl.glGetUniformLocation(programID, "translation");
		gl.glUniformMatrix4fv(location, 1, false, FloatBuffer.wrap(axis));
		
		
		gl.glBindVertexArray(objectsToRender.get(0).VAO[0]);
		gl.glPolygonMode(GL3.GL_FRONT_AND_BACK, GL3.GL_LINE);
		gl.glDrawElements(GL3.GL_TRIANGLES, 3, GL.GL_UNSIGNED_INT, 0);
		gl.glBindVertexArray(0);
		
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	public void setObjectList(ArrayList<GObject> renderables) {
		objectsToRender = renderables;		
	}

}
