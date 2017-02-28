package graph;

import java.util.Iterator;
import java.util.LinkedList;

public class Node implements Iterable<Node>, Comparable<Node> {
	private Edge north = null;
	private Edge south = null;
	private Edge east = null;
	private Edge west = null;
	private int distance = Integer.MAX_VALUE;
	private boolean visited = false;
	private Node previous = null;

	public Node() {
	}

	public Node(Edge north, Edge south, Edge east, Edge west) {
		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
	}

	public Node(Node north, Node south, Node east, Node west) {
		this.north = new Edge(this, north);
		this.south = new Edge(this, south);
		this.east = new Edge(this, east);
		this.west = new Edge(this, west);
	}

	public int getNorthWeight() {
		return north.getWeight();
	}

	public Node getNorth() {
		if (north == null) {
			return null;
		}
		return north.getOther(this);
	}

	public void setNorth(Node north) {
		if (this.north == null) {
			this.north = new Edge(this, north);
		} else {
			this.north.setOther(this, north);
		}
		
		if(north == null){
			this.north.setOther(this, north);
		}
		
		if (getNorth() != null && north.getSouth() == null) {
			north.setSouth(this);
		}
	}

	public int getSouthWeight() {
		return south.getWeight();
	}

	public Node getSouth() {
		if (south == null) {
			return null;
		}
		return south.getOther(this);
	}

	public void setSouth(Node south) {
		if (this.south == null) {
			this.south = new Edge(this, south);
		} else {
			this.south.setOther(this, south);
		}
		
		if(south == null){
			this.south.setOther(this, south);
		}
		
		if (getSouth() != null && south.getNorth() == null) {
			south.setNorth(this);
		}
	}

	public int getEastWeight() {
		return east.getWeight();
	}

	public Node getEast() {
		if (east == null) {
			return null;
		}
		return east.getOther(this);
	}

	public void setEast(Node east) {		
		if (this.east == null) {
			this.east = new Edge(this, east);
		} else {
			this.east.setOther(this, east);
		}
		
		if(east == null){
			this.east.setOther(this, east);
		}
		
		if (getEast() != null && east.getWest() == null) {
			east.setWest(this);
		}
	}

	public int getWestWeight() {
		return west.getWeight();
	}

	public Node getWest() {
		if (west == null) {
			return null;
		}
		return west.getOther(this);
	}

	public void setWest(Node west) {
		if (this.west == null) {
			this.west = new Edge(this, west);
		} else {
			this.west.setOther(this, west);
		}
		
		if(west == null){
			this.west.setOther(this, west);
		}
		
		if (getWest() != null && west.getEast() == null) {
			west.setEast(this);
		}
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public boolean hasBeenVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	@Override
	public Iterator<Node> iterator() {
		LinkedList<Node> nodes = new LinkedList<Node>();
		if(getNorth() != null){
			nodes.add(getNorth());
		}
		if(getSouth() != null){
			nodes.add(getSouth());
		}
		if(getEast() != null){
			nodes.add(getEast());
		}
		if(getWest() != null){
			nodes.add(getWest());
		}
		return nodes.iterator();
	}

	public int getWeightTo(Node other) {
		if (other == getNorth()) {
			return getNorthWeight();
		} else if (other == getSouth()) {
			return getSouthWeight();
		} else if (other == getEast()) {
			return getEastWeight();
		} else if (other == getWest()) {
			return getWestWeight();
		}
		return Integer.MAX_VALUE;
	}

	@Override
	public int compareTo(Node arg0) {
		return distance - arg0.getDistance();
	}
	
	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}
}
