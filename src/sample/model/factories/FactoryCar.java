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
	
	private boolean work = false;
	private boolean wait = false;
	
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
	
	public void warehouseDetailsListener(EventObject e) { // wait()
		switch ((Event_Warehouse) e.getType()) {
			case ADDED:
				if (enable && isWork() && isDetails()) {
					if (isWait()) {
						setWait(false);
						waitOff();
					}
				}
				break;
		}
	}
	
	@Override
	void on() {
		enable = true;
		ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.ENABLE);
		
		if (isWork())
			workOn();
		
		if (isWait())
			waitOn();
		
		if (isWork()) {
			if (!isDetails()) {
				if (!isWait()) {
					setWait(true);
					waitOn();
				}
			} else {
				if (isWait()) {
					setWait(false);
					waitOff();
				}
			}
		}
	}
	
	@Override
	void off() {
		if (isWork())
			workOff();
		
		if (isWait())
			ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.WAIT_OFF);
		
		enable = false;
		ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.DISABLE);
	}
	
	@Override
	public void creating() {
		if (isDetails()) {
			// adding detail
			createDetail();
			super.warehousePut();
			totalAddNew();
			if (problem) toFixProblem();

			// task work
			--needCars;
			if (needCars == 0) {
				taskDone();
			} else {
				if (!isDetails()) {
					if (!isWait()) {
						setWait(true);
						waitOn();
					}
				}
			}
		} else {
			
			if (!isWait()) {
				setWait(true);
				waitOn();
			}
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
		
		setWork(true);
		if (enable) {
			if (!isWait()) {
				workOn();
				
				if (!isDetails()) {
					setWait(true);
					waitOn();
				}
			}
		}
	}
	void taskDone() {
		setWork(false);
		workOff();
		
		dispatchEvent(this, Event.FACTORY_CAR, Event_FactoryCar.TASK_DONE);
	}
	
	// work
	void workOn() {
		timeline.play();
		ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.WORK_ON);
	}
	void workOff() {
		timeline.stop();
		ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.WORK_OFF);
	}
	
	// wait
	void waitOn() {
		timeline.stop();
		ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.WAIT_ON);
	}
	void waitOff() {
		timeline.play();
		ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.WAIT_OFF);
	}
	
	@Override
	public void pullDetail() {
		if (isWork() && needCars < needCarsTemp) {
			super.pullDetail();
			needCars++;
		}
	}
	
	@Override
	public void clearWarehouse() {
		if (isWork()) {
			super.clearWarehouse();
			needCars = needCarsTemp;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	// get / set
	///////////////////////////////////////////////////////////////////////////
	
	public boolean isWork() {
		return work;
	}
	public void setWork(boolean work) {
		this.work = work;
	}
	
	public boolean isWait() {
		return wait;
	}
	public void setWait(boolean wait) {
		this.wait = wait;
	}
}
