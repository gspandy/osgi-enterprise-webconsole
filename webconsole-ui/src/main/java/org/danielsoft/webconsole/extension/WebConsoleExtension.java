package org.danielsoft.webconsole.extension;

import java.util.List;

public interface WebConsoleExtension {

	/** Returns a short name of this extension. */
	String getName();

	/** Returns a description of this extension. */
	String getDescription();

	List<String> getResources();

	String getComponentClass();

}
