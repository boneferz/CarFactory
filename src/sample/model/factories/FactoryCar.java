package sample.model.factories;

import sample.model.data.GlobalData;
import sample.model.events.Custom_EventObject;
import sample.model.events.Warehouse_Events;
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
	
	public FactoryCar(int index) {
		super(index);
		
		warehouseE = GlobalData.getInstance().warehouseEngine;
		warehouseB = GlobalData.getInstance().warehouseBody;
		warehouseA = GlobalData.getInstance().warehouseAccessories;
		
		warehouseE.addEventListener(this::warehouseDetailsListener);
		warehouseB.addEventListener(this::warehouseDetailsListener);
		warehouseA.addEventListener(this::warehouseDetailsListener);
	}
	
	@Override
	public void initWarehouse() {
		super.warehouse = new WarehouseCar(index);
		super.warehouse.addEventListener(this::warehouseListener);
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
		switch ((Warehouse_Events) e.getEvent()) {
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
		switch ((Warehouse_Events) e.getEvent()) {
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
		
		Car car = new Car(total, engine, body, accessorie);
		warehouse.put(car);
	}
}
