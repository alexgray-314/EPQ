package edu.agray.maze.ai;

import java.util.ArrayList;

import edu.agray.maze.entities.Explorer;
import edu.agray.maze.map.Map;
import edu.agray.maze.map.Tile;
import edu.agray.maze.util.Direction;

public class Multi extends AI {

	private ArrayList<Explorer> scouts;
	
	public Multi() {
		
		scouts = new ArrayList<Explorer>();
		
	}
	
	@Override
	public void tick(Map map, Tile currentPosition) {
		
		if (scouts.size() == 0) {
			
//			At the start, creates one explorer that will go on to make more explorers
			
//			Works out direction that first explorer must move in
			Tile firstStep = generateOptions(map, currentPosition)[0];
			scouts.add(new Explorer(map, currentPosition, Direction.Calculate(currentPosition, firstStep)));
			
		}

		for (Explorer scout : scouts) {
			
			ArrayList<Explorer> recruits = scout.advance(generateOptions(map, currentPosition), scouts);
			if (recruits == null) {
				return;
			}
			scouts.addAll(recruits);
			
		}
		
	}
	
	@Override
	public Tile nextMove(Map map, Tile currentPosition) {
		// TODO Auto-generated method stub
		return null;
	}

}
