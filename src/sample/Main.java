package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.ModelFacade;

public class Main extends Application {
	
	private ModelFacade model;
	private View view;
	private Controller controller;
	
	public static void main(String[] args) {
		System.setProperty("prism.lcdtext", "false");
		launch(args);
	}
	
    @Override
    public void start(Stage primaryStage) throws Exception{
	    
	    FXMLLoader UIview = new FXMLLoader(getClass().getResource("sample.fxml"));
	    Parent root = UIview.load();
	    
	    primaryStage.setTitle("Car Factory");
	    primaryStage.setScene(new Scene(root, 857, 527));
	    primaryStage.setResizable(false);
	    primaryStage.sizeToScene();
	    primaryStage.setX(400);
	    primaryStage.setY(100);
	    primaryStage.show();
	    
	    // mvc
	    model = new ModelFacade();
	    view = UIview.getController();
	    view.setModel(model);
	    controller = new Controller(view, model);
	    
    }
	
}
