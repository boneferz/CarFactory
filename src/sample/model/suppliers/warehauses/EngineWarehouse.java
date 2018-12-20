package sample.model.suppliers.warehauses;

import sample.model.ModelFacade;
import sample.model.data.Config;
import sample.model.events.Custom_EventObject;
import sample.model.events.EventType;
import sample.model.events.Supplier_Events;
import sample.model.events.Warehouse_Events;
import sample.model.observer.EventDispatcher;
import sample.model.suppliers.details.Engine;

import java.nio.file.WatchEvent;
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
	
	public void put(Engine detail) {
		warehouseArr.add(detail);
		setOccupancy(++occupancy);
		
		if (occupancy + 1 > size) {
			dispatchEvent(this, EventType.WAREHOUSE, Warehouse_Events.OVERFLOW);
		}
	}
	
	public void clear() {
		setOccupancy(0);
		warehouseArr.clear();
		
		ModelFacade.fireEvent(this, EventType.WAREHOUSE, Warehouse_Events.RELEASED);
		dispatchEvent(this, EventType.WAREHOUSE, Warehouse_Events.RELEASED);
	}
	
	void setOccupancy(int i) {
		occupancy = i;
		ModelFacade.fireEvent(this, EventType.WAREHOUSE, Warehouse_Events.UPDATE);
	}
	
	// event dispatching
	EventDispatcher listener;
	public void addEventListener(EventDispatcher l) {
		this.listener = l;
	}
	public void dispatchEvent(Object source, Enum eventType, Enum event) {
		Custom_EventObject eventObject = new Custom_EventObject(source, eventType, event);
		listener.dispatchEvent(eventObject);
	}
}
