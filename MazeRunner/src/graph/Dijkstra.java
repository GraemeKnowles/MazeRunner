package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import system.Diagnostics.Console;;

public class Dijkstra<T extends Node> {

	Graph<T> graphToTraverse;

	public Dijkstra(Graph<T> graph) {
		graphToTraverse = graph;
	}

	public List<Node> findPath() {
		Console.printLn(System.out, "Using Dijkstra to find the shortest path.");
		long startTime = System.currentTimeMillis();
		ArrayList<Node> path = new ArrayList<Node>();
		Node start = graphToTraverse.getStart();
		Node destination = graphToTraverse.getEnd();
		// 1. Assign to every node a tentative distance value: set it to
		// zero for our initial node and to infinity for all other nodes.
		start.setDistance(0);

		// 2. Set the initial node as current. Mark all other nodes
		// unvisited.
		Node current = start;
		current.setVisited(true);
		// Create a set of all the unvisited nodes called the unvisited set.
		PriorityQueue<Node> unvisited = new PriorityQueue<Node>();
		for (Node node : graphToTraverse) {
			unvisited.add(node);
		}
		long totalUnvisited = unvisited.size();
		double currentPercent = 0;
		// The start node has the lowest priority, so it gets removed from
		// the unvisited set immediately.
		while ((current = unvisited.poll()) != null) {
			double percent = 100 - ((double) unvisited.size() / totalUnvisited) * 100;
			if (percent > currentPercent + 10) {
				currentPercent = percent;
				Console.printLn(System.out, String.format("%.0f", currentPercent) + "% of Nodes Visited.");
			}

			// 3. For the current node, consider all of its unvisited
			// neighbors and calculate their tentative distances. Compare
			// the newly calculated tentative distance to the current
			// assigned value and assign the smaller one. For example, if
			// the current node A is marked with a distance of 6, and the
			// edge connecting it with a neighbor B has length 2, then the
			// distance to B (through A) will be 6 + 2 = 8. If B was
			// previously marked with a distance greater than 8 then change
			// it to 8. Otherwise, keep the current value.

			for (Node directionNode : current) {
				if (directionNode.hasBeenVisited()) {
					continue;
				}
				int distance = current.getDistance() + directionNode.getWeightTo(current);
				if (distance < directionNode.getDistance()) {
					unvisited.remove(directionNode);// Need to re-add the object
													// into the list to update
													// position
					directionNode.setDistance(distance);
					directionNode.setPrevious(current);
					unvisited.add(directionNode);
				}
			}
			// 4. When we are done considering all of the neighbors of the
			// current node, mark the current node as visited and remove it
			// from the unvisited set. A visited node will never be checked
			// again.
			current.setVisited(true);
			unvisited.remove(current);

			// 5. If the destination node has been marked visited (when
			// planning
			// a route between two specific nodes) or if the smallest
			// tentative
			// distance among the nodes in the unvisited set is infinity
			// (when
			// planning a complete traversal; occurs when there is no
			// connection
			// between the initial node and remaining unvisited nodes),
			if (destination.hasBeenVisited() || unvisited.element().getDistance() == Integer.MAX_VALUE) {
				break;
			}

			// 6. Otherwise, select the unvisited node that is marked with
			// the smallest tentative distance, set it as the new "current
			// node", and go back to step 3.
		}

		current = destination;
		while (current.getPrevious() != null) {
			path.add(current);
			current = current.getPrevious();
		}
		path.add(current);
		long endTime = System.currentTimeMillis();
		Console.printLn(System.out,
				"Pathfinding visited " + String.format("%.2f", 100 - ((double) unvisited.size() / totalUnvisited) * 100)
						+ "% of the Nodes and took " + (endTime - startTime) + "ms");
		return path;
	}
}
