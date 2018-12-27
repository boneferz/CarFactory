package sample.model.factories;

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
import sample.model.events.Events;
import sample.model.events.Factory_Events;
import sample.model.events.Warehouse_Events;
import sample.model.events.observer.EventDispatcher;
import sample.model.factories.detail.Detail;
import sample.model.factories.warehause.Warehouse;

public abstract class Factory extends EventDispatcher {
	public int index;
	
	Timeline timeline;
	boolean isEnabled;
	boolean isProblem;
	boolean isPaused;
	
	public int total;
	private int speed;
	private int speedMax;
	private int speedMin;
	private int problemChance;
	private int problemDelay;
	
	public Warehouse warehouse;
	Detail detai;
	
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
		warehouse.addEventListener(this::warehouseListener);
	}
	protected abstract void initWarehouse();
	
	private void init() {
		isEnabled = false;
		isProblem = false;
		isPaused = false;
		total = 0;
		problemChance = 0; //Config.getInstance().problemChance;
		problemDelay = 3000; //Config.getInstance().problemDelay;
		
		speedMin = Config.getInstance().factorysSpeedMin;
		speedMax = Config.getInstance().factorysSpeedMax;
		speed = Config.getInstance().factorysSpeed;
		
		int deltaValue = speedMax - speedMin;
		ratio = deltaValue / deltaPosition;
		setSpeedSliderStartPosition();
	}
	
	public void warehouseListener(Custom_EventObject e) {
		switch ((Warehouse_Events) e.getType()) {
			case RELEASED:
				if (isPaused) {
					if (isEnabled) resume(); // standart resume
					else if (!isEnabled) isPaused = false; // off resume
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
	
	public void on() {
		isEnabled = true;
		FacadeModel.fireEvent(this, Events.FACTORY, Factory_Events.SWITCH_ON);
		
		if (isPaused) {
			FacadeModel.fireEvent(this, Events.FACTORY, Factory_Events.PAUSE);
		} else {
			timeline.play();
		}
	}
	
	public void off() {
		if (!isProblem) {
			isEnabled = false;
			timeline.stop();
			
			if (isPaused) FacadeModel.fireEvent(this, Events.FACTORY, Factory_Events.RESUME);
			FacadeModel.fireEvent(this, Events.FACTORY, Factory_Events.SWITCH_OFF);
		}
	}
	
	public void pause() {
		isPaused = true;
		timeline.stop();
		FacadeModel.fireEvent(this, Events.FACTORY, Factory_Events.PAUSE);
	}
	
	public void resume() {
		isPaused = false;
		timeline.play();
		FacadeModel.fireEvent(this, Events.FACTORY, Factory_Events.RESUME);
	}
	
	void work(ActionEvent e) {
		if (!isProblem) {
			tryProblem();
			if (isProblem) return;
		}
		creating();
	}
	
	protected abstract void createDetail();
	public void creating() {
		createDetail();
		warehousePut();
		
		totalAddNew();
		if (isProblem) toFixProblem();
		
		if (isCanAddNextDetail()) {
			if (!isPaused) pause();
		}
	}
	
	void warehousePut() {
		warehouse.put(detai);
	}
	
	boolean isCanAddNextDetail() {
		if (warehouse.getOccupancy() + 1 > warehouse.getSize())
			return true;
		return false;
	}
	
	
	
	public void totalAddNew() {
		this.total = ++total;
		FacadeModel.fireEvent(this, Events.FACTORY, Factory_Events.CREATED);
	}
	
	public void tryProblem() {
		if (Math.random() * 100 < problemChance) {
			isProblem = true;
			timeline.stop();
			FacadeModel.fireEvent(this, Events.FACTORY, Factory_Events.PROBLEM);
			
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
		FacadeModel.fireEvent(this, Events.FACTORY, Factory_Events.PROBLEM_FIXED);
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
		
		FacadeModel.fireEvent(this, Events.FACTORY, Factory_Events.MOVE_SPEED_SLIDER);
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
		FacadeModel.fireEvent(this, Events.FACTORY, Factory_Events.INIT_SPEED_SLIDER);
	}
}
