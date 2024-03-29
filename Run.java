package edu.agray.maze;

import java.util.ArrayList;

import edu.agray.maze.entities.Entity;
import edu.agray.maze.map.BlankMap;
import edu.agray.maze.map.Map;
import edu.agray.maze.ui.AStarButton;
import edu.agray.maze.ui.Button;
import edu.agray.maze.ui.MultiButton;
import edu.agray.maze.ui.PlayerButton;
import edu.agray.maze.ui.ScorerButton;
import edu.agray.maze.util.MapHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class Run extends AnimationTimer {

	private GraphicsContext g;
	private MapHandler mapHandler;
	private ArrayList<Entity> entities;
	private Button[] buttons;
	private long lastTime;
	
	public Run(GraphicsContext g) {
		
		super();
		this.g = g;
		
		mapHandler = new MapHandler(new BlankMap(40, 40));
		entities = new ArrayList<Entity>();
		lastTime = System.currentTimeMillis();
		
		buttons = new Button[4];
		
		buttons[0] = new PlayerButton(mapHandler, 0, Main.WIDTH, Main.WIDTH/4, Main.HEIGHT - Main.WIDTH);
		buttons[1] = new ScorerButton(mapHandler, Main.WIDTH/4, Main.WIDTH, Main.WIDTH/4, Main.HEIGHT - Main.WIDTH);
		buttons[2] = new MultiButton(mapHandler, 2*Main.WIDTH/4, Main.WIDTH, Main.WIDTH/4, Main.HEIGHT - Main.WIDTH);
		buttons[3] = new AStarButton(mapHandler, 3*Main.WIDTH/4, Main.WIDTH, Main.WIDTH/4, Main.HEIGHT - Main.WIDTH);
		
	}

	@Override
	public void handle(long now) {
		
//		if (now - lastTime > 1_000_000L) {
			tick();
			render();
			lastTime = now;
//		}
	
	}

	private void tick() {
		
		for (Button b : buttons) {
			Entity fetched = b.getEntity();
			if (fetched != null) {
				entities.add(fetched);
			}
		}
		
		for (Entity e : entities) {
			if (e != null) {
				e.tick();
			}
		}
		
	}
	
	private void render() {
		
		g.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
		
		mapHandler.getMap().render(g);
		
		for (Button b : buttons) {
			b.render(g);
		}
		
		for (Entity e : entities) {
			if (e != null) {
				e.render(g);
			}
		}
		
	}
	
	public Map getMap() {
		return mapHandler.getMap();
	}

	public void setMap(Map map) {
		mapHandler.setMap(map);
	}
	
	public MapHandler getMapHandler() {
		return mapHandler;
	}

	public Button[] getButtons() {
		return buttons;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

}
