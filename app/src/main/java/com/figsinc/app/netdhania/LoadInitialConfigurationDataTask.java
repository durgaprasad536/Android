package com.figsinc.app.netdhania;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.figsinc.app.Constants;
import com.figsinc.app.FigsApplication;
import com.netdania.NDChartAPI;
import com.netdania.common.NDChartApiConfig;
import com.netdania.common.NDChartDataFeedService;
import com.netdania.common.NDChartFileHelper;
import com.netdania.core.config.chart.xml.NdChartApiConfigParser;
import com.netdania.core.utils.concurrent.threads.CheckThread;
import com.netdania.ui.task.AbstractAsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public abstract class LoadInitialConfigurationDataTask extends AbstractAsyncTask<Void, NDChartAPI> {
	private final Executor threadPool;
	private final Context context;
	private List<PriceBar> priceBars = new ArrayList<>();

	public LoadInitialConfigurationDataTask(Context context, Executor threadPool,List<PriceBar> priceBars ) {
		this.threadPool = threadPool;
		this.context = context;
		this.priceBars = priceBars;
	}
	
	@Override
	protected final String getExceptionMessageToLog() {
		return "Exception while loading the initial configuration data.";
	}
	
	@Override
	protected NDChartAPI executeInBackground(CheckThread thread) throws Exception {

		NDChartApiConfig apiConfig = parseApiConfig();

	//	NDChartDataFeedService chartDataFeedService = new NDChartDataFeedService(context, threadPool, apiConfig);

		DemoDataFeedService chartDataFeedService = new DemoDataFeedService(context,priceBars);
		NDChartAPI chartAPI = NDChartAPI.getInstance();
		chartAPI.init(context, this.threadPool, chartDataFeedService, apiConfig);
		return chartAPI;
	}

	private NDChartApiConfig parseApiConfig() throws IOException, XmlPullParserException {
		InputStream configInputStream = NDChartFileHelper.instance(context).openNDChartApiConfigStream();
		try {
			NdChartApiConfigParser apiConfigParser = new NdChartApiConfigParser();
			return apiConfigParser.parse(configInputStream);
		} finally {
			configInputStream.close();
		}
	}





}
