package org.danielsoft.webconsole.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

public class BundleServlet extends HttpServlet {
	
	private BundleContext bundleContext;

	public BundleServlet(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.println("Hello World!");
		Bundle[] bundles = bundleContext.getBundles();
		for (Bundle bundle : bundles) {
			out.println(toString(bundle));
		}
		
	}
	
	String toString(Bundle bundle) {
		return "" + bundle.getSymbolicName() + " (" + bundle.getVersion() + ") (" + bundle.getLocation() + ")";
	}

}
