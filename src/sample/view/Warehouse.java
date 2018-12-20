package sample.view;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import sample.model.data.Config;

public class Warehouse {
	
	private Label totalText;
	private ImageView xBtn;
	private ImageView totalLine;
	
	public Warehouse(Label totalText, ImageView xBtn, ImageView totalLine) {
		this.totalText = totalText;
		this.xBtn = xBtn;
		this.totalLine = totalLine;
		
		totalUpdate(0);
	}
	
	void totalUpdate(int i) {
		totalText.setText(i + "/" + Config.getInstance().warehouseSizeMax);
		
		totalLine.getTransforms().clear();
		
		float scaleValue;
		if (i != 0) {
			scaleValue = getScaleValue(Config.getInstance().warehouseSizeMax, i);
			System.out.println(scaleValue);
			System.out.println("");
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
		float aFloat = (float) a;
		float bFloat = (float) b;
		System.out.println("a:" + a + ", b:" + b);
		System.out.println("a/b:" + (a/b));
		float proc = 100 / (aFloat / bFloat);
		System.out.println("proc (100/(a/b)):" + proc);
		return proc / 100;
	}
}
