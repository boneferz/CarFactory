package sample.model.suppliers.warehauses;

import sample.model.ModelFacade;
import sample.model.data.Config;
import sample.model.events.Custom_EventObject;
import sample.model.events.EventType;
import sample.model.events.Supplier_Events;
import sample.model.events.Warehouse_Events;
import sample.model.suppliers.details.Engine;

import java.util.ArrayList;
import java.util.List;

public class EngineWarehouse {
	
	public int index;
	
	private int size;
	public int occupancy;
	private List<Engine> warehouseArr;
	
	public EngineWarehouse(int index) {
		this.index = index;
		
		warehouseArr = new ArrayList<>();
		size = Config.getInstance().warehouseSizeMax;
		occupancy = 0;
	}
	
	public boolean put(Engine detail) {
		warehouseArr.add(detail);
		setOccupancy(++occupancy);
		
		if (occupancy + 1 > size) {
			return false;
		}
		
		return true;
	}
	
	public void clear() {
		warehouseArr.clear();
		setOccupancy(0);
	}
	
	void setOccupancy(int i) {
		occupancy = i;
		ModelFacade.fireEvent(this, EventType.WAREHOUSE, Warehouse_Events.UPDATE);
	}
}
