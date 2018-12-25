package sample.model.events.observer;

import sample.model.events.Custom_EventObject;

import java.util.HashMap;
import java.util.Map;

public abstract class EventDispatcherTyped {
	
	private static Map<Enum, EventListener> map = new HashMap<>();
	
	public void addEventListener(Enum eventType, EventListener listener) {
		map.put(eventType, listener);
	}
	public static void fireEvent(Object source, Enum eventType, Enum event) {
		Custom_EventObject eventObject = new Custom_EventObject(source, eventType, event);
		for (Enum eventTypeKey : map.keySet()) {
			if (eventObject.getType().equals(eventTypeKey))
				map.get(eventTypeKey).handle(eventObject);
		}
	}
}
