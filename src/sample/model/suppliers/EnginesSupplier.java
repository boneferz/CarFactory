package sample.model.suppliers;

import sample.model.Config;
import sample.model.ModelFacade;
import sample.model.SupplierEvents;

import static sample.model.suppliers.SupplierState.*;

public class EnginesSupplier {
	
	private SupplierState state;
	private boolean isEnabled;
	private int total;
	private int speeed;
	
	// work logic
	
	public EnginesSupplier() {
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
			ModelFacade.eventDispatcher.dispatchEvent(SupplierEvents.SWITCH_OFF.getValue());
		}
		else {
			isEnabled = true;
			state = RUNNING;
			ModelFacade.eventDispatcher.dispatchEvent(SupplierEvents.SWITCH_ON.getValue());
		}
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
