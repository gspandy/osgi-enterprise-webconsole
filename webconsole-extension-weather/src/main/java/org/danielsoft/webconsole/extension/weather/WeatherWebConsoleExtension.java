package org.danielsoft.webconsole.extension.weather;

import org.danielsoft.webconsole.extension.WebConsoleExtension;

public class WeatherWebConsoleExtension implements WebConsoleExtension {

	public String getName() {
		return "Weather";
	}
	
	public String getDescription() {
		return "Displays weather forecast for a specific location";
	}

}
