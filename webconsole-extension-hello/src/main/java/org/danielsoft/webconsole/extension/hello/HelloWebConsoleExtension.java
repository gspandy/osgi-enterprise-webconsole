package org.danielsoft.webconsole.extension.hello;

import java.util.Arrays;
import java.util.List;

import org.danielsoft.webconsole.extension.WebConsoleExtension;

public class HelloWebConsoleExtension implements WebConsoleExtension {

	public String getName() {
		return "Hello";
	}

	public String getDescription() {
		return "Very simple extension that displays Hello message!";
	}
	
	// list of resources you want to add to index.html
	public List<String> getResources() {
		return Arrays.asList("hello/HelloWindow.js");
	}

	public String getComponentClass() {
		return "WebConsole.Extension.HelloWindow";
	}

}
