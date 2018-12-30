package sample.model.events.observer;

import sample.model.events.EventObject;

public interface EventListener {
	void handle(EventObject event);
}
