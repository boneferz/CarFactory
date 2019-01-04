package sample.model.factories;

import sample.model.ModelFacade;
import sample.model.data.GlobalData;
import sample.model.events.Event;
import sample.model.events.EventObject;
import sample.model.events.Event_FactoryCar;
import sample.model.events.Event_Warehouse;
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
	
	private boolean working = false;
	private boolean waiting = false;
	
	private int needCars = 0;
	private int needCarsTemp = 0;
	
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
	public void warehouseListener(EventObject e) {
		// del
	}
	
	public void warehouseDetailsListener(EventObject e) { // waiting()
		switch ((Event_Warehouse) e.getType()) {
			case ADDED:
				if (enable && isWorking() && isDetails()) {
					if (isWaiting()) {
						timeline.play();
						setWaiting(false);
						waitOff();
						workOn();
					}
				}
				break;
		}
	}
	
	@Override
	void on() {
		enable = true;
		ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.ENABLE);
		
		if (isWorking()) {
			if (isDetails()) {
				timeline.play();
				workOn();
				
				if (isWaiting()) {
					setWaiting(false);
				}
			} else {
				setWaiting(true);
				waitOn();
			}
		}
	}
	
	@Override
	void off() {
		if (!problem) {
			if (isWorking()) {
				timeline.stop();
				workOff();
				
				if (isWaiting()) waitOff();
			}
			
			enable = false;
			ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.DISABLE);
		}
	}
	
	@Override
	public void creating() {
		if (isDetails()) {
			// adding of detail
			createDetail();
			super.warehousePut();
			totalAddNew();
			if (problem) toFixProblem();

			// task working
			--needCars;
			if (needCars == 0) {
				taskDone();
				return;
			}
		}
		
		if (needCars > 0 && !isDetails()) {
			timeline.stop();
			setWaiting(true);
			workOff();
			waitOn();
		}
	}
	
	@Override
	protected void createDetail() {
		engine = (Engine) warehouseE.pull();
		body = (Body) warehouseB.pull();
		accessorie = (Accessories) warehouseA.pull();
		
		detail = new Car(total, engine, body, accessorie);
	}
	
	public boolean isDetails() {
		if (warehouseE.getOccupancy() > 0
				&& warehouseB.getOccupancy() > 0
				&& warehouseA.getOccupancy() > 0)
			return true;
		
		return false;
	}
	
	// task
	public void taskOnCreation(int count) {
		needCars = count;
		needCarsTemp = count;
		
		setWorking(true);
		
		if (enable) {
			if (isDetails()) {
				timeline.play();
				workOn();
			} else {
				setWaiting(true);
				waitOn();
			}
		}
	}
	void taskDone() {
		timeline.stop();
		setWorking(false);
		workOff();
		
		if (isWaiting()) {
			setWaiting(false);
			waitOff();
		}
		
		dispatchEvent(this, Event.FACTORY_CAR, Event_FactoryCar.TASK_DONE);
	}
	
	void workOn() {
		ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.WORK_ON);
	}
	void workOff() {
		ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.WORK_OFF);
	}
	
	void waitOn() {
		ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.WAIT_ON);
	}
	void waitOff() {
		ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.WAIT_OFF);
	}
	
	@Override
	public void pullDetail() {
		if (isWorking() && needCars < needCarsTemp) {
			super.pullDetail();
			needCars++;
		}
	}
	
	@Override
	public void clearWarehouse() {
		if (isWorking()) {
			super.clearWarehouse();
			needCars = needCarsTemp;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	// get / set
	///////////////////////////////////////////////////////////////////////////
	
	public boolean isWorking() {
		return working;
	}
	public void setWorking(boolean working) {
		this.working = working;
	}
	
	public boolean isWaiting() {
		return waiting;
	}
	public void setWaiting(boolean waiting) {
		this.waiting = waiting;
	}
}
