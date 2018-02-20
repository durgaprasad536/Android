package com.figsinc.app.netdhania;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ChartPreferences {
	private final SharedPreferences preferences;

	public ChartPreferences(Context context) {
		this.preferences = context.getSharedPreferences("chart_preferences", Context.MODE_PRIVATE);
	}
	
	public void saveChartXML(String symbol, String providerName, int timeScale, String xml) {
		String key = buildKey(symbol, providerName, timeScale);
		Editor editor = this.preferences.edit();
		editor.putString(key, xml);
		
		key = buildKey(symbol, providerName);
		editor.putString(key, Integer.toString(timeScale));
		editor.commit();
	}

	public String loadChartXML(String symbol, String providerName) {
		String key = buildKey(symbol, providerName);
		String timeScaleAsString = this.preferences.getString(key, null);
		if (timeScaleAsString != null) {
			int timeScale = Integer.parseInt(timeScaleAsString);
			key = buildKey(symbol, providerName, timeScale);
			return this.preferences.getString(key, null);
		}
		return null;
	}

	private static String buildKey(String symbol, String providerName, int timeScale) {
		return symbol + "|" + providerName + "|" + timeScale;
	}

	private static String buildKey(String symbol, String providerName) {
		return symbol + "|" + providerName;
	}
}
