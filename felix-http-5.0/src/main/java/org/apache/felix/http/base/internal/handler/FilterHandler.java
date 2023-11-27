package org.apache.felix.http.base.internal.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class FilterHandler {

	public abstract String getName();
	
	public void handle(ServletRequest req, ServletResponse res, FilterChain chain) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Felix","FilterHandler",getName(),"handle");
		Weaver.callOriginal();
	}
}
