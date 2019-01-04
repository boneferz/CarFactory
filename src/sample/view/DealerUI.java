package sample.view;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.model.Dealers;

public class DealerUI {

	Pane parent;
	Image iconPauseImg = new Image("sample/_res/dealWain.png");
	ImageView iconPause = new ImageView(iconPauseImg);
	ImageView iconWork;
	Label total;
	Label count;
	//	ImageView speedSlider;
	
	public DealerUI(Pane root, ImageView iconWork, Label total, Label count) {
		this.parent = root;
		this.iconWork = iconWork;
		this.total = total;
		this.count = count;
		
		total.setText("0");
		count.setText("0" + "/5");
		count.setStyle("-fx-text-fill: orange");
	}
	
	public void pause() {
		parent.getChildren().add(iconPause);
		iconPause.setX(iconWork.getLayoutX());
		iconPause.setY(iconWork.getLayoutY());
		iconWork.setOpacity(0);
	}
	
	public void resume() {
		parent.getChildren().remove(iconPause);
		iconWork.setOpacity(1);
	}
	
	public void updateTotal(Object sourse) {
		Dealers dealer = (Dealers) sourse;
		
		total.setText(String.valueOf(dealer.getTotal()));
		count.setText(String.valueOf(dealer.getOccupancy()) + "/" + String.valueOf(dealer.getSize()));
		
		if (dealer.getOccupancy() == 0) {
			count.setStyle("-fx-text-fill: orange");
		} else {
			count.setStyle("-fx-text-fill: #00000080");
		}
	}
}
