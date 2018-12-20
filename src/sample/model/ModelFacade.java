package sample.model;

import sample.model.events.Custom_EventObject;
import sample.model.observer.EventDispatcher;
import java.util.HashMap;
import java.util.Map;

public class ModelFacade {
	
	Model model;
	
	public ModelFacade() {
		model = new Model();
	}
	
	
	// fasade
	public void engineSupplierSwitcher(int i) {
		model.suppliers[i].switcher();
	}
	public void engineWarehouseXBtn(int i) {
		model.suppliers[i].warehouse.clear();
	}
	
	
	// Observable
	private static Map<Enum, EventDispatcher> map = new HashMap<>();
	
	public void addEventListener(Enum eventType, EventDispatcher listener) {
		map.put(eventType, listener);
	}
	public static void fireEvent(Object source, Enum eventType, Enum event) {
		Custom_EventObject eventObject = new Custom_EventObject(source, eventType, event);
		for (Enum eventTypeKey : map.keySet()) {
			if (eventObject.getType().equals(eventTypeKey))
				map.get(eventTypeKey).dispatchEvent(eventObject);
		}
	}
	
	
}
