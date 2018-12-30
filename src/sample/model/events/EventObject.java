package sample.model.events;

public class EventObject extends java.util.EventObject {
	
	private Enum eventType;
	private Enum event;
	
	public EventObject(Object source, Enum event, Enum eventType) {
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
