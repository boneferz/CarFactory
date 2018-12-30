package sample.model.factories.warehause;

import sample.model.ModelFacade;
import sample.model.data.Config;
import sample.model.events.Event;
import sample.model.events.Event_Warehouse;
import sample.model.events.observer.EventDispatcher;
import sample.model.factories.detail.Detail;
import java.util.ArrayList;
import java.util.List;

public abstract class Warehouse extends EventDispatcher {
	
	private int index;
	private int size;
	private int occupancy = 0;
	private List<Detail> warehouseArr = new ArrayList<>();
	
	public Warehouse(int index) {
		this.index = index;
		size = Config.getInstance().warehouseSizeMax;
	}
	
	public void put(Detail detail) {
		setOccupancy(++occupancy);
		warehouseArr.add(detail);
		dispatchEvent(this, Event.WAREHOUSE, Event_Warehouse.ADDED);
	}
	
	public Detail pull() {
		if (occupancy > 0) {
			setOccupancy(--occupancy);
			dispatchEvent(this, Event.WAREHOUSE, Event_Warehouse.RELEASED);
			return warehouseArr.remove(0);
		}
		return null;
	}
	
	void setOccupancy(int i) {
		occupancy = i;
		ModelFacade.fireEvent(this, Event.WAREHOUSE, Event_Warehouse.UPDATE);
	}
	
	public void clear() {
		setOccupancy(0);
		warehouseArr.clear();
		dispatchEvent(this, Event.WAREHOUSE, Event_Warehouse.RELEASED);
		
	}
	
	
	
	public int getSize() {
		return size;
	}
	public int getOccupancy() {
		return occupancy;
	}
	public int getIndex() {
		return index;
	}
	public List<Detail> getWarehouseArr() {
		return warehouseArr;
	}
}
