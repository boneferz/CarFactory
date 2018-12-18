package sample.model.suppliers;

import sample.model.Config;
import sample.model.CustomEvent;
import sample.model.ModelFacade;
import sample.model.SupplierEvents;

import static sample.model.suppliers.SupplierState.*;

public class EnginesSupplier {
	
	private int index;
	
	private SupplierState state;
	private boolean isEnabled;
	private int total;
	private int speeed;
	private int problemChance;
	
	public EnginesSupplier(int index) {
		this.index = index;
		
		init();
	}
	
	
	private void init() {
		total = 0;
		speeed = Config.getInstance().factorysSpeed;
		
		isEnabled = false;
		state = OFF;
	}
	
	void reset() {
	
	}
	
	public void switcher() {
		if (isEnabled) {
			isEnabled = false;
			state = OFF;
			
			ModelFacade.eventDispatcher.dispatchEvent(
					new CustomEvent(SupplierEvents.SWITCH_OFF.getValue(), index));
		} else {
			isEnabled = true;
			state = RUNNING;
			
			ModelFacade.eventDispatcher.dispatchEvent(
					new CustomEvent(SupplierEvents.SWITCH_ON.getValue(), index));
			
			run();
		}
	}
	
	private void run() {
		total++;
		ModelFacade.eventDispatcher.dispatchEvent(
				new CustomEvent(SupplierEvents.MADE.getValue(), index));
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
