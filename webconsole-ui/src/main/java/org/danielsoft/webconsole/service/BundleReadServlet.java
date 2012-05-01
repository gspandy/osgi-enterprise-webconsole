package org.danielsoft.webconsole.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

public class BundleReadServlet extends HttpServlet {

	private BundleContext bundleContext;
	
	public BundleReadServlet(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json");
		Long bundleId = Long.valueOf(req.getParameter("bundleId"));
		PrintWriter out = resp.getWriter();
		Bundle bundle = bundleContext.getBundle(bundleId);
		out.println(toJson(bundle));
	}
	
	String toJson(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("bundleId : " + bundle.getBundleId() + ",");
		sb.append("location : '" + bundle.getLocation() + "',");
		sb.append("version : '" + bundle.getVersion() + "',");
		sb.append("lastModified : " + bundle.getLastModified() + ",");
		sb.append("state : '" + state(bundle.getState()) + "',");
		sb.append("symbolicName : '" + bundle.getSymbolicName() + "',");
		sb.append("servicesInUse : '" + bundle.getServicesInUse() + "'");
		sb.append("}");
		return sb.toString();
	}
	
	String state(int state) {
		switch (state) {
		case Bundle.ACTIVE:
			return "ACTIVE";
			default:
				return "UNKNOWN";
		}
	}

}
