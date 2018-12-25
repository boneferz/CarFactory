package sample.view;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import sample.model.data.Config;

public class WarehouseUI {
	
	private Label totalText;
	private ImageView xBtn;
	private ImageView totalLine;
	
	public WarehouseUI(Label totalText, ImageView xBtn, ImageView totalLine) {
		this.totalText = totalText;
		this.xBtn = xBtn;
		this.totalLine = totalLine;
		
		totalUpdate(0);
	}
	
	public void totalUpdate(int i) {
		totalText.setText(i + "/" + Config.getInstance().warehouseSizeMax);
		
		totalLine.getTransforms().clear();
		
		float scaleValue;
		if (i != 0) {
			scaleValue = getScaleValue(Config.getInstance().warehouseSizeMax, i);
		} else {
			scaleValue = 0;
		}
		
		Scale scale = new Scale();
		scale.setPivotX(0);
		scale.setX(scaleValue);
		totalLine.getTransforms().add(scale);
		
		totalLine.setScaleX(1);
	}
	
	public float getScaleValue(int a, int b) {
		float proc = 100 / ((float) a / (float) b);
		return proc / 100;
	}
}
