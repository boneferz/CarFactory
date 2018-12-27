package sample.model.factories;

import jdk.nashorn.internal.objects.Global;
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
	Warehouse warehouseE, warehouseB, warehouseA;
	
	int needCars = 0;
	
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
	public void on() {
		super.on();
		
		if (!isDetailsAvailability()) {
			if (!isPaused) pause();
		}
	}
	
	@Override
	public void warehouseListener(Custom_EventObject e) {
		switch ((Warehouse_Events) e.getType()) {
			case RELEASED:
				if (isDetailsAvailability() && isCanAddNextDetail()) {
					if (isPaused) {
						if (isEnabled) {
							resume();
						}
						else {
							isPaused = false;
						}
					}
				}
				break;
		}
	}
	
	public void warehouseDetailsListener(Custom_EventObject e) {
		switch ((Warehouse_Events) e.getType()) {
			case ADDED:
				if (isDetailsAvailability() && isCanAddNextDetail()) {
					if (isPaused) resume();
				}
				break;
		}
	}
	
	@Override
	public void creating() {
		if (isDetailsAvailability() && isCanAddNextDetail()) {
			
			createDetail();
			super.warehousePut();
			
			System.out.println("needCars:" + needCars);
			System.out.println("");
			if (needCars == 0) taskDone();
			
			totalAddNew();
			
			if (isProblem) toFixProblem();
			
			if (!isCanAddNextDetail()) pause();
		} else {
			pause();
		}
	}
	
	boolean isCanAddNextDetail() {
		if (warehouse.getOccupancy() + 1 <= warehouse.getSize())
			return true;
		return false;
	}
	
	boolean isDetailsAvailability() {
		if (warehouseE.getOccupancy() > 0
			&& warehouseB.getOccupancy() > 0
			&& warehouseA.getOccupancy() > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	protected void createDetail() {
		engine = (Engine) warehouseE.pull();
		body = (Body) warehouseB.pull();
		accessorie = (Accessories) warehouseA.pull();
		
		detai = new Car(total, engine, body, accessorie);
		--needCars;
	}
	
	public void taskOnCreation(int count) {
		needCars = count;
		on();
	}
	
	void taskDone() {
//		off();
		dispatchEvent(this, Events.CAR_FACTORY, CarFactory_Events.TASK_DONE);
	}
}
