package base.model.factories;

import base.model.ModelFacade;
import base.model.data.GlobalData;
import base.model.factories.detail.Accessories;
import base.model.factories.detail.Body;
import base.model.factories.detail.Car;
import base.model.factories.detail.Engine;
import base.model.factories.warehause.Warehouse;
import base.model.factories.warehause.WarehouseCar;

public class FactoryCar extends Factory {
	
	Engine engine;
	Body body;
	Accessories accessorie;
	Warehouse warehouseE;
	Warehouse warehouseB;
	Warehouse warehouseA;
	
	// states
	private boolean work = false;
	private boolean wait = false;
	// vars
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
				/*if (isDetailsAvailability() && isCanAddNextDetail()) {
					if (paused) {
						if (enable) {
							resume();
						}
						else if (!enable){
							paused = false;
						}
					}
				}*/
				break;
		}
	}
	
	public void warehouseDetailsListener(EventObject e) { // wait()
		switch ((Event_Warehouse) e.getType()) {
			case ADDED:
				/*if (isCanAddNextDetail()) {
					if (paused) resume(); // pause???
				}*/
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
		
//		if (paused) {
//			ModelFacade.fireEvent(this, Event.FACTORIES, Event_Factory.PAUSE);
//		} else {
//			timeline.play();
//
	}
	
	@Override
	void off() {
		if (isWork())
			workOff();
		
		if (isWait())
			ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.WAIT_OFF);
		
		enable = false;
		ModelFacade.fireEvent(this, Event.FACTORY_CAR, Event_FactoryCar.DISABLE);
//		if (!problem) {
//			timeline.stop();
//			if (paused) ModelFacade.fireEvent(this, Event.FACTORIES, Event_Factory.RESUME);
//		}
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
		
		// if isCanAdd
		// if isDetails
		/*if (isDetailsAvailability() && isCanAddNextDetail()) {
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
		}*/
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
	
	// canAdd
	/*boolean isCanAddNextDetail() { // get + check + set
		if (warehouse.getOccupancy() + 1 <= warehouse.getSize()) {
			if (!canAdd)
				setCanAddNextDetail(true);
		} else {
			if (canAdd)
				setCanAddNextDetail(false);
		}
		
		return canAdd;
	}
	public void setCanAddNextDetail(boolean canAdd) { // set + event
		this.canAdd = canAdd;
	}*/
	
	// task
	public void taskOnCreation(int count) {
		needCars = count;
		
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
	
	public boolean isCanAdd() {
		return canAdd;
	}
	public void setCanAdd(boolean canAdd) {
		this.canAdd = canAdd;
	}
}
