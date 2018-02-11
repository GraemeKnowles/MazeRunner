package mazeRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import graph.Dijkstra;
import graph.Node;
import system.Diagnostics.Console;

public class MazeRunner {

	public static void main(String[] args) {
		Maze maze = null;
		//tiny
		//small
		//normal
		//braid200
		//logo
		//combo400
		//perfect2k
		//vertical15k
		long startTime = System.currentTimeMillis();
		Path filename = Paths.get(args[0]);//"C:\\Users\\Graeme\\Desktop\\mazesolving\\mazesolving-master\\examples\\combo400.png");//args[0]);
		try {
			Console.printLn(System.out, "Loading file: " + filename);
			maze = new Maze(ImageIO.read(new File(filename.toString())));
		} catch (IOException e) {
			System.out.println(filename + " does not exist.");
			System.exit(0);
		}
		
		Dijkstra<MazeNode> alg = new Dijkstra<MazeNode>(maze.getGraph());
		List<MazeNode> path = nodeToMazeNode(alg.findPath());
		
		maze.drawPath(path);
		
		String outputFilename = filename.getParent().toString() + "\\" + filename.getFileName().toString().substring(0, filename.getFileName().toString().lastIndexOf('.')) + "_solved.png";
		
		try {
		    File outputfile = new File(outputFilename);
		    ImageIO.write(maze, "png", outputfile);
		} catch (IOException e) {
		}
		
		long endTime = System.currentTimeMillis();
		
		Console.printLn(System.out, "Total Time Taken: " + (endTime - startTime) + "ms");
		Console.printLn(System.out, "The shortest path has " + path.size() + " Nodes and is "+ path.get(0).getDistance() + " pixels long");
	}
	
	private static List<MazeNode> nodeToMazeNode(List<Node> nodes){
		ArrayList<MazeNode> mazeNodes = new ArrayList<MazeNode>();
		for(Node node : nodes){
			mazeNodes.add((MazeNode)node);
		}
		return mazeNodes;
	}
}
