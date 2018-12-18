package sample.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.awt.Point;

public class EngineSupplier {
	
	Pane parent;
	
	Image onRes = new Image("sample/_res/on.png");
	ImageView switcherOn = new ImageView(onRes);
	
	Image runningImg = new Image("sample/_res/runing.png");
	ImageView running = new ImageView(runningImg);
	
	public ImageView switcherOff;
	ImageView offIcon;
	Label switchText;
	Label totalText;
	
	Timeline timeline;
	
	public EngineSupplier(
			Pane parent,
			ImageView switcherOff, Label switchText, ImageView iconOff, Label totalText) {
		this.parent = parent;
		this.switcherOff = switcherOff;
		this.switchText = switchText;
		this.offIcon = iconOff;
		this.totalText = totalText;
		
		timeline = new Timeline();
		timeline.setOnFinished(this::onUpdate);
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		timeline.getKeyFrames().add(new KeyFrame(
				Duration.millis(50), timeline.getOnFinished()));
		
		
		
		init();
	}
	
	private void init() {
		switchText.setText("off");
		totalText.setText("0");
	}
	
	private void onUpdate(ActionEvent e) {
		running.setRotate(running.getRotate() + 5);
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
	}
	
	public void off() {
		switchText.setText("off");
		
		parent.getChildren().remove(switcherOn);
		parent.getChildren().remove(running);
		
		timeline.stop();
	}
	
	public void made(int total) {
		totalText.setText(String.valueOf(total));
	}
}
