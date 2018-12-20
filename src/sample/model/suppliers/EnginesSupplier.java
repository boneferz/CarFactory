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
		speeed = Config.getInstance().factorysSpeed;
		problemChance = Config.getInstance().problemChance;
		problemDelay = Config.getInstance().problemDelay;
	}
	
	void warehouseListener(Custom_EventObject e) {
		System.out.println("EnginesSupplier.warehouseListener !");
		System.out.println(e.getEvent());
//		resume();
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
		timeline.play();
		
		ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.SWITCH_ON);

//		if (isPaused) pause();
	}
	
	private void off() {
		isEnabled = false;
		
		timeline.stop();
		ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.SWITCH_OFF);
		
//		if (!isProblem) {
//			if (isPaused) resume();
//		}
	}
	
	private void work(ActionEvent e) {
		System.out.println("work");
		create();
//		tryProblem();
//
//		if (!isProblem) {
//			System.out.println("create --- if problem ON > ERROR");
//			create();
//		} else {
//			System.out.println("ERROR!");
//		}
	}
	
	public void pause() {
		isPaused = true;
		timeline.stop();
		ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.PAUSE);
	}
	
	public void resume() {
		if (isEnabled && isPaused) {
			isPaused = false;
			
			ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.RESUME);
			timeline.play();
		}
	}
	
	public void create() {
		setTotal(++total);
		put(new Engine(total));
	}
	
	public void setTotal(int total) {
		this.total = total;
		ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.MADE);
	}
	
	public void put(Engine detail) {
//		if (!warehouse.put(detail)) {
//			pause();
//		}
		warehouse.put(detail);
	}
	
	public void tryProblem() {
		int random = (int) (Math.random() * 100);
		if (random < problemChance) {
			System.out.println("█ problem : ON");
			isProblem = true;
			timeline.stop();
			ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.PROBLEM);
			
			Timeline delay = new Timeline(new KeyFrame(Duration.millis(problemDelay)));
			delay.setOnFinished(e -> {
				timeline.play();
				ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.PROBLEM_FIXED);
				isProblem = false;
				System.out.println("    █ problem : OFF");
			});
			delay.play();
		}
	}
	
	public void changeSpeed() {
	
	}
	
}
