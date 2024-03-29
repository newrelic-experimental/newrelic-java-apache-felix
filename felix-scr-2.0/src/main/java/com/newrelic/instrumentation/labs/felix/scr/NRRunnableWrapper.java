package com.newrelic.instrumentation.labs.felix.scr;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NRRunnableWrapper implements Runnable {

	private static boolean isTransformed = false;
	private Runnable delegate = null;
	private Token token = null;
	
	public NRRunnableWrapper(Runnable r, Token t) {
		delegate = r;
		token = t;
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
			isTransformed = true;
		}
	}

	@Override
	@Trace(async = true)
	public void run() {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		if(delegate != null) {
			delegate.run();
		}
	}

}
