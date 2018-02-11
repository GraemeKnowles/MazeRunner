package graph;

public class Edge {
	private Node left;
	private Node right;
	private int weight = 1;
		
	public Edge(Node left, Node right){
		this.left = left;
		this.right = right;
		calculateWeight();
	}
	
	public Edge(Node left, Node right, int weight){
		this(left, right);
		this.weight = weight;
	}
	
	public Node getOther(Node passThisThis){
		return passThisThis == left ? right : left;
	}
	
	public void setOther(Node passThisThis, Node toSetTo){
		if(passThisThis == left){
			right = toSetTo;
		} else {
			left = toSetTo;
		}
		calculateWeight();
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	private void calculateWeight(){
		if(left == null || right == null){
			return;
		}
		
		if(PlottableNode.class.isAssignableFrom(left.getClass()) && PlottableNode.class.isAssignableFrom(right.getClass())){
			PlottableNode lft = (PlottableNode)left;
			PlottableNode rgt = (PlottableNode)right;
			if(lft.getX() == rgt.getX()){
				weight = Math.abs(lft.getY() - rgt.getY());
			} else {
				weight = Math.abs(lft.getX() - rgt.getX());
			}
		}
	}
}
