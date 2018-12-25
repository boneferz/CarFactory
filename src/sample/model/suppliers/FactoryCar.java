package sample.model.suppliers;

import sample.model.data.GlobalData;
import sample.model.suppliers.warehause.Warehouse;
import sample.model.suppliers.warehause.WarehouseCar;

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
		Warehouse warehouseE = GlobalData.getInstance().warehouseEngine;
		Warehouse warehouseB = GlobalData.getInstance().warehouseBody;
		Warehouse warehouseA = GlobalData.getInstance().warehouseAccessories;
		
		System.out.println(warehouseE.getClass().getSimpleName());
		System.out.println(warehouseE.getOccupancy());
		System.out.println(warehouseB.getClass().getSimpleName());
		System.out.println(warehouseB.getOccupancy());
		System.out.println(warehouseA.getClass().getSimpleName());
		System.out.println(warehouseA.getOccupancy());
		System.out.println("");
		
		if (warehouseE.getOccupancy() > 0
				&& warehouseB.getOccupancy() > 0
				&& warehouseA.getOccupancy() > 0) {
			warehouseE.pull();
			warehouseB.pull();
			warehouseA.pull();
		} else {
			pause();
		}
		
//		Detail engine = warehouseE.pull();

//		if (warehouseList.size() > 0) {
//			if (warehouseList.get(0).size > 0) {
//				if (engine != null) {
//					System.out.println(engine.getClass().getSimpleName());
//				} else {
//					System.out.println(engine);
//				}
//			}
//		} else {
//			System.out.println("ERROR!");
//		}


//		Detail body = warehouseList.get(1).pull();
//		Detail accessorie = warehouseList.get(2).pull();

		
//		 car creating  (body + engine + accessories(x3))
//		 ...
//		 request [pull] details
//		 else wait
		
		//Car car = new Car(total, engine, body, accessorie);
//		Car car = new Car(total);
		//warehouse.(car);
	}
}
