package sample.model.factory;

import sample.model.suppliers.Factory;
import sample.model.suppliers.detail.Detail;

public class FactoryCar extends Factory {
	
	public FactoryCar(int index) {
		super(index);
	}
	
	@Override
	public void initWarehouse() {
		super.warehouse = new WarehouseCar(index);
		super.warehouse.addEventListener(this::warehouseListener);
	}
	
	@Override
	protected void createDetail() {
		// car creating  (body + engine + accessories(x3))
		// ...
		// request [pull] details
		// else wait
		
		Car car = new Car(total);
		warehouse.put(car);
	}
}
