package sample.model.suppliers.warehause;

import sample.model.FacadeModel;
import sample.model.data.Config;
import sample.model.events.EventType;
import sample.model.events.Warehouse_Events;
import sample.model.observer.EventDispatcher;
import sample.model.suppliers.detail.Detail;

import java.util.ArrayList;
import java.util.List;

public abstract class Warehouse extends EventDispatcher {
	public int index;
	
	public int size;
	public int occupancy;
	private List<Detail> warehouseArr;
	
	public Warehouse(int index) {
		this.index = index;
		
		warehouseArr = new ArrayList<>();
		size = Config.getInstance().warehouseSizeMax;
		occupancy = 0;
	}
	
	public void put(Detail detail) {
		warehouseArr.add(detail);
		setOccupancy(++occupancy);
	}
	
	void setOccupancy(int i) {
		occupancy = i;
		dispatchEvent(this, EventType.WAREHOUSE, Warehouse_Events.RELEASED);
		FacadeModel.fireEvent(this, EventType.WAREHOUSE, Warehouse_Events.UPDATE);
		
		Detail detail;
		
		for (int j = 0; j < warehouseArr.size(); j++) {
			detail = warehouseArr.get(j);
			System.out.println( detail.id + ":id , " + detail.getClass().getSimpleName());
		}
		System.out.println("");
	}
	
	public void clear() {
		setOccupancy(0);
		warehouseArr.clear();
	}
	
	public Detail pull() {
		System.out.println("-");
		if (occupancy > 0) {
			setOccupancy(--occupancy);
			return warehouseArr.remove(0);
		}
		return null;
	}
}
