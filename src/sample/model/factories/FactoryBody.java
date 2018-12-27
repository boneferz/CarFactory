package sample.model.factories;

import sample.model.data.GlobalData;
import sample.model.factories.detail.Body;
import sample.model.factories.warehause.WarehouseBody;

public class FactoryBody extends Factory {
	
	public FactoryBody(int index) {
		super(index);
	}
	
	@Override
	protected void initWarehouse() {
		super.warehouse = new WarehouseBody(index);
		GlobalData.getInstance().factoryB = this;
		GlobalData.getInstance().warehouseBody = warehouse;
	}
	
	@Override
	protected void createDetail() {
		detai = new Body(total);
	}
}
