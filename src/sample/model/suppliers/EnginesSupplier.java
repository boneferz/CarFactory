package sample.model.suppliers;

import sample.model.Config;

import static sample.model.suppliers.SupplierState.*;

public class EnginesSupplier implements Supplier {
	
	private SupplierState state;
	private boolean isEnabled;
	private int total;
	private int speeed;
	
	// speed slider - g
	// on switcher - g
	// state animation - g
	// total output
	
	// work logic
	
	public EnginesSupplier() {
		init();
	}
	
	private void init() {
		isEnabled = false;
		state = OFF;
		total = 0;
		speeed = Config.getInstance().factorysSpeed;
	}
	
	void reset() {
	
	}
	
	@Override
	public void on() {
		System.out.println("Engine -> ON");
	}
	
	@Override
	public void off() {
		System.out.println("Engine -> OFF");
	}
	
	@Override
	public void pause() {
	
	}
	
	@Override
	public void resume() {
	
	}
	
	@Override
	public void problem() {
	
	}
	
	@Override
	public void create() {
	
	}
	
	@Override
	public void changeSpeed() {
	
	}
	
	@Override
	public void put() {
	
	}
}
