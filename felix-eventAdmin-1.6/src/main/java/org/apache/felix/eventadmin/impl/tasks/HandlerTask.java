package org.apache.felix.eventadmin.impl.tasks;

import org.apache.felix.eventadmin.impl.handler.EventHandlerProxy;
import org.osgi.service.event.Event;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class HandlerTask {
	
	private final EventHandlerProxy task = Weaver.callOriginal();
	private final Event event = Weaver.callOriginal();


	@Trace
	public void run() {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("TaskInfo", task.getInfo());
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("EventTopic", event.getTopic());
		Weaver.callOriginal();
	}
	
	@Trace
	public void runWithoutBlacklistTiming() {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("TaskInfo", task.getInfo());
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("EventTopic", event.getTopic());
		Weaver.callOriginal();
	}
}
