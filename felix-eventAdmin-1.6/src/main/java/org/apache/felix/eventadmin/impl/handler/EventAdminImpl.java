package org.apache.felix.eventadmin.impl.handler;

import org.osgi.service.event.Event;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class EventAdminImpl {

	@Trace(dispatcher = true)
	public void sendEvent(Event event) {
		String[] metricNames;
		boolean check = checkTopic(event);
		if(check) {
			if(event != null) {
				metricNames = new String[] {"Custom","Felix","EventAdmin","sendEvent",event.getTopic()};
			} else {
				metricNames = new String[] {"Custom","Felix","EventAdmin","sendEvent","Unknown"};
			}
		} else {
			metricNames = new String[] {"Custom","Felix","EventAdmin","sendEvent","Ignored"};
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(metricNames);

		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void postEvent(Event event) {
		String[] metricNames;
		boolean check = checkTopic(event);
		if(check) {
			if(event != null) {
				metricNames = new String[] {"Custom","Felix","EventAdmin","postEvent",event.getTopic()};
			} else {
				metricNames = new String[] {"Custom","Felix","EventAdmin","postEvent","Unknown"};
			}
		} else {
			metricNames = new String[] {"Custom","Felix","EventAdmin","postEvent","Ignored"};
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(metricNames);

		Weaver.callOriginal();
	}

	@Trace
	public void update(int timeout, String[] ignoreTimeout, boolean requireTopic, String[] ignoreTopics) {
		Weaver.callOriginal();
	}

	private boolean checkTopic(Event event) {
		return Weaver.callOriginal();
	}
}
