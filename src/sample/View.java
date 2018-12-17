package sample;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.model.ModelFacade;

public class View {
	
	@FXML
	private Pane rootPane;
	
	@FXML
	private Label enableTaxt;
	
	Image onRes = new Image("sample/res/on.png");
	ImageView on = new ImageView(onRes);
	
	ModelFacade model;
	
	@FXML
	void initialize() {
		init();
	}
	
	private void init() {
		
		rootPane.getChildren().add(on);
		on.setX(40);
		on.setY(40);
		on.setCursor(Cursor.HAND);
	}
	
	void setModel(ModelFacade model) {
		this.model = model;
		
//		model.addEventListener();
	}
	
	
	
	public Pane getRootPane() {
		return rootPane;
	}
	
}
