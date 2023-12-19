package org.apache.felix.eventadmin.impl.tasks;

import java.util.Collection;

import org.apache.felix.eventadmin.impl.handler.EventHandlerProxy;
import org.osgi.service.event.Event;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class SyncDeliverTasks {
	
	@NewField
	protected Token token = null;

	public SyncDeliverTasks(DefaultThreadPool pool, long timeout) {
		
	}
	
	@Trace(async = true)
	public void execute(Collection<EventHandlerProxy> tasks, Event event, boolean filterAsyncUnordered) {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("EventTopic", event.getTopic());
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}
	
}
