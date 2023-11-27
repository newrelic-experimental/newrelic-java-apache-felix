package org.apache.felix.http.base.internal.dispatch;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.http.base.internal.registry.HandlerRegistry;
import org.apache.felix.http.base.internal.registry.PathResolution;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.felix.http.Utils;

@Weave
public abstract class Dispatcher {
	
	private final HandlerRegistry handlerRegistry = Weaver.callOriginal();

	@Trace
	public void dispatch(HttpServletRequest req, HttpServletResponse res)  {
		HashMap<String, Object> attributes = new HashMap<>();
		String path = req.getServletPath();
		if (path == null) {
			path = "";
		}

		if (req.getPathInfo() != null) {
			path = path.concat(req.getPathInfo());
		}

		PathResolution pRes = handlerRegistry.resolveServlet(path);
		if(pRes != null) {
			Utils.capturePathResolution(attributes, pRes);
			NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		}
		
		Weaver.callOriginal();
	}
}
