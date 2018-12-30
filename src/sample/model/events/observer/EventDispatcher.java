package sample.model.events.observer;

import sample.model.events.EventObject;

import java.util.ArrayList;
import java.util.List;

public abstract class EventDispatcher {
	
	List<EventListener> listeners = new ArrayList<>();
	
	public void addEventListener(EventListener l) {
		listeners.add(l);
	}
	
	public void removeEventListener(EventListener l) {
		listeners.remove(l);
	}
	
	public void dispatchEvent(Object source, Enum event,  Enum eventType) {
		EventObject eventObject = new EventObject(source, event, eventType);
		for (EventListener l : listeners) {
			l.handle(eventObject);
		}
	}
}
