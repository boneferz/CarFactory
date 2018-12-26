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
		GlobalData.getInstance().warehouseEngine = warehouse;
		
		super.warehouse.addEventListener(this::warehouseListener);
	}
	
	@Override
	protected void createDetail() {
		Engine engine = new Engine(total);
		warehouse.put(engine);
	}
}