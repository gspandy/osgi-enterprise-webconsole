package org.danielsoft.webconsole.extjswrapper;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;

public class Activator implements BundleActivator {

	private HttpService httpService;

	public void start(BundleContext bundleContext) throws Exception {
		ServiceReference ref = bundleContext
				.getServiceReference(HttpService.class.getName());

		httpService = (HttpService) bundleContext.getService(ref);
		httpService.registerResources("/extjs/4.1.0", "/web/extjs", null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		if (httpService != null) {
			httpService.unregister("/extjs/4.1.0");
		}
	}

}
