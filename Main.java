package edu.agray.maze;

import edu.agray.maze.ui.Button;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Main extends Application {

//	GLOBAL VARIABLES
	public static final double WIDTH = 800, HEIGHT = 850;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	
//		Adding canvas to the stage to allow for rendering
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext g = canvas.getGraphicsContext2D();
		Group placeholder = new Group(canvas);
		
//		Adding the AnimationTimer class to tick and render
		Run run = new Run(g);
		run.start();
		
//		Create scene and add listeners
		Scene scene = new Scene(placeholder);
//		scene.setOnKeyReleased(run.getPlayer()); // player now listens for key presses to move
		for (Button b : run.getButtons()) {
			scene.setOnMouseMoved(b);
			scene.setOnMouseClicked(b);
		}
		
//		Setup for stage
		primaryStage.setScene(scene);
		primaryStage.setTitle("Alex Gray EPQ Maze AI Program");
		primaryStage.setWidth(WIDTH + 16);
		primaryStage.setHeight(HEIGHT + 39);
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(e -> {
			run.stop();
		});
		primaryStage.show();
		
	}

}
