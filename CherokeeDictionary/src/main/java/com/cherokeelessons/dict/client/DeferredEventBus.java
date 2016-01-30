package com.cherokeelessons.dict.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.Event.Type;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class DeferredEventBus extends EventBus {
	private final EventBus wrapped;

	public DeferredEventBus() {
	    this.wrapped = new SimpleEventBus();
	  }
	
	@Override
	public <H> HandlerRegistration addHandler(Type<H> type, H handler) {
		return wrapped.addHandler(type, handler);
	}

	@Override
	public <H> HandlerRegistration addHandlerToSource(Type<H> type, Object source, H handler) {
		return wrapped.addHandlerToSource(type, source, handler);
	}

	@Override
	public void fireEvent(final Event<?> event) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				wrapped.fireEvent(event);
			}
		});
	}

	@Override
	public void fireEventFromSource(final Event<?> event, final Object source) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				wrapped.fireEventFromSource(event, source);
			}
		});
	}
}
