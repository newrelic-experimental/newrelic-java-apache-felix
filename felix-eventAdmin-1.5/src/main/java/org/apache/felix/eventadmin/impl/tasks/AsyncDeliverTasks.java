package org.apache.felix.eventadmin.impl.tasks;

import java.util.Collection;

import org.apache.felix.eventadmin.impl.handler.EventHandlerProxy;
import org.osgi.service.event.Event;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class AsyncDeliverTasks {
	
	private final SyncDeliverTasks m_deliver_task = Weaver.callOriginal();

	@Trace
	public void execute(Collection<EventHandlerProxy> tasks, Event event) {
		if(m_deliver_task != null && m_deliver_task.token == null) {
			Token t = NewRelic.getAgent().getTransaction().getToken();
			if(t != null && t.isActive()) {
				m_deliver_task.token = t;
			} else if(t != null) {
				t.expire();
				t = null;
			}
		}
		Weaver.callOriginal();
	}
}
