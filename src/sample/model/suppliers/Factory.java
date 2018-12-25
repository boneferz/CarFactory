package sample.model.suppliers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import sample.model.FacadeModel;
import sample.model.data.Config;
import sample.model.events.Custom_EventObject;
import sample.model.events.EventType;
import sample.model.events.Supplier_Events;
import sample.model.events.Warehouse_Events;
import sample.model.suppliers.warehause.Warehouse;

public abstract class Factory {
	public int index;
	
	Timeline timeline;
	private boolean isEnabled;
	private boolean isProblem;
	private boolean isPaused;
	
	public int total;
	private int speed;
	private int speedMax;
	private int speedMin;
	private int problemChance;
	private int problemDelay;
	
	public Warehouse warehouse;
	
	int sliderStartPosition = 0;
	public int sliderPosition = 0;
	public int initSliderPosition;
	int deltaPosition = 34;
	double ratio;
	int moveDistance;
	
	public Factory(int index) {
		this.index = index;
		
		init();
		
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setOnFinished(this::work);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(speed), timeline.getOnFinished()));
		
		initWarehouse();
	}
	
	protected abstract void initWarehouse();
	
	private void init() {
		isEnabled = false;
		isProblem = false;
		isPaused = false;
		total = 0;
		problemChance = 10; //Config.getInstance().problemChance;
		problemDelay = 3000; //Config.getInstance().problemDelay;
		
		speedMin = Config.getInstance().factorysSpeedMin;
		speedMax = Config.getInstance().factorysSpeedMax;
		speed = speedMax; //Config.getInstance().factorysSpeed;
		
		int deltaValue = speedMax - speedMin;
		ratio = deltaValue / deltaPosition;
		setSpeedSliderStartPosition();
	}
	
	public void warehouseListener(Custom_EventObject e) {
		switch ((Warehouse_Events) e.getEvent()) {
			case RELEASED:
				if (isPaused) {
					if (isEnabled) resume();
					else isPaused = false;
				}
				break;
		}
	}
	
	public void switcher() {
		if (!isEnabled) {
			on();
		} else {
			off();
		}
	}
	
	private void on() {
		isEnabled = true;
		FacadeModel.fireEvent(this, EventType.SUPPLIER, Supplier_Events.SWITCH_ON);
		
		if (isPaused) {
			FacadeModel.fireEvent(this, EventType.SUPPLIER, Supplier_Events.PAUSE);
		} else {
			timeline.play();
		}
	}
	
	private void off() {
		if (!isProblem) {
			isEnabled = false;
			timeline.stop();
			
			if (isPaused) FacadeModel.fireEvent(this, EventType.SUPPLIER, Supplier_Events.RESUME);
			FacadeModel.fireEvent(this, EventType.SUPPLIER, Supplier_Events.SWITCH_OFF);
		}
	}
	
	public void pause() {
		isPaused = true;
		timeline.stop();
		FacadeModel.fireEvent(this, EventType.SUPPLIER, Supplier_Events.PAUSE);
	}
	
	public void resume() {
		isPaused = false;
		timeline.play();
		FacadeModel.fireEvent(this, EventType.SUPPLIER, Supplier_Events.RESUME);
	}
	
	private void work(ActionEvent e) {
		if (!isProblem) {
			tryProblem();
			if (isProblem) return;
		}
		
		creating();
	}
	
	public void creating() {
		createDetail();
		
		totalAddNew();
		if (isProblem) toFixProblem();
		
		// on next
		if (warehouse.occupancy + 1 > warehouse.size) {
			if (!isPaused) pause();
		}
	}
	
	protected abstract void createDetail();
	
	public void totalAddNew() {
		this.total = ++total;
		FacadeModel.fireEvent(this, EventType.SUPPLIER, Supplier_Events.CREATED);
	}
	
	public void tryProblem() {
		if (Math.random() * 100 < problemChance) {
			isProblem = true;
			timeline.stop();
			FacadeModel.fireEvent(this, EventType.SUPPLIER, Supplier_Events.PROBLEM);
			
			Timeline delay = new Timeline(new KeyFrame(Duration.millis(problemDelay)));
			delay.setOnFinished(this::problemDelay);
			delay.play();
		}
	}
	
	void problemDelay(ActionEvent e) {
		timeline.play();
	}
	
	void toFixProblem() {
		isProblem = false;
		FacadeModel.fireEvent(this, EventType.SUPPLIER, Supplier_Events.PROBLEM_FIXED);
	}
	
	public void mousePressed(MouseEvent e) { // [press]
		ImageView sliderImage = (ImageView) e.getTarget();
		
		// save
		if (sliderStartPosition == 0) {
			sliderStartPosition = (int) sliderImage.getLayoutX();
		}
	}
	
	public void mouseDrag(MouseEvent e) { // [drag]
		int moveX = (int) e.getX();
		
		sliderPosition = moveX - 5;
		moveDistance = sliderPosition - sliderStartPosition;
		
		if (moveDistance < 0) {
			moveDistance = 0;
			sliderPosition = sliderStartPosition;
		} else if (moveDistance > deltaPosition) {
			moveDistance = deltaPosition;
			sliderPosition = sliderStartPosition + deltaPosition;
		}
		
		FacadeModel.fireEvent(this, EventType.SUPPLIER, Supplier_Events.MOVE_SPEED_SLIDER);
	}
	
	public void mouseRealised(MouseEvent e) { // [relise]
		changeSpeed();
	}
	
	public void changeSpeed() {
		speed = (int) ((moveDistance) * ratio) + speedMin;
		System.out.println(" > new speed: " + speed);
		
		if (timeline.getStatus().equals(Animation.Status.RUNNING)) {
			timeline.stop();
			timeline.getKeyFrames().clear();
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(speed), timeline.getOnFinished()));
			timeline.play();
		} else {
			timeline.getKeyFrames().clear();
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(speed), timeline.getOnFinished()));
		}
	}
	
	private void setSpeedSliderStartPosition() {
		initSliderPosition = (int) (((speed - speedMin) / ratio));
		FacadeModel.fireEvent(this, EventType.SUPPLIER, Supplier_Events.INIT_SPEED_SLIDER);
	}
}
