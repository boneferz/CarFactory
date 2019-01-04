package sample.model.factories;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import sample.model.ModelFacade;
import sample.model.data.Config;
import sample.model.events.EventObject;
import sample.model.events.Event;
import sample.model.events.Event_Factory;
import sample.model.events.Event_Warehouse;
import sample.model.events.observer.EventDispatcher;
import sample.model.factories.detail.Detail;
import sample.model.factories.warehause.Warehouse;

public abstract class Factory extends EventDispatcher {
	public int index;
	
	Timeline timeline;
	boolean enable;
	boolean problem;
	boolean paused;
	
	public int total;
	private int speed;
	private int speedMax;
	private int speedMin;
	private int problemChance;
	private int problemDelay;
	
	int sliderStartPosition = 0;
	public int sliderPosition = 0;
	public int initSliderPosition;
	int deltaPosition = 34;
	double ratio;
	int moveDistance;
	
	public Warehouse warehouse;
	Detail detail;
	
	public Factory(int index) {
		this.index = index;
		
		init();
		
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setOnFinished(this::onUpdate);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(speed), timeline.getOnFinished()));
		
		initWarehouse();
		warehouse.addEventListener(this::warehouseListener);
	}
	protected abstract void initWarehouse();
	
	private void init() {
		enable = false;
		problem = false;
		paused = false;
		total = 0;
		problemChance = 1; //Config.getInstance().problemChance;
		problemDelay = 3000; //Config.getInstance().problemDelay;
		
		speedMin = Config.getInstance().factorysSpeedMin;
		speedMax = Config.getInstance().factorysSpeedMax;
		speed = Config.getInstance().factorysSpeed;
		
		int deltaValue = speedMax - speedMin;
		ratio = deltaValue / deltaPosition;
		setSpeedSliderStartPosition();
	}
	
	public void warehouseListener(EventObject e) {
		switch ((Event_Warehouse) e.getType()) {
			case RELEASED:
				if (paused) {
					if (enable) resume(); // standart resume
					else if (!enable) paused = false; // off resume
				}
				break;
		}
	}
	
	public void pause() {
		paused = true;
		
		timeline.stop();
		ModelFacade.fireEvent(this, Event.FACTORIES, Event_Factory.PAUSE);
	}
	
	public void resume() {
		paused = false;
		
		timeline.play();
		ModelFacade.fireEvent(this, Event.FACTORIES, Event_Factory.RESUME);
	}
	
	public void switcher() {
		if (!enable) {
			on();
		} else {
			off();
		}
	}
	
	void on() {
		enable = true;
		ModelFacade.fireEvent(this, Event.FACTORIES, Event_Factory.SWITCH_ON);
		
		if (paused) {
			ModelFacade.fireEvent(this, Event.FACTORIES, Event_Factory.PAUSE);
		} else {
			timeline.play();
		}
	}
	
	void off() {
		if (!problem) {
			enable = false;
			timeline.stop();
			
			if (paused) ModelFacade.fireEvent(this, Event.FACTORIES, Event_Factory.RESUME);
			ModelFacade.fireEvent(this, Event.FACTORIES, Event_Factory.SWITCH_OFF);
		}
	}
	
	void onUpdate(ActionEvent e) {
		if (!problem) {
			tryProblem();
			if (problem) return;
		}
		creating();
	}
	
	public void creating() {
		createDetail();
		warehousePut();
		
		totalAddNew();
		if (problem) toFixProblem();
		
		if (isCanAddNextDetail()) {
			if (!paused) pause();
		}
	}
	protected abstract void createDetail();
	
	void warehousePut() {
		warehouse.put(detail);
	}
	
	boolean isCanAddNextDetail() {
		if (warehouse.getOccupancy() + 1 > warehouse.getSize())
			return true;
		return false;
	}
	
	public void totalAddNew() {
		this.total = ++total;
		ModelFacade.fireEvent(this, Event.FACTORIES, Event_Factory.CREATED);
	}
	
	public void tryProblem() {
		if (Math.random() * 100 < problemChance) {
			
			problem = true;
			timeline.stop();
			ModelFacade.fireEvent(this, Event.FACTORIES, Event_Factory.PROBLEM);
			
			Timeline delay = new Timeline(new KeyFrame(Duration.millis(problemDelay)));
			delay.setOnFinished(e -> timeline.play());
			delay.play();
		}
	}
	
	void toFixProblem() {
		problem = false;
		ModelFacade.fireEvent(this, Event.FACTORIES, Event_Factory.PROBLEM_FIXED);
	}
	
	// speed slider --------------------------------------------------
	
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
		
		ModelFacade.fireEvent(this, Event.FACTORIES, Event_Factory.MOVE_SPEED_SLIDER);
	}
	
	public void mouseRealised(MouseEvent e) { // [relise]
		changeSpeed();
	}
	
	public void changeSpeed() {
		speed = (int) ((moveDistance) * ratio) + speedMin;
//		System.out.println(" > new speed: " + speed);
		
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
		ModelFacade.fireEvent(this, Event.FACTORIES, Event_Factory.INIT_SPEED_SLIDER);
	}
	
	public void pullDetail() {
		warehouse.pull();
	}
	
	public void clearWarehouse() {
		warehouse.clear();
	}
}
