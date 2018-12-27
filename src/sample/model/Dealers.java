package sample.model;

import sample.model.data.GlobalData;
import sample.model.events.Custom_EventObject;
import sample.model.events.Dealer_Events;
import sample.model.events.Events;
import sample.model.events.Office_Events;
import sample.model.events.observer.EventDispatcher;
import sample.model.factories.detail.Detail;
import sample.model.factories.warehause.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class Dealers extends EventDispatcher {
	
	List<Detail> warehouse = new ArrayList<>();
	int wSize = 0;
	int wOccupancy = 0;
	
	boolean isWait = false;
	int total = 0;
	int speed = 0;
	
	Warehouse warehouseCar;
	Office office;
	
	public Dealers() {
		wSize = 5;
		
		warehouseCar = GlobalData.getInstance().warehouseCar;
		office = GlobalData.getInstance().office;
		office.addEventListener(this::officeListener);
		
		System.out.println("Dealers.Dealers");
		
		//requestRefill();
	}

	private void officeListener(Custom_EventObject e) {
		switch ((Office_Events) e.getType()) {
			case ANSWER_DONE:
				System.out.println("Dealers.officeListener - ANSWER");
				refill();
				break;
		}
		
	}
	
	void sale() {
		warehouse.remove(0);
		setWOccupancy(--wOccupancy);
	}
	
	void requestRefill() {
		setWait(true);
		office.dealerRequest(wSize);
	}
	
	void refill() {
		System.out.println("    Dealers.refill << ");
		for (int i = 0; i < wSize; i++) {
			setWOccupancy(++wOccupancy);
			warehouse.add(warehouseCar.pull());
		}
	}
	
//	void setSpeed() {
//
//	}
	
	public void setWOccupancy(int occupancy) {
		this.wOccupancy = occupancy;
		FacadeModel.fireEvent(this, Events.DEALER, Dealer_Events.UPDATE);
	}
	
	public void setWait(boolean wait) {
		if (wait) {
			FacadeModel.fireEvent(this, Events.DEALER, Dealer_Events.UPDATE);
		} else {
			FacadeModel.fireEvent(this, Events.DEALER, Dealer_Events.UPDATE);
		}
		isWait = wait;
	}
}
