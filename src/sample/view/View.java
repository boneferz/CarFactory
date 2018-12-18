package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.model.CustomEvent;
import sample.model.EventDispatcher;
import sample.model.ModelFacade;
import sample.model.SupplierEvents;
import sample.model.suppliers.EnginesSupplier;

public class View {
	
	@FXML
	private Pane rootPane;
	
	@FXML
	private ImageView switch_2;
	
	@FXML
	private Label switchText_1;
	
	@FXML
	private Label switchText_2;
	
	@FXML
	private Label total_1;
	
	@FXML
	private Label total_3;
	
	@FXML
	private ImageView switch_4;
	
	@FXML
	private Label switchText_4;
	
	@FXML
	private ImageView switch_3;
	
	@FXML
	private Label switchText_3;
	
	@FXML
	private Label total_2;
	
	@FXML
	private Label total_4;
	
	@FXML
	private Label total_5;
	
	@FXML
	private ImageView off_2;
	
	@FXML
	private ImageView off_3;
	
	@FXML
	private ImageView off_4;
	
	@FXML
	private ImageView off_1;
	
	@FXML
	private ImageView switch_1;
	
	ModelFacade model;
	
	EngineSupplier supplier;
	public EngineSupplier[] suppliers = new EngineSupplier[4];
	ImageView[] switchArr = new ImageView[4];
	Label[] switchTextArr = new Label[4];
	ImageView[] offIconArr = new ImageView[4];
	Label[] totalArr = new Label[4];
	
	@FXML
	void initialize() {
		
		switchArr[0] = switch_1;
		switchArr[1] = switch_2;
		switchArr[2] = switch_3;
		switchArr[3] = switch_4;
		
		switchTextArr[0] = switchText_1;
		switchTextArr[1] = switchText_2;
		switchTextArr[2] = switchText_3;
		switchTextArr[3] = switchText_4;
		
		offIconArr[0] = off_1;
		offIconArr[1] = off_2;
		offIconArr[2] = off_3;
		offIconArr[3] = off_4;
		
		totalArr[0] = total_1;
		totalArr[1] = total_2;
		totalArr[2] = total_3;
		totalArr[3] = total_4;
		
		for (int i = 0; i < suppliers.length; i++) {
			supplier = new EngineSupplier(
					rootPane, switchArr[i], switchTextArr[i], offIconArr[i], totalArr[i]);
			suppliers[i] = supplier;
		}
		
	}
	
	public void setModel(ModelFacade model) {
		this.model = model;

		EventDispatcher listener = this::modelListener;
		model.addEventListener(listener);
	}
	
	void modelListener(CustomEvent event) {
		// suppliers
		int index = ((EnginesSupplier) event.getSource()).index;
		int total = ((EnginesSupplier) event.getSource()).total;
		
		switch (event.getType()) {
			case SWITCH_ON:
				suppliers[index].on();
				break;
				
			case SWITCH_OFF:
				suppliers[index].off();
				break;
				
			case MADE:
				suppliers[index].made(total);
				break;
		}
	}
	
	public Pane getRootPane() {
		return rootPane;
	}
	
}
