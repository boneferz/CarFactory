package sample.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sample.model.suppliers.EnginesSupplier;

public class Factory {
	
	Pane parent;
	
	Image onRes = new Image("sample/_res/on.png");
	ImageView switcherOn = new ImageView(onRes);
	
	Image runningImg = new Image("sample/_res/runing.png");
	ImageView running = new ImageView(runningImg);
	
	Image waitImg = new Image("sample/_res/wait.png");
	ImageView wait = new ImageView(waitImg);
	
	Image problemImg = new Image("sample/_res/problem.png");
	ImageView problem = new ImageView(problemImg);
	
	public ImageView switcherOff;
	ImageView offIcon;
	Label switchText;
	Label totalText;
	ImageView speedSlider;
	
	Timeline timeline;
	int speedSliderStartX = 0;
	
	public Factory(
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
		
		timeline.getKeyFrames().add(new KeyFrame(
				Duration.millis(50), timeline.getOnFinished()));
		
		init();
	}
	
	private void init() {
		totalText.setStyle("-fx-text-fill: gainsboro");
		switchText.setText("off");
		totalUpdate(0);
		
		speedSliderStartX = (int) speedSlider.getLayoutX();
	}
	
	private void onUpdate(ActionEvent e) {
		running.setRotate(running.getRotate() + 5);
	}
	
	public void totalUpdate(int total) {
		totalText.setText(String.valueOf(total));
	}
	
	public void on() {
		switchText.setText("on");
		
		parent.getChildren().add(switcherOn);
		
		switcherOn.setX(switcherOff.getLayoutX());
		switcherOn.setY(switcherOff.getLayoutY());
		switcherOn.setDisable(true);
		
		parent.getChildren().add(running);
		running.setX(offIcon.getLayoutX());
		running.setY(offIcon.getLayoutY());
		
		timeline.play();
		totalText.setStyle("-fx-text-fill: black");
	}
	public void off() {
		switchText.setText("off");
		
		parent.getChildren().remove(switcherOn);
		parent.getChildren().remove(running);
		
		timeline.stop();
		totalText.setStyle("-fx-text-fill: gainsboro");
	}
	
	public void pause() {
		parent.getChildren().add(wait);
		
		wait.setX(offIcon.getLayoutX());
		wait.setY(offIcon.getLayoutY());
		
		totalText.setStyle("-fx-text-fill: orange");
	}
	public void resume() {
		parent.getChildren().remove(wait);
		totalText.setStyle("-fx-text-fill: black");
	}
	
	public void problem() {
		parent.getChildren().add(problem);
		
		problem.setX(offIcon.getLayoutX());
		problem.setY(offIcon.getLayoutY());
		
		totalText.setStyle("-fx-text-fill: crimson");
	}
	public void problemFixed() {
		parent.getChildren().remove(problem);
		totalText.setStyle("-fx-text-fill: black");
	}
	
	public void moveSpeedSlider(Object object) {
		EnginesSupplier supplier = (EnginesSupplier) object;
		speedSlider.setX(supplier.sliderPosition - speedSliderStartX);
	}
	
	public void initSpeedSlider(Object source) {
		EnginesSupplier supplier = (EnginesSupplier) source;
		speedSlider.setX(supplier.initSliderPosition);
	}
}
