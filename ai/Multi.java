package edu.agray.maze.ai;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import edu.agray.maze.map.Map;
import edu.agray.maze.map.Tile;
import edu.agray.maze.util.Direction;
import edu.agray.maze.util.Explorer;
import javafx.scene.canvas.GraphicsContext;

public class Multi extends AI {

	private ArrayList<Explorer> scouts;
	private Queue<Tile> queue;
	private Tile backOfQueue;
	
	public Multi() {
		
		scouts = new ArrayList<Explorer>();
		queue = new LinkedList<Tile>();
		
	}
	
	@Override
	public void tick(Map map, Tile currentPosition) {
		
		if (queue.size() == 0) {
		
//			At the start, creates one explorer that will go on to make more explorers
			if (scouts.size() == 0) {

//				Works out all the junctions so that the explorers know where to go
				calculateJunctions(map);
//				Saves the starting tile
				backOfQueue = currentPosition;

//				Calculates all the "vertices" that the turtle can get to from its current position
				Tile firstStep = generateOptions(map, currentPosition)[0];
//				Creates the first explorer
				scouts.add(new Explorer(map, currentPosition, Direction.Calculate(currentPosition, firstStep)));

			}

			ArrayList<Explorer> children = new ArrayList<Explorer>();

//			Goes through every current explorer and creates new explorers in all possible directions
			for (Explorer scout : scouts) {
				children.addAll(scout.generateChildren(generateOptions(map, scout.getPosition())));
				
//				Check to see if any of the explorers have reached the goal.
//				The check for size = 1 is due to the fact that the generateChildren() function will only
//				return one tile if it detects the goal tile
				if (children.size() == 1 && children.get(0).getPosition().isGoal()) {
					loadQueue(map, children.get(0).getPath()); // loads the explorer's path into the movement queue
					scouts = null;
					return;
				}
				
			}

			scouts = children;
			
		}
		
	}

	@Override
	public Tile nextMove(Map map, Tile currentPosition) {

		if (!queue.isEmpty()) {
			return queue.poll();
		}
		
		return null;
		
	}

	public void calculateJunctions(Map map) {
		
		for (Tile[] column : map.getMasterArray()) {
			for (Tile row : column) {
				if (generateOptions(map, row).length > 2) {
					row.setJunction(true);
				}
			}
		}
		
	}
	
	private void loadQueue(Map map, ArrayList<Tile> path) {
		
//		Adds all the saved moves from the winning explorer to the queue
		for (Tile vertex : path) {
			trackRoute(map, backOfQueue, vertex);
			backOfQueue = vertex;
		}
		
	}
	
	private void trackRoute(Map map, Tile start, Tile end) {
		
//		Does not include start tile. Does include end tiles
		Direction direction = Direction.Calculate(start, end);
		Tile head = start;
		
		do {
			head = map.getTile(head, direction);
			queue.add(head);
		} while (!head.equals(end));
		
		for (Tile tile : queue) {
			tile.setScore(300);
		}
		
	}

	@Override
	public void render(GraphicsContext g) {

		if (scouts != null) {
			for (Explorer scout : scouts) {
				scout.render(g);
			}
		}

	}
	
}
