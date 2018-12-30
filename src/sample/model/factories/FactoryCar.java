package sample.model.factories;

import sample.model.ModelFacade;
import sample.model.data.GlobalData;
import sample.model.events.*;
import sample.model.factories.detail.Accessories;
import sample.model.factories.detail.Body;
import sample.model.factories.detail.Car;
import sample.model.factories.detail.Engine;
import sample.model.factories.warehause.Warehouse;
import sample.model.factories.warehause.WarehouseCar;

public class FactoryCar extends Factory {
	
	Engine engine;
	Body body;
	Accessories accessorie;
	Warehouse warehouseE;
	Warehouse warehouseB;
	Warehouse warehouseA;
	
	// states (new)
	private boolean work = false;
	private boolean wait = false;
	
	// vars: get + set
	private boolean canAdd = false; // pause()
	private boolean details = false; // wait()
	
	private int needCars = 0;
	
	public FactoryCar(int index) {
		super(index);
		
		warehouseE = GlobalData.getInstance().warehouseEngine;
		warehouseB = GlobalData.getInstance().warehouseBody;
		warehouseA = GlobalData.getInstance().warehouseAccessories;
		warehouseE.addEventListener(this::warehouseDetailsListener);
		warehouseB.addEventListener(this::warehouseDetailsListener);
		warehouseA.addEventListener(this::warehouseDetailsListener);
		
		GlobalData.getInstance().factoryCar = this;
	}
	
	@Override
	public void initWarehouse() {
		super.warehouse = new WarehouseCar(index);
		GlobalData.getInstance().warehouseCar = warehouse;
	}
	
	@Override
	public void warehouseListener(EventObject e) { // pause()
		switch ((Event_Warehouse) e.getType()) {
			case RELEASED:
				if (isDetailsAvailability() && isCanAddNextDetail()) {
					if (paused) {
						if (enable) {
							resume();
						}
						else {
							paused = false;
						}
					}
				}
				break;
		}
	}
	
	public void warehouseDetailsListener(EventObject e) { // wait()
		switch ((Event_Warehouse) e.getType()) {
			case ADDED:
				if (isDetailsAvailability() && isCanAddNextDetail()) {
					if (paused) resume(); // pause???
				}
				break;
		}
	}
	
	@Override
	void on() {
		enable = true;
		ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.ENABLE);
//		if (paused) {
//			ModelFacade.fireEvent(this, Event.FACTORIES, Event_Factory.PAUSE);
//		} else {
//			timeline.play();
//		}
	}
	@Override
	void off() {
		enable = false;
		ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.DISABLE);
//		if (!problem) {
//			timeline.stop();
//			if (paused) ModelFacade.fireEvent(this, Event.FACTORIES, Event_Factory.RESUME);
//		}
	}
	
	@Override
	public void creating() {
		if (isDetailsAvailability() && isCanAddNextDetail()) {
			// add detail
			createDetail();
			super.warehousePut();
			totalAddNew();
			if (problem) toFixProblem();
			
			// task work
			--needCars;
			if (needCars == 0) {
				taskDone();
			} else {
				if (!isCanAddNextDetail()) pause();
			}
		} else {
			pause();
		}
	}
	
	@Override
	protected void createDetail() {
		engine = (Engine) warehouseE.pull();
		body = (Body) warehouseB.pull();
		accessorie = (Accessories) warehouseA.pull();
		
		detai = new Car(total, engine, body, accessorie);
	}
	
	boolean isCanAddNextDetail() { // get + check + set
		if (warehouse.getOccupancy() + 1 <= warehouse.getSize()) {
			if (!canAdd)
				setCanAddNextDetail(true);
		} else {
			if (canAdd)
				setCanAddNextDetail(false);
		}
		
		return canAdd;
	}
	
	public void setCanAddNextDetail(boolean value) { // set + event
		if (value) {
			canAdd = true;
//			ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.WAIT_DETAILS_ON);
		} else {
			canAdd = false;
//			ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.WAIT_DETAILS_OFF);
		}
	}
	
	boolean isDetailsAvailability() {
		if (warehouseE.getOccupancy() > 0
				&& warehouseB.getOccupancy() > 0
				&& warehouseA.getOccupancy() > 0) {
			return true;
		}
		return false;
	}
	
//	void enableDatailWait() {
//		if (!waitDetail) {
//			waitDetail = true;
//			ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.WAIT_DETAILS_ON);
//		}
//	}
//	void disableDatailWait() {
//		if (waitDetail) {
//			waitDetail = false;
//			ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.WAIT_DETAILS_OFF);
//		}
//	}
	
	public void taskOnCreation(int count) {
		needCars = count;
	}
	
	void taskDone() {
		dispatchEvent(this, Event.FACTORY_CAR, Event_FactoryCar.TASK_DONE);
	}
	
	// --------------------------------------
	
	void enable() {
	
	}
	void disable() {
	
	}
	
	void problem() {
	
	}
	void fixProblem() {
	
	}
	
	void onWork() {
	
	}
	void offWork() {
	
	}
	
	void onWait() {
	
	}
	void offWait() {
	
	}
	
}
