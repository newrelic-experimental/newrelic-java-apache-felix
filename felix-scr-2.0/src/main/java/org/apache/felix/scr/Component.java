package org.apache.felix.scr;

import com.newrelic.api.agent.weaver.SkipIfPresent;

/**
 * This class exists in felix.scr 1.x but was removed in 2.0.0.
 * If present on the classpath, this module will be skipped,
 * preventing instrumentation from applying to unsupported 1.x versions.
 */
@SkipIfPresent
public class Component {
}
