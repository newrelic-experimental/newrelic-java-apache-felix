package org.eclipse.jetty.server;

import com.newrelic.api.agent.weaver.SkipIfPresent;

/**
 * This class exists in felix.http.jetty 4.2.0+ but not in 4.0.x/4.1.x.
 * If present on the classpath, this module will be skipped,
 * preventing double instrumentation with the felix-http-4.2 module.
 */
@SkipIfPresent
public class AllowedResourceAliasChecker {
}
