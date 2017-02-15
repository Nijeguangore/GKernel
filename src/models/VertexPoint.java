package models;

public class VertexPoint {
	float[] vertexData = null;
	public VertexPoint() {
		vertexData = new float[3];
	}
	public void setX(float parseFloat) {
		vertexData[0] = parseFloat;
	}
	public void setY(float parseFloat) {
		vertexData[1] = parseFloat;		
	}
	public void setZ(float parseFloat) {
		vertexData[2] = parseFloat;			
	}
	
	@Override
	public String toString(){
		return "X:"+vertexData[0]+" Y:"+vertexData[1]+" Z:"+vertexData[2];
	}
}
