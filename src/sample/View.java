package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.model.ModelFacade;
import sample.model.events.*;
import sample.model.factories.Factory;
import sample.model.factories.warehause.Warehouse;
import sample.view.DealerUI;
import sample.view.FactoryUI;
import sample.view.OfficeUI;
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
	private ImageView dealerIcon_4;
	
	@FXML
	private ImageView dealerIcon_6;
	
	@FXML
	private ImageView dealerIcon_5;
	
	@FXML
	private ImageView dealerIcon_1;
	
	@FXML
	private ImageView dealerIcon_3;
	
	@FXML
	private ImageView dealerIcon_2;
	
	@FXML
	private ImageView totalLine_1;
	
	@FXML
	private ImageView totalLine_2;
	
	@FXML
	private ImageView totalLine_3;
	
	@FXML
	private ImageView totalLine_4;
	
	@FXML
	private Label wTotal_1;
	
	@FXML
	private Label wCount_2;
	
	@FXML
	private Label wTotal_2;
	
	@FXML
	private Label wCount_3;
	
	@FXML
	private Label wTotal_3;
	
	@FXML
	private Label wCount_4;
	
	@FXML
	private Label wTotal_4;
	
	@FXML
	private Label wCount_5;
	
	@FXML
	private Label wTotal_5;
	
	@FXML
	private Label wCount_1;
	
	@FXML
	private ImageView switch_1;
	
	@FXML
	private Label warehouseTotal_2;
	
	@FXML
	private Label warehouseTotal_4;
	
	@FXML
	private Label warehouseTotal_3;
	
	@FXML
	private Label wCount_6;
	
	@FXML
	private Label wTotal_6;
	
	public ImageView[] switchBtn = new ImageView[4];
	public Label[] switchText = new Label[4];
	public ImageView[] offIcon = new ImageView[4];
	public Label[] total = new Label[4];
	public Label[] warehouseTotal = new Label[4];
	public ImageView[] xBtn = new ImageView[4];
	public ImageView[] totalLines = new ImageView[4];
	public ImageView[] speedSlider = new ImageView[4];
	
	ModelFacade model;
	
	public List<FactoryUI> factoryUI = new ArrayList<>();
	public List<WarehouseUI> warehouseUI = new ArrayList<>();
	public OfficeUI officeUI;
	public DealerUI dealerUI;
	
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
		
		officeUI = new OfficeUI();
		dealerUI = new DealerUI(rootPane, dealerIcon_1, wTotal_1, wCount_1);
	}
	
	public void setModel(ModelFacade model) {
		this.model = model;

		model.addEventListener(Event.FACTORIES, this::factoriesListener);
		model.addEventListener(Event.WAREHOUSE, this::warehouseListener);
		
		model.addEventListener(Event.DEALER, this::dealerListener);
		model.addEventListener(Event.OFFICE, this::officeListener);
		model.addEventListener(Event.FACTORY_CAR, this::factoryCarListener);
	}
	
	private void factoryCarListener(EventObject e) {
		int index = ((Factory) (e.getSource())).index;
		
		switch ((Event_FactoryCar) e.getType()) {
			case ENABLE:
				factoryUI.get(index).enable();
				break;
			case DISABLE:
				factoryUI.get(index).disable();
				break;
				
			case WAIT_ON:
				factoryUI.get(index).waitOn();
				break;
			case WAIT_OFF:
				factoryUI.get(index).waitOff();
				break;
				
			case WORK_ON:
				factoryUI.get(index).workOn();
				break;
			case WORK_OFF:
				factoryUI.get(index).workOff();
				break;
		}
	}
	
	private void officeListener(EventObject e) {
		switch ((Event_Office) e.getType()) {
			case ANSWER_DONE:
				
				break;
			
			case UPDATE:
				
				break;
		}
	}
	
	private void dealerListener(EventObject e) {
		switch ((Event_Dealer) e.getType()) {
			case UPDATE:
				dealerUI.updateTotal(e.getSource());
				break;
				
			case PAUSE:
				dealerUI.pause();
				break;
				
			case RESUME:
				dealerUI.resume();
				break;
		}
	}
	
	void warehouseListener(EventObject event) {
		int index = ((Warehouse) event.getSource()).getIndex();
		int total = ((Warehouse) event.getSource()).getOccupancy();
		
		switch ((Event_Warehouse) event.getType()) {
			case UPDATE:
				warehouseUI.get(index).totalUpdate(total);
				break;
		}
	}
	
	void factoriesListener(EventObject event) {
		int index = ((Factory) event.getSource()).index;
		int total = ((Factory) event.getSource()).total;
		
		switch ((Event_Factory) event.getType()) {
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
