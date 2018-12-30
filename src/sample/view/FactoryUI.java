package sample.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sample.model.factories.Factory;

public class FactoryUI {
	
	Pane parent;
	
	Image onRes = new Image("sample/_res/on.png");
	ImageView switcherOn = new ImageView(onRes);
	
	Image runningImg = new Image("sample/_res/runing.png");
	ImageView running = new ImageView(runningImg);
	
	Image pauseImg = new Image("sample/_res/pause.png");
	ImageView pause = new ImageView(pauseImg);
	
	Image problemImg = new Image("sample/_res/problem.png");
	ImageView problem = new ImageView(problemImg);
	
	Image loadingImg = new Image("sample/_res/loading.png");
	ImageView loading = new ImageView(loadingImg);

	Image waitImg = new Image("sample/_res/wait.png");
	ImageView wait = new ImageView(waitImg);
	
	public ImageView switcherOff;
	ImageView offIcon;
	Label switchText;
	Label totalText;
	ImageView speedSlider;
	
	Timeline timeline;
	int speedSliderStartX = 0;
	
	public FactoryUI(
			Pane parent,
			ImageView switcherOff, Label switchText, ImageView iconOff, Label totalText, ImageView speedSlider) {
		this.parent = parent;
		this.switcherOff = switcherOff;
		this.switchText = switchText;
		this.offIcon = iconOff;
		this.totalText = totalText;
		this.speedSlider = speedSlider;
		
		timeline = new Timeline();
		timeline.setOnFinished(this::onUpdate);
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		
		// 10, 5
		timeline.getKeyFrames().add(
				new KeyFrame(Duration.millis(40), timeline.getOnFinished()));
		timeline.play();
		
		speedSliderStartX = (int) speedSlider.getLayoutX();
		
		totalText.setStyle("-fx-text-fill: gainsboro");
		switchText.setText("off");
		totalText.setText(String.valueOf("0"));
		
		switcherOn.setX(switcherOff.getLayoutX());
		switcherOn.setY(switcherOff.getLayoutY());
		switcherOn.setDisable(true);
		
		running.setX(offIcon.getLayoutX());
		running.setY(offIcon.getLayoutY());
		
		pause.setX(offIcon.getLayoutX());
		pause.setY(offIcon.getLayoutY());
		
		problem.setX(offIcon.getLayoutX());
		problem.setY(offIcon.getLayoutY());
		
		wait.setX(offIcon.getLayoutX() + 1);
		wait.setY(offIcon.getLayoutY() + 1);
		
		loading.setX(offIcon.getLayoutX() + 1);
		loading.setY(offIcon.getLayoutY() + 1);
	}
	
	private void onUpdate(ActionEvent e) {
		running.setRotate(running.getRotate() - 6);
		loading.setRotate(loading.getRotate() - 2);
	}
	
	public void totalUpdate(int total) {
		totalText.setText(String.valueOf(total));
	}
	
	// on / off
	public void on() {
		switchText.setText("on");
		parent.getChildren().add(switcherOn);
		parent.getChildren().add(running);
		totalText.setStyle("-fx-text-fill: black");
	}
	public void off() {
		switchText.setText("off");
		parent.getChildren().remove(switcherOn);
		parent.getChildren().remove(running);
		totalText.setStyle("-fx-text-fill: gainsboro");
	}
	
	// pause
	public void pause() {
		parent.getChildren().add(pause);
		totalText.setStyle("-fx-text-fill: orange");
	}
	public void resume() {
		parent.getChildren().remove(pause);
		totalText.setStyle("-fx-text-fill: black");
	}
	
	// problem
	public void problem() {
		parent.getChildren().add(problem);
		totalText.setStyle("-fx-text-fill: crimson");
	}
	public void problemFixed() {
		parent.getChildren().remove(problem);
		totalText.setStyle("-fx-text-fill: black");
	}
	
	// SpeedSlider
	public void moveSpeedSlider(Object object) {
		Factory supplier = (Factory) object;
		speedSlider.setX(supplier.sliderPosition - speedSliderStartX);
	}
	public void initSpeedSlider(Object source) {
		Factory supplier = (Factory) source;
		speedSlider.setX(supplier.initSliderPosition);
	}
	
	// factoryCar ---------------------------------------------
	
	//  on/off
	public void enable() {
		switchText.setText("on");
		parent.getChildren().add(loading);
		parent.getChildren().add(switcherOn);
		totalText.setStyle("-fx-text-fill: black");
	}
	public void disable() {
		switchText.setText("off");
		parent.getChildren().remove(loading);
		parent.getChildren().remove(switcherOn);
		totalText.setStyle("-fx-text-fill: gainsboro");
	}
	
	// wait - Details
	public void waitOn() {
		parent.getChildren().add(wait);
	}
	public void waitOff() {
		parent.getChildren().remove(wait);
	}
	
	// wait - Task
	public void workOn() {
		parent.getChildren().add(running);
	}
	public void workOff() {
		parent.getChildren().remove(running);
	}
}
