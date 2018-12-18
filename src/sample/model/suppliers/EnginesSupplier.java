package sample.model.suppliers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import sample.model.Config;
import sample.model.CustomEvent;
import sample.model.ModelFacade;
import sample.model.SupplierEvents;

import static sample.model.suppliers.SupplierState.*;

public class EnginesSupplier {
	
	Timeline timeline;
	
	public int index;
	private SupplierState state;
	private boolean isEnabled;
	public int total;
	private int speeed;
	private int problemChance;
	
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
	}
	
	private void workCycle(ActionEvent actionEvent) {
		setTotal(++total);
	}
	
	public void setTotal(int total) {
		this.total = total;
		ModelFacade.eventDispatcher.dispatchEvent(new CustomEvent(this, SupplierEvents.MADE));
	}
	
	public void switcher() {
		if (isEnabled) {
			isEnabled = false;
			state = OFF;
			ModelFacade.eventDispatcher.dispatchEvent(new CustomEvent(this, SupplierEvents.SWITCH_OFF));
			off();
		} else {
			isEnabled = true;
			state = RUNNING;
			ModelFacade.eventDispatcher.dispatchEvent(new CustomEvent(this, SupplierEvents.SWITCH_ON));
			run();
		}
	}
	
	private void off() {
		timeline.stop();
		setTotal(0);
	}
	
	private void run() {
		timeline.play();
		System.out.println("EnginesSupplier.run");
		ModelFacade.eventDispatcher.dispatchEvent(new CustomEvent(this, SupplierEvents.MADE));
	}
	
	public void pause() {
	
	}
	
	public void resume() {
	
	}
	
	public void problem() {
	
	}
	
	public void create() {
	
	}
	
	public void changeSpeed() {
	
	}
	
	public void put() {
	
	}
}
