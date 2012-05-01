package org.danielsoft.webconsole.ui;

import org.danielsoft.webconsole.service.BundleReadServlet;
import org.danielsoft.webconsole.service.BundleServlet;
import org.danielsoft.webconsole.service.BundleTreeServlet;
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
		httpService.registerResources("/webconsole", "/web", null);
		
		BundleServlet bundleServlet = new BundleServlet(bundleContext);
		BundleTreeServlet bundleTreeServlet = new BundleTreeServlet(bundleContext);
		BundleReadServlet bundleReadServlet = new BundleReadServlet(bundleContext);
		httpService.registerServlet("/webconsole/service/bundles", bundleServlet, null, null);
		httpService.registerServlet("/webconsole/service/bundles/tree", bundleTreeServlet, null, null);
		httpService.registerServlet("/webconsole/service/bundles/read", bundleReadServlet, null, null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		if (httpService != null) {
			httpService.unregister("/webconsole");
		}
	}

}
