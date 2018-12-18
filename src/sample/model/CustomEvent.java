package sample.model;

public class CustomEvent {
	String eventName;
	int index;
	
	public CustomEvent(String eventName, int index) {
		this.eventName = eventName;
		this.index = index;
	}
	
	public CustomEvent(String eventName) {
		this.eventName = eventName;
	}
	
	public String getEventName() {
		return eventName;
	}
	
	public int getIndex() {
		return index;
	}
}
