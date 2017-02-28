package mazeRunner;

import graph.PlottableNode;

public class MazeNode extends PlottableNode {
	public MazeNode(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean equals(Object obj){
		if(obj instanceof MazeNode){
			MazeNode node = (MazeNode) obj;
			if(node.getX() != getX() || node.getY() != getY()){				
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
}
