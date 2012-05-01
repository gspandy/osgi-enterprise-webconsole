package org.danielsoft.webconsole.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

public class BundleTreeServlet extends HttpServlet {
	
	private BundleContext bundleContext;
	
	public BundleTreeServlet(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json");
		Bundle[] bundles = bundleContext.getBundles();
		PrintWriter out = resp.getWriter();
		out.println("[");
		for (int i = 0; i < bundles.length; i++) {
			out.println(toJson(bundles[i]));
			if (i < bundles.length - 1) {
				out.println(",");
			}
		}
		out.println("]");
	}
	
	String toJson(Bundle bundle) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("'id' : " + bundle.getBundleId() + ",");
		sb.append("'text' : '" + bundle.getBundleId() + " - " + bundle.getSymbolicName() + " - " + bundle.getVersion() + "',");
		sb.append("'leaf' : true");
		sb.append("}");
		return sb.toString();
	}

}
