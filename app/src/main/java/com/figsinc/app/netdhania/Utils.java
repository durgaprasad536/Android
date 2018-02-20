package com.figsinc.app.netdhania;

import android.os.Bundle;

import com.netdania.common.NDChartField;
import com.netdania.common.NDChartInstrument;
import com.netdania.common.NDChartInstrumentType;

public class Utils {
	
	private Utils() {
	}

	public static NDChartInstrument getIntstrumentFromBundle(Bundle bundle) {
		if (bundle == null || !bundle.containsKey("name")) {
			return null;
		}

		String name = bundle.getString("name");
		String symbol = bundle.getString("symbol");
		String provider = bundle.getString("provider");
		int decimals = bundle.getInt("decimals");
		int pipDecimals = bundle.getInt("pipDecimals");
		NDChartInstrumentType type = (NDChartInstrumentType) bundle.getSerializable("type");
		NDChartField field =(NDChartField) bundle.getSerializable("field");
		return new NDChartInstrument(name, symbol, provider, type, field, decimals, pipDecimals);
	}

	public static void putInstrumentInBundle(Bundle bundle, NDChartInstrument instrument) {
		bundle.putString("name", instrument.getName());
		bundle.putString("symbol", instrument.getSymbol());
		bundle.putString("provider", instrument.getProvider());
		bundle.putInt("decimals", instrument.getDecimals());
		bundle.putInt("pipDecimals", instrument.getPipDecimals());
		bundle.putSerializable("type", instrument.getType());
		bundle.putSerializable("field", instrument.getField());
	}
}
