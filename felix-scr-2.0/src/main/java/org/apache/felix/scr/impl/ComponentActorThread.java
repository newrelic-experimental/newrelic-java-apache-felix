package org.apache.felix.scr.impl;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.felix.scr.NRRunnableWrapper;

@Weave
abstract class ComponentActorThread {

	void schedule( Runnable task ) {
		Token t = NewRelic.getAgent().getTransaction().getToken();
		if(!(task instanceof NRRunnableWrapper)) {
			if(t != null && t.isActive()) {
				task = new NRRunnableWrapper(task, t);
			} else if(t != null) {
				t.expire();
				t = null;
			}
		}
		Weaver.callOriginal();
	}
}
