package graph;

import java.util.ArrayList;
import java.util.Iterator;

public class Graph<T extends Node> implements Iterable<T>{
	private T start = null;
	private T end = null;
	private ArrayList<T> nodes;
	
	
	public Graph(ArrayList<T> allNodes){
		nodes = allNodes;
		start = nodes.get(0);
		end = nodes.get(nodes.size() - 1);
	}
	
//	public Graph(Node start, Node end){
//		this.start = start;
//		this.end = end;
//	}
	
	public T getStart() {
		return start;
	}

	public T getEnd() {
		return end;
	}

	@Override
	public Iterator<T> iterator() {
		return nodes.iterator();
	}
}
