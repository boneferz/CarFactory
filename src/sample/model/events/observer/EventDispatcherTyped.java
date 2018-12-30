package sample.model.events.observer;

import sample.model.events.EventObject;

import java.util.HashMap;
import java.util.Map;

public abstract class EventDispatcherTyped {
	
	private static Map<Enum, EventListener> map = new HashMap<>();
	
	public void addEventListener(Enum event, EventListener listener) {
		map.put(event, listener);
	}
	public static void fireEvent(Object source, Enum event, Enum eventType) {
		EventObject eventObject = new EventObject(source, event, eventType);
		
		for (Enum eventKey : map.keySet()) {
			if (eventObject.getEvent().equals(eventKey))
				map.get(eventKey).handle(eventObject);
		}
	}
}
