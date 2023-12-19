package org.apache.felix.eventadmin.impl.tasks;

import org.osgi.service.event.Event;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class HandlerTask {
	
	private final Event event = Weaver.callOriginal();


	@Trace
	public void run() {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("EventTopic", event.getTopic());
		Weaver.callOriginal();
	}
	
	@Trace
	public void runWithoutBlacklistTiming() {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("EventTopic", event.getTopic());
		Weaver.callOriginal();
	}
}
