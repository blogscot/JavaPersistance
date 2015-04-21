package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import builder.Controller;

/**
 * 
 * The Java Persistence Application main class.
 * 
 * This main class bootstraps the JavaFX application, demonstrating
 * File IO, XML, Serialisation and SQLite Database persistence.
 * 
 * @author Iain Diamond
 * @version 10/04/2015
 * 
 */

public class Main extends Application {

  public static void main(String[] args) {
  	launch(args);
  }

  @Override
	public void start(Stage Stage) {
		try {
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Demo.fxml"));
		  Parent root = (Parent)loader.load();
			Scene scene = new Scene(root,370,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Controller controller = (Controller)loader.getController();
			controller.setStage(Stage);
			
			Stage.setTitle("Java Persistence");
			Stage.setResizable(false);
			Stage.setScene(scene);
			Stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
