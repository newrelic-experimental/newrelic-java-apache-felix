package org.apache.felix.eventadmin.impl.handler;

import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class EventHandlerProxy {

	private final ServiceReference<EventHandler> reference = Weaver.callOriginal();
	
	@Trace(dispatcher = true)
	public void sendEvent(Event event) {
		String info = this.reference.toString() + " [Bundle " + this.reference.getBundle() + "]";
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Info", info);
		Weaver.callOriginal();
	}
}
