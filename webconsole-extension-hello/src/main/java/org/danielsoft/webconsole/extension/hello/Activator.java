package org.danielsoft.webconsole.extension.hello;

import org.danielsoft.webconsole.extension.WebConsoleExtension;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private ServiceRegistration serviceRegistration;

	public void start(BundleContext bundleContext) throws Exception {
		serviceRegistration = bundleContext.registerService(
				WebConsoleExtension.class.getName(),
				new HelloWebConsoleExtension(), null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		serviceRegistration.unregister();
	}

}