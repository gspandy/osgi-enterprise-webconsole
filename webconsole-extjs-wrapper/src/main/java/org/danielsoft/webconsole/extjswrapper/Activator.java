package org.danielsoft.webconsole.extjswrapper;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

public class Activator implements BundleActivator, ServiceListener {

	private BundleContext bundleContext;
	private HttpService httpService;

	public void start(BundleContext bundleContext) throws Exception {
		this.bundleContext = bundleContext;
		String httpServiceFilter = String.format("(objectClass=%s)", HttpService.class.getName());
		ServiceReference[] httpServiceRefs = bundleContext.getServiceReferences(null, httpServiceFilter);

		if (httpServiceRefs != null) {
			registerResources(httpServiceRefs[0]);
		} else {
			bundleContext.addServiceListener(this, httpServiceFilter);
		}
	}

	public void stop(BundleContext bundleContext) throws Exception {
		if (httpService != null) {
			httpService.unregister("/extjs/4.1.0");
		}
	}
	
	public void serviceChanged(ServiceEvent event) {
		if (event.getType() == ServiceEvent.REGISTERED) {
			registerResources(event.getServiceReference());
		}
	}
	
	void registerResources(ServiceReference httpServiceRef) {
		try {
			httpService = (HttpService) bundleContext.getService(httpServiceRef);
			httpService.registerResources("/extjs/4.1.0", "/web/extjs", null);
		} catch (NamespaceException e) {
			System.err.println("Error while registering resources: " + e);
		}
	}

}
