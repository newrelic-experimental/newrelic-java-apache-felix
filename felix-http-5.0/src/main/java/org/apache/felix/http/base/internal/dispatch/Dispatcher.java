package org.apache.felix.http.base.internal.dispatch;

import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.felix.http.base.internal.registry.HandlerRegistry;
import org.apache.felix.http.base.internal.registry.PathResolution;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
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
			String matched = pRes.matchedPattern;
			if(matched != null) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "FelixDispatcher", "Felix","Dispatcher",matched);
			}
			Utils.capturePathResolution(attributes, pRes);
			NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		}
		
		Weaver.callOriginal();
	}
}
