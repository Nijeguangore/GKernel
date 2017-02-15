package models;

import java.util.ArrayList;

public class FaceObject {
	
	ArrayList<Integer> faceIndices;
	
	public FaceObject() {
		faceIndices = new ArrayList<>();
	}
	
	public void addIndex(String string) {
		faceIndices.add(Integer.parseInt(string)-1);
		
	}

}
