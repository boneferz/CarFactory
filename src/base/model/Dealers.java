package base.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import base.model.data.GlobalData;
import base.model.events.EventObject;
import base.model.events.Event_Dealer;
import base.model.events.Event;
import base.model.events.Event_Office;
import base.model.events.observer.EventDispatcher;
import base.model.factories.detail.Car;
import base.model.factories.detail.Detail;
import base.model.factories.warehause.Warehouse;

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
	
	private void officeListener(EventObject e) {
		switch ((Event_Office) e.getType()) {
			case ANSWER_DONE:
				
				System.out.println("---ANSWER_DONE >> DELAY");
				
				Timeline delay = new Timeline();
				delay.setOnFinished(event -> {
					System.out.println("---ANSWER_DONE >> stop");
					refill();
				});
				delay.setCycleCount(5);
				delay.getKeyFrames().add(new KeyFrame(Duration.millis(500)));
				delay.play();
				
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
			System.out.println(
					"[sale] " +
					"carID: " + soldCar.getId() + ", " +
					"carCLASS: " + soldCar.getClass().getSimpleName() + ", " +
					"carCODE: " + soldCar.getCode()
			);
		} else {
			requestRefill();
		}
	}
	
	void requestRefill() {
		System.out.println("");
		System.out.println("");
		System.out.println("█ request ->");
		
		pause();
		office.dealerRequest(size);
		ModelFacade.fireEvent(this, Event.DEALER, Event_Dealer.UPDATE);
	}
	
	void refill() {
		System.out.println("█ answer <-");
		
		for (int i = 0; i < size; i++) {
			total++;
			setOccupancy(++occupancy);
			
			Car car = (Car) warehouseCar.pull();
			warehouse.add(car);
		}
		
		resume();
	}
	
	void pause() {
		isPause = true;
		timeline.stop();
		ModelFacade.fireEvent(this, Event.DEALER, Event_Dealer.PAUSE);
	}
	
	void resume() {
		isPause = false;
		timeline.play();
		ModelFacade.fireEvent(this, Event.DEALER, Event_Dealer.RESUME);
	}
	
//	void setSpeed() {
//
//	}
	
	private void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
		ModelFacade.fireEvent(this, Event.DEALER, Event_Dealer.UPDATE);
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
