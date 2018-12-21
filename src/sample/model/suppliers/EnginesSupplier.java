package sample.model.suppliers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import sample.model.data.Config;
import sample.model.ModelFacade;
import sample.model.events.Custom_EventObject;
import sample.model.events.EventType;
import sample.model.events.Supplier_Events;
import sample.model.events.Warehouse_Events;
import sample.model.suppliers.details.Engine;
import sample.model.suppliers.warehauses.EngineWarehouse;

public class EnginesSupplier {
	
	public int index;
	
	Timeline timeline;
	private boolean isEnabled;
	private boolean isProblem;
	private boolean isPaused;
	public int total;
	private int speeed;
	private int problemChance;
	private int problemDelay;
	Engine engine;
	public EngineWarehouse warehouse;
	
	public EnginesSupplier(int index) {
		this.index = index;
		
		init();
		
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setOnFinished(this::work);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(speeed), timeline.getOnFinished()));
		
		warehouse = new EngineWarehouse(index);
		warehouse.addEventListener(this::warehouseListener);
	}
	
	private void init() {
		isEnabled = false;
		isProblem = false;
		isPaused = false;
		total = 0;
		speeed = 100; //Config.getInstance().factorysSpeed;
		problemChance = 25; //Config.getInstance().problemChance;
		problemDelay = 3000; //Config.getInstance().problemDelay;
	}
	
	void warehouseListener(Custom_EventObject e) {
		switch ((Warehouse_Events) e.getEvent()) {
			case RELEASED:
				if (isPaused) {
					if (isEnabled) resume(); // resume ENABLED
					else isPaused = false; // resume DISABLED
				}
				break;
		}
	}
	
	public void switcher() {
		if (!isEnabled) {
			on();
		} else {
			off();
		}
	}
	
	private void on() {
		isEnabled = true;
		ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.SWITCH_ON);
		
		if (isPaused) {
			ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.PAUSE);
		} else {
			timeline.play();
		}
	}
	
	private void off() {
		if (!isProblem) {
			isEnabled = false;
			timeline.stop();
			
			if (isPaused) ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.RESUME);
			ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.SWITCH_OFF);
		}
	}
	
	public void pause() {
		isPaused = true;
		timeline.stop();
		ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.PAUSE);
	}
	
	public void resume() {
		isPaused = false;
		timeline.play();
		ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.RESUME);
	}
	
	private void work(ActionEvent e) {
		if (!isProblem) {
			tryProblem();
			if (isProblem) return;
		}
		
		create();
	}
	
	public void create() {
		put(new Engine(total));
		setTotal(++total);
		
		if (isProblem) fixedProblem();
		
		if (warehouse.occupancy + 1 > warehouse.size) {
			if (!isPaused) pause();
		}
	}
	
	public void setTotal(int total) {
		this.total = total;
		ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.CREATED);
	}
	
	public void put(Engine detail) {
		warehouse.put(detail);
	}
	
	public void tryProblem() {
		if (Math.random() * 100 < problemChance) {
			isProblem = true;
			timeline.stop();
			ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.PROBLEM);
			
			Timeline delay = new Timeline(new KeyFrame(Duration.millis(problemDelay)));
			delay.setOnFinished(this::problemDelay);
			delay.play();
		}
	}
	
	void problemDelay(ActionEvent e) {
		timeline.play();
	}
	
	void fixedProblem() {
		isProblem = false;
		ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.PROBLEM_FIXED);
	}
	
	public void changeSpeed() {
	
	}
	
}
