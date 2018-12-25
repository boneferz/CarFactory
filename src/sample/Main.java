package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.FacadeModel;

public class Main extends Application {
	
	private FacadeModel model;
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
	    
	    primaryStage.setTitle("Car FactoryCar");
	    primaryStage.setScene(new Scene(root, 857, 527));
	    primaryStage.setResizable(false);
	    primaryStage.sizeToScene();
	    primaryStage.setX(500);
	    primaryStage.setY(60);
	    primaryStage.show();
	    
	    // mvc
	    model = new FacadeModel();
	    view = UIview.getController();
	    controller = new Controller(view, model);
	
	    view.setModel(model);
	    model.createModel();
    }
	
}
