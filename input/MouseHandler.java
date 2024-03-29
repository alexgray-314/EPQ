package edu.agray.maze.input;

import edu.agray.maze.map.Tile;
import edu.agray.maze.ui.Button;
import edu.agray.maze.util.MapHandler;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MouseHandler implements EventHandler<MouseEvent> {

	private Button[] buttons;
	private MapHandler mapHandler;
	private boolean mouseHeld;
	private Tile oldTile;
	
	public MouseHandler(Button[] buttons, MapHandler mapHandler) {
		this.buttons = buttons;
		this.mapHandler = mapHandler;
		mouseHeld = false;
	}

	@Override
	public void handle(MouseEvent event) {
		
		if (!mapHandler.getMap().isLocked()) {

			if (event.getButton().equals(MouseButton.PRIMARY)) {
			
				if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
					mouseHeld = true;
				} 
				if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
					mouseHeld = false;
				}
				
				
				Tile target = mapHandler.getMap().getTile(event.getX(), event.getY());
				if (target != null) {
					if (mouseHeld && !target.equals(oldTile) && !target.isGoal() && !target.isStart()) {
						if (event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
							target.setSolid(!oldTile.isSolid());
						}
						target.setSolid(!target.isSolid());
						oldTile = target;
					}
					
				}
				
			} else if (event.getButton().equals(MouseButton.SECONDARY)) {
				
				Tile target = mapHandler.getMap().getTile(event.getX(), event.getY());
				if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && !target.isSolid()) {
					target.setGoal(!target.isGoal());
					if (target.isGoal()) {
						mapHandler.getMap().setEndX(target.getX());
						mapHandler.getMap().setEndY(target.getY());
					}
				}
				
			} else if (event.getButton().equals(MouseButton.MIDDLE)) {
				
				Tile target = mapHandler.getMap().getTile(event.getX(), event.getY());
				if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && !target.isSolid()) {
					target.setStart(!target.isStart());
					if (target.isStart()) {
						mapHandler.getMap().setStartX(target.getX());
						mapHandler.getMap().setStartY(target.getY());
					}
				}
				
				
				
			}
			
		}
		
		for (Button b : buttons) {
			b.handle(event);
		}
		
	}

}