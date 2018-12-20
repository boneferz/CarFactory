package sample.model.observer;

import sample.model.events.Custom_EventObject;

public interface EventDispatcher {
	void dispatchEvent(Custom_EventObject event);
}
