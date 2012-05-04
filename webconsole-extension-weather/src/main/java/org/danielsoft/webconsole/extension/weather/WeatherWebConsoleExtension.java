package org.danielsoft.webconsole.extension.weather;

import java.util.Arrays;
import java.util.List;

import org.danielsoft.webconsole.extension.WebConsoleExtension;

public class WeatherWebConsoleExtension implements WebConsoleExtension {

	public String getName() {
		return "Weather";
	}

	public String getDescription() {
		return "Displays weather forecast for a specific location";
	}

	public List<String> getResources() {
		return Arrays.asList("weather/WeatherWindow.js");
	}

	public String getComponentClass() {
		return "WebConsole.Extension.WeatherWindow";
	}

}
