package sample.model.events;

import java.util.EventObject;

public class Custom_EventObject extends EventObject {
	private Enum eventType;
	private Enum event;
	
	public Custom_EventObject(Object source, Enum event, Enum eventType) {
		super(source);
		
		this.event = event;
		this.eventType = eventType;
	}
	
	public Enum getType() {
		return eventType;
	}
	
	public Enum getEvent() {
		return event;
	}
}
