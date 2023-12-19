package org.apache.felix.eventadmin.impl.handler;

import org.osgi.service.event.Event;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class EventHandlerProxy {

	public abstract String getInfo();
	
	@Trace(dispatcher = true)
	public void sendEvent(Event event) {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Info", getInfo());
		Weaver.callOriginal();
	}
}
