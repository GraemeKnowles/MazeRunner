package mazeRunner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import graph.Graph;
import graph.IPathable;
import graph.Node;
import system.Diagnostics.Console;

public class Maze extends BufferedImage implements IPathable<MazeNode>{

	private Graph<MazeNode> graph = null;

	public Maze(BufferedImage image) {
		// copy image to this object
		super(image.getWidth(), image.getHeight(), TYPE_INT_ARGB);
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		generateGraph();
	}

	@Override
	public Graph<MazeNode> getGraph() {
		return graph;
	}

	private void generateGraph() {
		long startTime = System.currentTimeMillis();
		Console.printLn(System.out, "Generating graph.");
		ArrayList<ArrayList<MazeNode>> nodes = new ArrayList<ArrayList<MazeNode>>();
		// Generate nodes
		for (int yLoc = 0; yLoc < getHeight(); yLoc++) {
			ArrayList<MazeNode> currentLine = new ArrayList<MazeNode>();
			for (int xCoord = 0; xCoord < getWidth(); xCoord++) {
				if (getRGB(xCoord, yLoc) == Color.WHITE.getRGB()) {
					if (isIntersection(xCoord, yLoc)) {
						// At intersection
						MazeNode newNode = new MazeNode(xCoord, yLoc);
						if (isNorthPixelWhite(xCoord, yLoc)) {
							// find node north of this
							newNode.setNorth(findNodeNorth(newNode, nodes));
						}
						if (isWestPixelWhite(xCoord, yLoc)) {
							newNode.setWest(findNodeWest(newNode, currentLine));
						}
						// add the new node to the list
						currentLine.add(newNode);
					}
				}
			}
			nodes.add(currentLine);
			currentLine = new ArrayList<MazeNode>();
		}

		
		ArrayList<MazeNode> flattened = new ArrayList<MazeNode>();
		for(ArrayList<MazeNode> line : nodes){
			flattened.addAll(line);
		}
		graph = new Graph<MazeNode>(flattened);
		long endTime = System.currentTimeMillis();
		Console.printLn(System.out, "Generated " + flattened.size() + " Nodes and took " + (endTime - startTime) + "ms");
	}

	private boolean isIntersection(int x, int y) {
		int northSouth = 0;
		int eastWest = 0;
		if (isNorthPixelWhite(x, y)) {
			++northSouth;
		}
		if (isSouthPixelWhite(x, y)) {
			++northSouth;
		}
		if (isEastPixelWhite(x, y)) {
			++eastWest;
		}
		if (isWestPixelWhite(x, y)) {
			++eastWest;
		}
		if (northSouth > 0 && eastWest > 0) {
			return true;// basic intersection
		} else if (y == 0 && getRGB(x, y) == Color.WHITE.getRGB()) {
			return true;// start location
		} else if (y == getHeight() - 1 && getRGB(x, y) == Color.WHITE.getRGB()) {
			return true;// end location
		}
		return false;
	}

	private boolean isNorthPixelWhite(int x, int y) {
		int northY = y - 1;
		return northY >= 0 ? getRGB(x, northY) == Color.WHITE.getRGB() : false;
	}

	private boolean isSouthPixelWhite(int x, int y) {
		int southY = y + 1;
		return southY < getHeight() ? getRGB(x, southY) == Color.WHITE.getRGB() : false;
	}

	private boolean isEastPixelWhite(int x, int y) {
		int eastX = x + 1;
		return eastX < getWidth() ? getRGB(eastX, y) == Color.WHITE.getRGB() : false;
	}

	private boolean isWestPixelWhite(int x, int y) {
		int westX = x - 1;
		return westX >= 0 ? getRGB(westX, y) == Color.WHITE.getRGB() : false;
	}

	private MazeNode findNodeWest(MazeNode node, ArrayList<MazeNode> nodesInLine) {
		for (int x = node.getX(), y = node.getY(); x > 0;) {
			if (isWestPixelWhite(x, y)) {
				if (isIntersection(--x, y)) {
					int index = nodesInLine.indexOf(new MazeNode(x, y));
					if (index >= 0) {
						return nodesInLine.get(index);
					}
				}
			} else {
				return null;
			}
		}
		return null;
	}

	private MazeNode findNodeNorth(MazeNode node, ArrayList<ArrayList<MazeNode>> allNodes) {
		for (int y = node.getY(), x = node.getX(); y > 0;) {
			if (isNorthPixelWhite(x, y)) {
				if (isIntersection(x, --y)) {
					int index = allNodes.get(y).indexOf(new MazeNode(x, y));
					if (index >= 0) {
						return allNodes.get(y).get(index);
					}
				}
			} else {
				return null;
			}
		}
		return null;
	}
	
	public void drawPath(List<MazeNode> path){
		for(MazeNode node : path){
			MazeNode prev = (MazeNode) node.getPrevious();
			if(node.getPrevious() != null){
				drawLine(node, prev);
			}
		}
	}
	
	private void drawLine(MazeNode node1, MazeNode node2){
		if(node1.getX() == node2.getX()){
			drawVerticalLine(node1.getX(), node1.getY(), node2.getY());
		}else if(node1.getY() == node2.getY()){
			drawHorizontalLine(node1.getX(), node2.getX(), node1.getY());
		}
	}
	
	private void drawVerticalLine(int x, int y1, int y2){
		int top;
		int bottom;
		if(y1 < y2){
			top = y1;
			bottom = y2;
		} else {
			top = y2;
			bottom = y1;
		}
		
		for(int i = bottom; i >= top; --i){
			setRGB(x, i, Color.red.getRGB());
		}
	}
	
	private void drawHorizontalLine(int x1, int x2, int y){
		int left;
		int right;
		if(x1 < x2){
			left = x1;
			right = x2;
		} else {
			left = x2;
			right = x1;
		}
		
		for(int i = left; i < right; ++i){
			setRGB(i, y, Color.red.getRGB());
		}
	}
}
