package org.apache.felix.http.base.internal.dispatch;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.felix.http.base.internal.util.UriUtils;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class RequestDispatcherImpl {

    private final RequestInfo requestInfo = Weaver.callOriginal();
    
	@Trace
	public void forward(ServletRequest request, ServletResponse response) {
		String request_Uri = UriUtils.concat(requestInfo.servletPath, requestInfo.pathInfo);
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Forward-RequestURI", request_Uri);
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("DispatcherType", "Forward");
		
		Weaver.callOriginal();
	}
	
	@Trace
	public void include(ServletRequest request, ServletResponse response) {
		String request_Uri = UriUtils.concat(requestInfo.servletPath, requestInfo.pathInfo);
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Include-RequestURI", request_Uri);
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("DispatcherType", "Include");
		
		Weaver.callOriginal();
	}
}
