package org.danielsoft.webconsole.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danielsoft.webconsole.extension.WebConsoleExtension;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

// lists available extension to web console
public class ExtensionServlet extends HttpServlet {

	private BundleContext bundleContext;

	public ExtensionServlet(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.println(toJson(getInstalledExtensions()));
	}
	
	String toJson(List<WebConsoleExtension> extensions) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		
		for (int i = 0; i < extensions.size(); i++) {
			WebConsoleExtension ext = extensions.get(i);
			sb.append(String.format("{'name' : '%s', 'desc' : '%s', 'componentClass' : '%s'}", ext.getName(), ext.getDescription(), ext.getComponentClass()));
			if (i < extensions.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	List<WebConsoleExtension> getInstalledExtensions() {
		List<WebConsoleExtension> extensions = new ArrayList<WebConsoleExtension>();
		try {
			String webConsoleExtensionFilter = String.format("(objectClass=%s)", WebConsoleExtension.class.getName());
			ServiceReference[] refs = bundleContext.getServiceReferences(null, webConsoleExtensionFilter);
			if (refs != null) {
				for (ServiceReference ref : refs) {
					extensions.add((WebConsoleExtension) bundleContext.getService(ref));
				}
			}
		} catch (InvalidSyntaxException e) {
			// IGNORE THIS DUMB OSGi EXCEPTION
		}
		return extensions;
	}

}
