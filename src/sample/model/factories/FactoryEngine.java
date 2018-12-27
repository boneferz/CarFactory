package sample.model.factories;

import sample.model.data.GlobalData;
import sample.model.factories.detail.Engine;
import sample.model.factories.warehause.WarehouseEngine;

public class FactoryEngine extends Factory {
	
	public FactoryEngine(int index) {
		super(index);
	}
	
	@Override
	protected void initWarehouse() {
		super.warehouse = new WarehouseEngine(index);
		GlobalData.getInstance().factoryE = this;
		GlobalData.getInstance().warehouseEngine = warehouse;
	}
	
	@Override
	protected void createDetail() {
		detai = new Engine(total);
	}
}
