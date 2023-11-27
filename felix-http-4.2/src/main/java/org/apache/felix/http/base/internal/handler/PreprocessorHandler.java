package org.apache.felix.http.base.internal.handler;

import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.felix.http.base.internal.runtime.PreprocessorInfo;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.felix.http.Utils;

@Weave
public abstract class PreprocessorHandler {
	
	private final PreprocessorInfo info = Weaver.callOriginal();
	private final ServletContext context = Weaver.callOriginal();

	@Trace
	public void handle(ServletRequest req, ServletResponse res, FilterChain chain) {
		HashMap<String, Object> attributes = new HashMap<>();
		Utils.setValue(attributes, "Target", info.getTarget());
		Utils.setValue(attributes, "ContextSelection", info.getContextSelection());
		Utils.captureServletContext(attributes, context);
		
		Weaver.callOriginal();
	}
}
