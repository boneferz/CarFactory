package sample.model.suppliers;

import sample.model.data.GlobalData;
import sample.model.suppliers.detail.Body;
import sample.model.suppliers.warehause.WarehouseBody;

public class FactoryBody extends Factory {
	
	public FactoryBody(int index) {
		super(index);
	}
	
	@Override
	protected void initWarehouse() {
		super.warehouse = new WarehouseBody(index);
		super.warehouse.addEventListener(this::warehouseListener);
		
		GlobalData.getInstance().warehouseBody = warehouse;
	}
	
	@Override
	protected void createDetail() {
		Body body = new Body(total);
		warehouse.put(body);
	}
}
