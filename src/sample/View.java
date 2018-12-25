package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.model.FacadeModel;
import sample.model.events.Custom_EventObject;
import sample.model.events.EventType;
import sample.model.events.Supplier_Events;
import sample.model.events.Warehouse_Events;
import sample.model.suppliers.Factory;
import sample.model.suppliers.warehause.Warehouse;
import sample.view.FactoryUI;
import sample.view.WarehouseUI;

import java.util.ArrayList;
import java.util.List;

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
	private ImageView SpeedSlider_1;
	
	@FXML
	private ImageView SpeedSlider_2;
	
	@FXML
	private ImageView SpeedSlider_3;
	
	@FXML
	private ImageView SpeedSlider_4;
	
	@FXML
	private ImageView SpeedSlider_5;
	
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
	private ImageView xBtn_2;
	
	@FXML
	private Label warehouseTotal_1;
	
	@FXML
	private ImageView xBtn_1;
	
	@FXML
	private ImageView xBtn_3;
	
	@FXML
	private Label total_4;
	
	@FXML
	private ImageView xBtn_4;
	
	@FXML
	private Label total_5;
	
	@FXML
	private ImageView off_2;
	
	@FXML
	private ImageView off_3;
	
	@FXML
	private ImageView off_1;
	
	@FXML
	private ImageView off_4;
	
	@FXML
	private ImageView totalLine_1;
	
	@FXML
	private ImageView totalLine_2;
	
	@FXML
	private ImageView totalLine_3;
	
	@FXML
	private ImageView totalLine_4;
	
	@FXML
	private ImageView switch_1;
	
	@FXML
	private Label warehouseTotal_2;
	
	@FXML
	private Label warehouseTotal_4;
	
	@FXML
	private Label warehouseTotal_3;
	
	public ImageView[] switchBtn = new ImageView[4];
	public Label[] switchText = new Label[4];
	public ImageView[] offIcon = new ImageView[4];
	public Label[] total = new Label[4];
	public Label[] warehouseTotal = new Label[4];
	public ImageView[] xBtn = new ImageView[4];
	public ImageView[] totalLines = new ImageView[4];
	public ImageView[] speedSlider = new ImageView[4];
	
	FacadeModel model;
	
	public List<FactoryUI> factoryUI = new ArrayList<>();
	public List<WarehouseUI> warehouseUI = new ArrayList<>();
	
	@FXML
	void initialize() {
		
		switchBtn[0] = switch_1;
		switchBtn[1] = switch_2;
		switchBtn[2] = switch_3;
		switchBtn[3] = switch_4;
		
		switchText[0] = switchText_1;
		switchText[1] = switchText_2;
		switchText[2] = switchText_3;
		switchText[3] = switchText_4;
		
		offIcon[0] = off_1;
		offIcon[1] = off_2;
		offIcon[2] = off_3;
		offIcon[3] = off_4;
		
		total[0] = total_1;
		total[1] = total_2;
		total[2] = total_3;
		total[3] = total_4;
		
		warehouseTotal[0] = warehouseTotal_1;
		warehouseTotal[1] = warehouseTotal_2;
		warehouseTotal[2] = warehouseTotal_3;
		warehouseTotal[3] = warehouseTotal_4;
		
		xBtn[0] = xBtn_1;
		xBtn[1] = xBtn_2;
		xBtn[2] = xBtn_3;
		xBtn[3] = xBtn_4;
		
		totalLines[0] = totalLine_1;
		totalLines[1] = totalLine_2;
		totalLines[2] = totalLine_3;
		totalLines[3] = totalLine_4;
		
		speedSlider[0] = SpeedSlider_1;
		speedSlider[1] = SpeedSlider_2;
		speedSlider[2] = SpeedSlider_3;
		speedSlider[3] = SpeedSlider_4;
		
		for (int i = 0; i < 4; i++) {
			factoryUI.add(
				new FactoryUI(rootPane,
					switchBtn[i], switchText[i], offIcon[i], total[i], speedSlider[i]));
		}
		
		for (int i = 0; i < 4; i++) {
			warehouseUI.add(
				new WarehouseUI(warehouseTotal[i], xBtn[i], totalLines[i]));
		}
	}
	
	public void setModel(FacadeModel model) {
		this.model = model;

		model.addEventListener(EventType.SUPPLIER, this::supplierListener);
		model.addEventListener(EventType.WAREHOUSE, this::warehouseListener);
	}
	
	void warehouseListener(Custom_EventObject event) {
		int index = ((Warehouse) event.getSource()).index;
		int total = ((Warehouse) event.getSource()).occupancy;
		
		switch ((Warehouse_Events) event.getEvent()) {
			case UPDATE:
				warehouseUI.get(index).totalUpdate(total);
				break;
		}
	}
	
	void supplierListener(Custom_EventObject event) {
		int index = ((Factory) event.getSource()).index;
		int total = ((Factory) event.getSource()).total;
		
		switch ((Supplier_Events) event.getEvent()) {
			case SWITCH_ON:
				factoryUI.get(index).on();
				break;
			case SWITCH_OFF:
				factoryUI.get(index).off();
				break;
				
			case CREATED:
				factoryUI.get(index).totalUpdate(total);
				break;
				
			case MOVE_SPEED_SLIDER:
				factoryUI.get(index).moveSpeedSlider(event.getSource());
				break;
			case INIT_SPEED_SLIDER:
				System.out.println(factoryUI);
				System.out.println(factoryUI.size());
				factoryUI.get(index).initSpeedSlider(event.getSource());
				break;
				
			case PAUSE:
				factoryUI.get(index).pause();
				break;
			case RESUME:
				factoryUI.get(index).resume();
				break;
				
			case PROBLEM:
				factoryUI.get(index).problem();
				break;
			case PROBLEM_FIXED:
				factoryUI.get(index).problemFixed();
				break;
		}
	}
	
	public Pane getRootPane() {
		return rootPane;
	}
	
}
