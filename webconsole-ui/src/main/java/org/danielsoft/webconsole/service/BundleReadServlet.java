package org.danielsoft.webconsole.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;

public class BundleReadServlet extends HttpServlet {
	private BundleContext bundleContext;
	
	public BundleReadServlet(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.println("{bundle : " + req.getParameter("bundleId") + "}");
	}

}
