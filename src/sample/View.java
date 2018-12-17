package sample;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.model.EventDispatcher;
import sample.model.ModelFacade;
import sample.model.SupplierEvents;
import sample.model.suppliers.SupplierState;

public class View {
	
	@FXML
	private Pane rootPane;
	
	@FXML
	private Label switchText_1;
	
	@FXML
	private Label switchText_2;
	
	@FXML
	private Label switchText_4;
	
	@FXML
	private Label switchText_3;
	
	ModelFacade model;
	
	Image onRes = new Image("sample/res/disable.png");
	ImageView switcherOn = new ImageView(onRes);
	Image onRes = new Image("sample/res/disable.png");
	ImageView switcherOn = new ImageView(onRes);
	Image onRes = new Image("sample/res/disable.png");
	ImageView switcherOn = new ImageView(onRes);
	Image onRes = new Image("sample/res/disable.png");
	ImageView switcherOn = new ImageView(onRes);
	
	Image onRes = new Image("sample/res/on.png");
	ImageView switcherOn = new ImageView(onRes);
	
	Image offRes = new Image("sample/res/off.png");
	ImageView switcherOffBg = new ImageView(offRes);
	
	int[][] switchBtnPos = {
			{ 47, 144},
			{ 47, 283},
			{ 36, 451},
			{308, 187}
	};
	
	@FXML
	void initialize() {
		init();
	}
	
	private void init() {
		
		rootPane.getChildren().add(switcherOffBg);
		switcherOffBg.setX(switchBtnPos[0][0]);
		switcherOffBg.setY(switchBtnPos[0][1]);
		switcherOffBg.setCursor(Cursor.HAND);
		switchText_1.setText("off");
		
	}
	
	void setModel(ModelFacade model) {
		this.model = model;


		EventDispatcher listener = this::modelListener;
		model.addEventListener(listener);
	}
	
	void modelListener(String eventType) {
		System.out.println("View.model Listener| event: " + eventType);
		
		if (eventType.equals(SupplierEvents.SWITCH_ON.getValue())) {
			rootPane.getChildren().add(switcherOn);
			switcherOn.setX(switchBtnPos[0][0]);
			switcherOn.setY(switchBtnPos[0][1]);
			switcherOn.setDisable(true);
			switchText_1.setText("on");
		} else if (eventType.equals(SupplierEvents.SWITCH_OFF.getValue())) {
			rootPane.getChildren().remove(switcherOn);
			switchText_1.setText("off");
		}
	}
	
	
	
	public Pane getRootPane() {
		return rootPane;
	}
	
}
