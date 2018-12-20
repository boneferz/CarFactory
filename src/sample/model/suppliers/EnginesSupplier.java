package sample.model.suppliers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import sample.model.data.Config;
import sample.model.ModelFacade;
import sample.model.events.EventType;
import sample.model.events.Supplier_Events;
import sample.model.suppliers.details.Engine;
import sample.model.suppliers.warehauses.EngineWarehouse;

import static sample.model.suppliers.SupplierState.*;

public class EnginesSupplier {
	
	public int index;
	
	Timeline timeline;
	private SupplierState state;
	private boolean isEnabled;
	private boolean isRezerv;
	private boolean isPaused;
	public int total;
	private int speeed;
	private int problemChance;
	
	Engine engine;
	public EngineWarehouse warehouse;
	
	public EnginesSupplier(int index) {
		this.index = index;
		
		total = 0;
		speeed = Config.getInstance().factorysSpeed;
		isEnabled = false;
		state = OFF;
		
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setOnFinished(this::workCycle);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(speeed), timeline.getOnFinished()));
		
		warehouse = new EngineWarehouse(index);
	}
	
	public void switcher() {
		if (isEnabled) {
			isEnabled = false;
			off();
		} else {
			isEnabled = true;
			run();
		}
	}
	
	private void off() {
		state = OFF;
		ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.SWITCH_OFF);
		timeline.stop();
	}
	
	private void run() {
		ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.SWITCH_ON);
		
		if (!isPaused) {
			state = RUNNING;
			timeline.play();
			
		} else {
			state = PAUSE;
			ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.PAUSE);
		}
	}
	
	private void workCycle(ActionEvent actionEvent) {
		if (isEnabled) create();
	}
	
	public void pause() {
		isPaused = true;
		timeline.stop();
		state = PAUSE;
		ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.PAUSE);
	}
	
	public void resume() {
		isPaused = false;
		state = RUNNING;
		timeline.play();
		warehouse.clear();
		ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.RESUME);
	}
	
	public void create() {
		setTotal(++total);
		put(new Engine(total));
	}
	
	public void put(Engine detail) {
		if (!warehouse.put(detail)) {
			pause();
		}
	}
	
	public void setTotal(int total) {
		this.total = total;
		ModelFacade.fireEvent(this, EventType.SUPPLIER, Supplier_Events.MADE);
	}
	
	public void problem() {
	
	}
	
	public void changeSpeed() {
	
	}
}
