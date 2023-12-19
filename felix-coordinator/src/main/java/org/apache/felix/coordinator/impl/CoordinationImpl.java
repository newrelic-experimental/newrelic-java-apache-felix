package org.apache.felix.coordinator.impl;

import java.util.HashMap;

import org.osgi.framework.Bundle;
import org.osgi.service.coordinator.Coordination;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class CoordinationImpl {
	
	public abstract String getName();
	public abstract Bundle getBundle();
	
	@Trace
	public boolean fail(Throwable reason) {
		if(reason != null) {
			HashMap<String, Object> attributes = new HashMap<>();
			attributes.put("CoordinationImpl-Name", getName());
			Bundle thisBundle = getBundle();
			if(thisBundle != null) {
				attributes.put("Bundle-SymbolicName", thisBundle.getSymbolicName());
				attributes.put("Bundle-Location", thisBundle.getLocation());			
			}
			NewRelic.noticeError(reason,attributes);
		}
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Felix","Coordinator","CoordinationImpl","fail");
		return Weaver.callOriginal();
	}

	@Trace
	public void join(long timeOutInMs) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Felix","Coordinator","CoordinationImpl","join");
		Weaver.callOriginal();
	}
	
	@Trace
	 public Coordination push() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Felix","Coordinator","CoordinationImpl","push");
		 return Weaver.callOriginal();
	 }
}
