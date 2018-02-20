package com.figsinc.app.netdhania;

import com.netdania.NDChartAPI;
import com.netdania.core.utils.concurrent.threads.CheckThread;
import com.netdania.ui.task.AbstractAsyncTask;

public class ChangeThemeTask extends AbstractAsyncTask<Void, Void> {
	private String themeName;
	private NDChartAPI chartAPI;

	public ChangeThemeTask(NDChartAPI chartAPI, String themeName) {
		this.chartAPI = chartAPI;
		this.themeName = themeName;

	}

	@Override
	protected String getExceptionMessageToLog() {
		return "Exception while changing theme";
	}

	@Override
	protected Void executeInBackground(CheckThread parentThread) throws Exception {
		chartAPI.changeTheme(themeName);
		return null;
	}

}
