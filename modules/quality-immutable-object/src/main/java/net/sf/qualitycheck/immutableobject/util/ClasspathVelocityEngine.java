package net.sf.qualitycheck.immutableobject.util;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public final class ClasspathVelocityEngine {

	public static final String TEMPLATE_CHARSET = "UTF-8";

	public static final VelocityEngine VELOCITY_ENGINE = initializeVelocityEngine();

	private static VelocityEngine initializeVelocityEngine() {
		final VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		engine.init();
		return engine;
	}

}
