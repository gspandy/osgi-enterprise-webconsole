package org.danielsoft.webconsole.extension.hello;

import org.danielsoft.webconsole.extension.WebConsoleExtension;

public class HelloWebConsoleExtension implements WebConsoleExtension {

	public String getName() {
		return "Hello";
	}

	public String getDescription() {
		return "Very simple extension that displays Hello message!";
	}

}
