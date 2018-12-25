package sample.model.observer;

import sample.model.events.Custom_EventObject;

public interface EventListener {
	void handle(Custom_EventObject event);
}
