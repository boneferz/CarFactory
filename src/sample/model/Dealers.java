package sample.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import sample.model.data.GlobalData;
import sample.model.events.Custom_EventObject;
import sample.model.events.Dealer_Events;
import sample.model.events.Events;
import sample.model.events.Office_Events;
import sample.model.events.observer.EventDispatcher;
import sample.model.factories.detail.Car;
import sample.model.factories.detail.Detail;
import sample.model.factories.warehause.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class Dealers extends EventDispatcher {
	
	Timeline timeline;
	List<Detail> warehouse = new ArrayList<>();
	private int size = 0;
	private int occupancy = 0;
	
	boolean isPause = false;
	private int total = 0;
	private int speed = 0;
	
	Warehouse warehouseCar;
	Office office;
	
	public Dealers() {
		size = 5;
		speed = 500;
		
		warehouseCar = GlobalData.getInstance().warehouseCar;
		office = GlobalData.getInstance().office;
		
		office.addEventListener(this::officeListener);
		
		timeline = new Timeline();
		timeline.setOnFinished(this::onUpdate);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(speed), timeline.getOnFinished()));
		
		on();
	}
	
	private void officeListener(Custom_EventObject e) {
		switch ((Office_Events) e.getType()) {
			case ANSWER_DONE:
				refill();
				break;
		}
		
	}
	
	private void onUpdate(ActionEvent e) {
		sale();
	}
	
	void on() {
		timeline.play();
	}
	
	void sale() {
		if (occupancy > 0) {
			setOccupancy(--occupancy);
			Car soldCar = (Car) warehouse.remove(0);
			System.out.println("car:" + soldCar);
			System.out.println(
					"[sale] " +
					"carCLASS:" + soldCar.getClass().getSimpleName() + ", " +
					"carCODE:" + soldCar.getCode() + ", " +
					"carID:" + soldCar.getId()
			);
		} else {
			requestRefill();
		}
	}
	
	void requestRefill() {
		pause();
		office.dealerRequest(size);
		FacadeModel.fireEvent(this, Events.DEALER, Dealer_Events.UPDATE);
	}
	
	void refill() {
		for (int i = 0; i < size; i++) {
			total++;
			setOccupancy(++occupancy);
			
			Car car = (Car) warehouseCar.pull();
			warehouse.add(car);
			System.out.println(i + " - - - " + warehouse.get(i));
			System.out.println(".");
		}
		
		resume();
	}
	
	void pause() {
		isPause = true;
		timeline.stop();
		FacadeModel.fireEvent(this, Events.DEALER, Dealer_Events.PAUSE);
	}
	
	void resume() {
		isPause = false;
		timeline.play();
		FacadeModel.fireEvent(this, Events.DEALER, Dealer_Events.RESUME);
	}
	
//	void setSpeed() {
//
//	}
	
	private void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
		FacadeModel.fireEvent(this, Events.DEALER, Dealer_Events.UPDATE);
	}
	
	public int getOccupancy() {
		return occupancy;
	}
	
	public int getTotal() {
		return total;
	}
	
	public int getSize() {
		return size;
	}
}
