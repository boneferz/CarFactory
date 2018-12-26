package sample.model.events.observer;

import sample.model.events.Custom_EventObject;

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
	
	public void dispatchEvent(Object source, Enum eventType, Enum event) {
		Custom_EventObject eventObject = new Custom_EventObject(source, eventType, event);
		for (EventListener l : listeners) {
			l.handle(eventObject);
		}
	}
}
