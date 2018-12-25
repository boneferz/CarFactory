package sample.model.events.observer;

import sample.model.events.Custom_EventObject;

public abstract class EventDispatcher {
	
	EventListener listener;
	
	public void addEventListener(EventListener l) {
		this.listener = l;
	}
	
	public void dispatchEvent(Object source, Enum eventType, Enum event) {
		Custom_EventObject eventObject = new Custom_EventObject(source, eventType, event);
		listener.handle(eventObject);
	}
}
