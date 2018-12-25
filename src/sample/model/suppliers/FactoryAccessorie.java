package sample.model.suppliers;

import sample.model.data.GlobalData;
import sample.model.suppliers.detail.Accessories;
import sample.model.suppliers.warehause.WarehouseAccessorie;

public class FactoryAccessorie extends Factory {
	
	public FactoryAccessorie(int index) {
		super(index);
	}
	
	@Override
	protected void initWarehouse() {
		super.warehouse = new WarehouseAccessorie(index);
		super.warehouse.addEventListener(this::warehouseListener);
		
		GlobalData.getInstance().warehouseAccessories = warehouse;
	}
	
	@Override
	protected void createDetail() {
		Accessories accessories = new Accessories(total);
		warehouse.put(accessories);
	}
}
