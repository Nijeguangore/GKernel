package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

import com.jogamp.opengl.awt.GLCanvas;

import models.FaceObject;
import models.GObject;
import models.VertexPoint;

public class Starter {
	public static void main(String[] args){
		JFrame topFrame = new JFrame();
		topFrame.setSize(1200, 900);
		
		GLCanvas contentCanvas = new GLCanvas();
		
		ArrayList<GObject> renderables = new ArrayList<>();
		try {
			loadObjects(renderables);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GLDraw renderer = new GLDraw();
		renderer.setObjectList(renderables);
		contentCanvas.addGLEventListener(renderer);
		
		topFrame.getContentPane().add(contentCanvas);
		topFrame.setVisible(true);
	}
	
	public static void loadObjects(ArrayList<GObject> objectList) throws FileNotFoundException{
		File projectDirectory = new File("./Models");
		for(File f : projectDirectory.listFiles()){
			String[] fileName_fileExtension = f.getName().split("\\.");
			Scanner objectScanner = new Scanner(f);
			
			if(fileName_fileExtension[1].equals("obj")){
				GObject newObject = new GObject(fileName_fileExtension[0]);
				while(objectScanner.hasNextLine()){
					String objectToken = objectScanner.nextLine();
					String[] objectLineParts = objectToken.split(" ");
					if(objectLineParts[0].equals("v")){
						VertexPoint vertex = new VertexPoint();
						vertex.setX(Float.parseFloat(objectLineParts[1]));
						vertex.setY(Float.parseFloat(objectLineParts[2]));
						vertex.setZ(Float.parseFloat(objectLineParts[3]));
						newObject.addVertex(vertex);
					}
					else if(objectLineParts[0].equals("f")){
						FaceObject newFace = new FaceObject();
						newFace.addIndex(objectLineParts[1].split("//")[0]);
						newFace.addIndex(objectLineParts[2].split("//")[0]);
						newFace.addIndex(objectLineParts[3].split("//")[0]);
						newObject.addFaces(newFace);
					}
				}
				objectList.add(newObject);
			}
			
		}
	}
}
