package graph;

public class PlottableNode extends Node {

	private int x = 0;
	private int y = 0;

	public PlottableNode(int x, int y) {
		super((Edge)null, null, null, null);
		this.x = x;
		this.y = y;
	}
	
	public PlottableNode(int x, int y, Node north, Node south, Node east, Node west){
		this(x, y);
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
