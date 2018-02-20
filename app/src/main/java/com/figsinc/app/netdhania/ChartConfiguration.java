package com.figsinc.app.netdhania;

import com.netdania.common.NDChartInstrument;
import com.netdania.core.chart.ChartSettings;

public class ChartConfiguration implements ChartSettings {
	
	private int timescale;
	private NDChartInstrument instrument;

	public ChartConfiguration(int timescale, NDChartInstrument instrument) {
		super();
		this.timescale = timescale;
		this.instrument = instrument;
	}

	public ChartConfiguration() {
		this(-1, null);
	}

	@Override
	public int getTimescale() {
		return timescale;
	}

	@Override
	public NDChartInstrument getInstrument() {
		return instrument;
	}

	public void setInstrument(NDChartInstrument instrument) {
		this.instrument = instrument;
	}
}
