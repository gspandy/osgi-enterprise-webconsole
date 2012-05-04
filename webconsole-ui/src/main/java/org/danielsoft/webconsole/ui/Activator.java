package org.danielsoft.webconsole.ui;

import org.danielsoft.webconsole.service.BundleInstallServlet;
import org.danielsoft.webconsole.service.BundleReadServlet;
import org.danielsoft.webconsole.service.BundleServlet;
import org.danielsoft.webconsole.service.BundleTreeServlet;
import org.danielsoft.webconsole.service.ExtensionServlet;
import org.danielsoft.webconsole.service.IndexServlet;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;

public class Activator implements BundleActivator, ServiceListener {

	private BundleContext bundleContext;
	private HttpService httpService;

	public void start(BundleContext bundleContext) throws Exception {
		this.bundleContext = bundleContext;
		String httpServiceFilter = String.format("(objectClass=%s)", HttpService.class.getName());
		ServiceReference[] httpServiceRefs = bundleContext.getServiceReferences(null, httpServiceFilter);
		
		if (httpServiceRefs != null) {
			registerServletsAndResources(httpServiceRefs[0]);
		} else {
			bundleContext.addServiceListener(this, httpServiceFilter);
		}

	}

	public void stop(BundleContext bundleContext) throws Exception {
		if (httpService != null) {
			httpService.unregister("/webconsole");
		}
		bundleContext.removeServiceListener(this);
	}
	
	public void serviceChanged(ServiceEvent event) {
		if (event.getType() == ServiceEvent.REGISTERED) {
			registerServletsAndResources(event.getServiceReference());
		}
	}
	
	void registerServletsAndResources(ServiceReference httpServiceRef) {
		try {
			httpService = (HttpService) bundleContext.getService(httpServiceRef);
			httpService.registerResources("/webconsole", "/web", null);
			
			IndexServlet indexServlet = new IndexServlet(bundleContext);
			BundleServlet bundleServlet = new BundleServlet(bundleContext);
			BundleTreeServlet bundleTreeServlet = new BundleTreeServlet(bundleContext);
			BundleReadServlet bundleReadServlet = new BundleReadServlet(bundleContext);
			BundleInstallServlet bundleInstallServlet = new BundleInstallServlet(bundleContext);
			ExtensionServlet extensionServlet = new ExtensionServlet(bundleContext);

			httpService.registerServlet("/webconsole/index.html", indexServlet, null, null);
			httpService.registerServlet("/webconsole/service/bundles", bundleServlet, null, null);
			httpService.registerServlet("/webconsole/service/bundles/tree", bundleTreeServlet, null, null);
			httpService.registerServlet("/webconsole/service/bundles/read", bundleReadServlet, null, null);
			httpService.registerServlet("/webconsole/service/bundles/install", bundleInstallServlet, null, null);
			httpService.registerServlet("/webconsole/service/extensions", extensionServlet, null, null);

		} catch (Exception e) {
			System.err.println("Error while registering resources and servlets: " + e);
		}

	}

}
