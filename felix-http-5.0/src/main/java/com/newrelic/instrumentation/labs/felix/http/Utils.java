package com.newrelic.instrumentation.labs.felix.http;

import java.util.Map;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;

import org.apache.felix.http.base.internal.registry.PathResolution;
import org.apache.felix.http.base.internal.runtime.ServletInfo;

public class Utils {

	public static void setValue(Map<String, Object> attributes, String key, Object value) {
		if(attributes != null && key != null && !key.isEmpty() && value != null) {
			attributes.put(key, value);
		}
	}
	
	public static void capturePathResolution(Map<String, Object> attributes, PathResolution pr) {
		if(pr != null) {
			setValue(attributes, "PathResolution-MatchedPattern", pr.matchedPattern);
			setValue(attributes, "PathResolution-MatchValue", pr.matchValue);
			setValue(attributes, "PathResolution-PathInfo", pr.pathInfo);
			setValue(attributes, "PathResolution-RequestURI", pr.requestURI);
			setValue(attributes, "PathResolution-ServletPath", pr.servletPath);
		}
	}
	
	public static void captureServletContext(Map<String,Object> attributes, ServletContext context) {
		if(context != null) {
			setValue(attributes, "ServletInfo", context.getServerInfo());
			setValue(attributes, "ContextPath", context.getContextPath());
			setValue(attributes, "ServletContextName", context.getServletContextName());
		}
	}
	
	public static void captureServletInfo(Map<String,Object> attributes, ServletInfo info) {
		if(info != null) {
			setValue(attributes, "ServletInfo-Name", info.getName());
			setValue(attributes, "ServletInfo-ContextSelection", info.getContextSelection());
			setValue(attributes, "ServletInfo-Prefix", info.getPrefix());
			setValue(attributes, "ServletInfo-Target", info.getTarget());
		}
	}
	
	public static void captureServlet(Map<String,Object> attributes, Servlet servlet, String prefix) {
		if(servlet != null) {
			String key = prefix != null ? prefix + "-ServletName" : "ServletName";
			setValue(attributes, key, servlet.getServletConfig().getServletName());
		}
	}
}
