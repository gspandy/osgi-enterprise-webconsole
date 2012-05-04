package org.danielsoft.webconsole.extension.hello;

import org.danielsoft.webconsole.extension.WebConsoleExtension;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.HttpService;

public class Activator implements BundleActivator, ServiceListener {

	private BundleContext bundleContext;
	private ServiceRegistration serviceRegistration;
	private HttpService httpService;

	public void start(BundleContext bundleContext) throws Exception {
		this.bundleContext = bundleContext;
		serviceRegistration = bundleContext.registerService(
				WebConsoleExtension.class.getName(),
				new HelloWebConsoleExtension(), null);
		
		String httpServiceFilter = String.format("(objectClass=%s)", HttpService.class.getName());
		ServiceReference[] httpServiceRefs = bundleContext.getServiceReferences(null, httpServiceFilter);
		
		if (httpServiceRefs != null) {
			registerResources(httpServiceRefs[0]);
		} else {
			bundleContext.addServiceListener(this, httpServiceFilter);
		}
	}
	
	public void serviceChanged(ServiceEvent event) {
		if (event.getType() == ServiceEvent.REGISTERED) {
			registerResources(event.getServiceReference());
		}
	}

	public void stop(BundleContext bundleContext) throws Exception {
		serviceRegistration.unregister();
		bundleContext.removeServiceListener(this);
	}
	
	void registerResources(ServiceReference httpServiceRef) {
		try {
			System.out.println("Registering resources for hello extension..");
			httpService = (HttpService) bundleContext.getService(httpServiceRef);
			httpService.registerResources("/webconsole/hello", "/web", null);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error while registering resources: " + e);
		}
	}

}
