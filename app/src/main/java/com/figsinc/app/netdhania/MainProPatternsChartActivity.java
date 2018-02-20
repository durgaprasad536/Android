package com.figsinc.app.netdhania;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Filter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
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
import com.figsinc.app.R;
import com.netdania.NDChartAPI;
import com.netdania.common.NDChartAnalysisObject;
import com.netdania.common.NDChartAnalysisTimeFrame;
import com.netdania.common.NDChartController;
import com.netdania.common.NDChartFibonacciObject;
import com.netdania.common.NDChartField;
import com.netdania.common.NDChartInstrument;
import com.netdania.common.NDChartInstrumentChangeTypeListener;
import com.netdania.common.NDChartInstrumentTimeScaleChangedListener;
import com.netdania.common.NDChartInstrumentType;
import com.netdania.common.NDChartListener;
import com.netdania.common.NDChartObject;
import com.netdania.common.NDChartOverlayObject;
import com.netdania.common.NDChartPatternsListener;
import com.netdania.common.NDChartSettings;
import com.netdania.common.NDChartStudyObject;
import com.netdania.common.NDChartTitleVisibility;
import com.netdania.common.NDChartToolbarLocation;
import com.netdania.common.NDChartTrendLineObject;
import com.netdania.common.NDChartType;
import com.netdania.common.NDChartView;
import com.netdania.ui.views.ListAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import static com.figsinc.app.analyze.trendingstocks.StockInfoActivity.KEY_EXTRA_CF_RIC;

public class MainProPatternsChartActivity extends BaseActivity implements ProPatternsChartCommands {
    private ChartPreferences chartPreferences;

    private ListView chartCommandsListView;
    private NDChartController chartViewCommands;
    private NDChartView chartView;
    boolean showLeftMenu;
    private String cfRic = "";
    private NDChartInstrument prefferedInstrument = null;
    private ChangeThemeTask changeThemeTask;
    private NDChartObject selectedChartObject;
    private List<NDChartObject> addedFromUIChartObject = new ArrayList<>();
    private List<PriceBar> priceBars = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.chart_commands);

        try {
            cfRic = getIntent().getStringExtra(KEY_EXTRA_CF_RIC);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(MainProPatternsChartActivity.this);
            String url = Constants.stocksCandleStickChart + cfRic;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            PriceBarParse pj = new PriceBarParse(response);
                            priceBars = pj.parseJSON();
                            Executor threadPool = getBaseApplication().getThreadPool();
                            LoadInitialConfigurationDataTask task = new LoadInitialConfigurationDataTask(MainProPatternsChartActivity.this, threadPool, priceBars) {
                                @Override
                                protected void onPostExecute(Exception exception, NDChartAPI frameworkConfiguration) {
                                    onFinishLoadingConfigurationData(exception, frameworkConfiguration);
                                }
                            };
                            task.executeAsync(threadPool); // start the task

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainProPatternsChartActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", FigsApplication.getAuthToken());
                    return headers;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };

            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onFinishLoadingConfigurationData(Exception exception, NDChartAPI frameworkConfiguration) {
        if (exception == null) {
            FigsApplication application = getBaseApplication();
            application.setFrameworkConfiguration(frameworkConfiguration);

            chartCommandsListView = (ListView) findViewById(R.id.chart_command_list);
            showLeftMenu = getIntent().getBooleanExtra("show_left_menu", false);
			/*if (showLeftMenu) {
				initChartCommandList();
			} else {
				chartCommandsListView.setVisibility(View.GONE);
			}*/

            initContent();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            String chartAsXML = this.chartViewCommands.writeChartAsXML();
            //String instrumentSymbol = "EURUSD";
            String instrumentSymbol = "";
            String providerName = "netdania_fxa";
            int timeScale = 60;
            this.chartPreferences.saveChartXML(instrumentSymbol, providerName, timeScale, chartAsXML);
            //System.out.println("chartAsXML="+chartAsXML);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initChartCommandList() {
        if (chartCommandsListView.getAdapter() == null) {
            ListAdapter<String> listAdapter = new ListAdapter<String>(this) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView tv;
                    if (convertView == null) {
                        convertView = View.inflate(getContext(), android.R.layout.simple_list_item_1, null);

                        tv = (TextView) convertView.findViewById(android.R.id.text1);
                        tv.setSingleLine();
                        tv.setTextSize(13);
                        tv.setEllipsize(TruncateAt.END);
                        convertView.setTag(tv);
                    } else {
                        tv = (TextView) convertView.getTag();
                    }
                    tv.setText(getItem(position));
                    return tv;
                }

                @Override
                protected Filter getFilter() {
                    return null;
                }
            };

            chartCommandsListView.setAdapter(listAdapter);
            setAdapterItems();

            chartCommandsListView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    handleOnMenuItemClick(position);
                }
            });
        }
    }

    private void initContent() {
        NDChartAPI frameworkConfiguration = NDChartAPI.getInstance();

        this.chartPreferences = new ChartPreferences(this);

        prefferedInstrument = Utils.getIntstrumentFromBundle(getIntent().getExtras());

        if (prefferedInstrument == null) {
            //String instrumentSymbol = "EURUSD";
            String instrumentSymbol = "";
            String providerName = "netdania_fxa";
            String instrumentName = "EUR/USD";
            prefferedInstrument = new NDChartInstrument(instrumentName, instrumentSymbol, providerName, NDChartInstrumentType.FOREX, NDChartField.BID, 5, 5);
        }

        NDChartSettings chartSettings = null;
        String xml = this.chartPreferences.loadChartXML(prefferedInstrument.getSymbol(), prefferedInstrument.getProvider());
        if (xml == null) {
            chartSettings = new NDChartSettings(NDChartInstrumentType.FOREX);
        } else {
            chartSettings = new NDChartSettings(xml);
        }
        NDChartListener chartListener = new NDChartPatternsListener() {
            @Override
            public void onLastAnalysisUpdate(NDChartAnalysisObject newAnalysis) {
                Toast.makeText(MainProPatternsChartActivity.this, "onLastAnalysisUpdate " + newAnalysis.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnalysesReceivedForTimeframe(NDChartAnalysisTimeFrame timeframe) {
                Toast.makeText(MainProPatternsChartActivity.this, "onAnalysesReceivedForTimeframe " + timeframe, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccessfullyLoadingChart(NDChartView chartView, NDChartController chartViewCommands) {
                Toast.makeText(getContext(), "Chart has been successfully loaded", Toast.LENGTH_LONG).show();
                MainProPatternsChartActivity.this.onSuccessfullyLoadingChart(chartViewCommands);
            }

            @Override
            public void onFailedLoadingChart(NDChartView chartView) {
            }

            @Override
            public boolean canChangeChartType(NDChartView chartView, NDChartType fromChartType, NDChartType toChartType) {
                return true;
            }

            @Override
            public boolean canChangeTimeScale(NDChartView chartView, long fromTimeScale, long toTimeScale) {
                return true;
            }

            @Override
            public void onFinishEditingObject(NDChartView chartView, NDChartObject chartObject) {
                Toast.makeText(getContext(), "Done editing object: " + chartObject.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSelectingObject(NDChartView chartView, NDChartObject chartObject) {
                Toast.makeText(getContext(), "Selected object: " + chartObject.getName(), Toast.LENGTH_SHORT).show();
                MainProPatternsChartActivity.this.selectedChartObject = chartObject;
            }

            @Override
            public boolean canSelectObject(NDChartView chartView, NDChartObject chartObject) {
                if (chartObject.getName() != null && chartObject.getName().equals("BB20;2")) {
                    Toast.makeText(getContext(), chartObject.getName() + " cannot be selected", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }

            @Override
            public void onDoubleTap(NDChartView chartView) {
                Toast.makeText(getContext(), "Chart was double tapped", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFinishAddingObjectFromUI(NDChartObject chartObject) {
                MainProPatternsChartActivity.this.addedFromUIChartObject.add(chartObject);
            }

            @Override
            public void chartBackButtonClicked() {
                Toast.makeText(getContext(), "Chart back button clicked!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMainInstrumentDataRebuild() {
            }
        };

        float density = getResources().getDisplayMetrics().density;
        Rect margins = new Rect((int) (density * 2), (int) (density * 2), (int) (density * 2), (int) (density * 2));
        Rect padding = new Rect((int) (density * 2), (int) (density * 2), (int) (density * 2), (int) (density * 2));

        chartView = frameworkConfiguration.buildNewChartView(this, prefferedInstrument, chartSettings, chartListener);
        chartView.showToolbar();
        chartView.setChartPadding(padding);
        chartView.setChartMargins(margins);
//        chartView.setChartTitleVisibility(NDChartTitleVisibility.MAIN_CHART_TITLE|NDChartTitleVisibility.SECONDARY_CHART_TITLE, true);
        chartView.setHorizontalScrollButtonVisibility(true);
        chartView.setVerticalFitButtonVisibility(true);
        FrameLayout chartHolder = (FrameLayout) findViewById(R.id.chartFrame);
        chartHolder.addView(chartView);
    }

    protected Context getContext() {
        return this;
    }

    private void onSuccessfullyLoadingChart(NDChartController chartViewCommands) {
        this.chartViewCommands = chartViewCommands;
    }

    private void setAdapterItems() {
        List<String> adapterItems = new LinkedList<String>();
        adapterItems.add(ADD_STUDY_UI);
        adapterItems.add(ADD_STUDY_OBJECT);
        adapterItems.add(EDIT_STUDY_OBJECT_UI);
        adapterItems.add(FINISH_EDITING_STUDY);
        adapterItems.add(CHANGE_TIMESCALE);
        adapterItems.add(CHANGE_CHART_TYPE);
        adapterItems.add(SHOW_CROSSHAIR);
        adapterItems.add(HIDE_CROSSHAIR);
        adapterItems.add(START_ADDING_LINE_UI);
        adapterItems.add(START_EDITING_LINE_UI);
        adapterItems.add(START_ADDING_FIBONACCI_LINE_UI);
        adapterItems.add(START_EDITING_FIBONACCI_LINE_UI);
        adapterItems.add(FINISH_EDITING_LINE);
        adapterItems.add(ADD_OVERLAY_USDJPY);
        adapterItems.add(REMOVE_OVERLAY_USDJPY);
        adapterItems.add(EDIT_OVERLAY_USDJPY);
        adapterItems.add(FINISH_EDITING_OVERLAY);
        adapterItems.add(DELETE_OBJECT);
        adapterItems.add(DELETE_ALL_OBJECTS);
        adapterItems.add(EXPORT_TO_IMAGE);
        adapterItems.add(SHOW_VOLUME);
        adapterItems.add(HIDE_VOLUME);
        adapterItems.add(SHOW_MAIN_CHART_TITLE);
        adapterItems.add(HIDE_MAIN_CHART_TITLE);
        adapterItems.add(" ");
        adapterItems.add(SHOW_DEFAULT_TOOLBAR);
        adapterItems.add(HIDE_DEFAULT_TOOLBAR);
        adapterItems.add(PLACE_TOOLBAR_UP);
        adapterItems.add(PLACE_TOOLBAR_DOWN);
        adapterItems.add(" ");
        adapterItems.add(BLACK_THEME);
        adapterItems.add(BLUE_THEME);
        adapterItems.add(WHITE_THEME);
        adapterItems.add(GRAY_THEME);
        adapterItems.add(" ");
        adapterItems.add(SELECT_STUDY);
        adapterItems.add(SELECT_LINE);
        adapterItems.add(SELECT_FIBONACCI);
        adapterItems.add(SELECT_OVERLAY);
        adapterItems.add(GET_CHART_TYPE);
        adapterItems.add(GET_TIMESCALE);

        adapterItems.add(" ");
        adapterItems.add(START_ADDING_ANALYSIS_UI);
        adapterItems.add(SELECT_ANALYSIS);
        adapterItems.add(SHOW_ANALYSIS_STORY);
        adapterItems.add(REMOVE_ANALYSIS);
        adapterItems.add(ADD_LAST_ANALYSIS);
        adapterItems.add(REMOVE_LAST_ANALYSIS);
        adapterItems.add(GET_DISPLAYED_ANALYSES);
        adapterItems.add(SELECT_ANALYSES_LANGUAGE);
        adapterItems.add(FIT_CHART_Y);
        adapterItems.add(GO_LAST_INDEX);

        ListAdapter<String> adapter = (ListAdapter<String>) chartCommandsListView.getAdapter();
        adapter.setItems(adapterItems);
        adapter.notifyDataSetChanged();

    }

    protected void handleOnMenuItemClick(int position) {
        if (chartViewCommands == null) {
            Toast.makeText(this, "Chart not ready to receive commands yet!", Toast.LENGTH_LONG).show();
            return;
        }
        String item = (String) chartCommandsListView.getAdapter().getItem(position);
        if (ADD_STUDY_UI.equals(item)) {
            chartViewCommands.showDialogToAddStudy();
        } else if (ADD_STUDY_OBJECT.equals(item)) {
            HashMap<String, NDChartStudyObject> allStudies = NDChartAPI.getInstance().getAllStudies();
            NDChartStudyObject ndChartStudyObject = allStudies.get("EWO");
            chartViewCommands.addStudyObject(ndChartStudyObject);
        } else if (EDIT_STUDY_OBJECT_UI.equals(item)) {
            List<NDChartStudyObject> studiesOnChart = chartViewCommands.getStudiesOnChart();
            if (studiesOnChart != null) {
                NDChartStudyObject ndChartStudyObject = studiesOnChart.get(0);
                chartViewCommands.editStudyUI(ndChartStudyObject);
            }
        } else if (FINISH_EDITING_STUDY.equals(item)) {
            chartViewCommands.finishEditingStudy();
        } else if (START_ADDING_LINE_UI.equals(item)) {
            chartViewCommands.startAddingLineUI();

        } else if (START_EDITING_LINE_UI.equals(item)) {
            List<NDChartTrendLineObject> trendlinesOnChart = chartViewCommands.getTrendlinesOnChart();
            if (trendlinesOnChart != null && !trendlinesOnChart.isEmpty()) {
                chartViewCommands.startEditingLineUI(trendlinesOnChart.get(0));
            }
        } else if (START_ADDING_FIBONACCI_LINE_UI.equals(item)) {
            chartViewCommands.startAddingFibonacciLineUI();

        } else if (START_EDITING_FIBONACCI_LINE_UI.equals(item)) {
            List<NDChartFibonacciObject> fibonacciLinesOnChart = chartViewCommands.getFibonacciLinesOnChart();
            if (fibonacciLinesOnChart != null && !fibonacciLinesOnChart.isEmpty()) {
                chartViewCommands.startEditingFibonacciUI(fibonacciLinesOnChart.get(0));
            }
        } else if (FINISH_EDITING_LINE.equals(item)) {
            chartViewCommands.finishEditingLine();
        } else if (EXPORT_TO_IMAGE.equals(item)) {
            Bitmap bitmap = chartViewCommands.exportToImageWithSize(600, 400);
            handleExportToImageCommand(bitmap);
        } else if (DELETE_OBJECT.equals(item)) {
            List<NDChartStudyObject> studiesOnChart = chartViewCommands.getStudiesOnChart();
            if (studiesOnChart != null) {
                NDChartObject objectToDelete = studiesOnChart.get(0);
                chartViewCommands.deleteChartObject(objectToDelete);
            }
            List<NDChartTrendLineObject> trendlinesOnChart = chartViewCommands.getTrendlinesOnChart();
            if (trendlinesOnChart != null && !trendlinesOnChart.isEmpty() && trendlinesOnChart.get(0) != null) {
                NDChartObject objectToDelete = trendlinesOnChart.get(0);
                chartViewCommands.deleteChartObject(objectToDelete);
            }
        } else if (DELETE_ALL_OBJECTS.equals(item)) {
            chartViewCommands.deleteAllChartItems();
        } else if (CHANGE_TIMESCALE.equals(item)) {
            NDChartInstrumentTimeScaleChangedListener changeInstrumentChartTimeScaleCallback = new NDChartInstrumentTimeScaleChangedListener() {
                @Override
                public void beforeChangeTimeScale(NDChartView chartView, int fromTimeScale, int toTimeScale) {
                }

                @Override
                public void afterChangeTimeScale(NDChartView chartView, int fromTimeScale, int toTimeScale, Exception exception) {
                }
            };
            chartViewCommands.changeInstrumentTimeScaleAsync(1, changeInstrumentChartTimeScaleCallback);
        } else if (CHANGE_CHART_TYPE.equals(item)) {
            NDChartInstrumentChangeTypeListener changeInstrumentChartTypeCallback = new NDChartInstrumentChangeTypeListener() {
                @Override
                public void beforeChangeChartType(NDChartView chartView, NDChartType fromChartType, NDChartType toChartType) {
                }

                @Override
                public void afterChangeChartType(NDChartView chartView, NDChartType fromChartType, NDChartType toChartType, Exception exception) {
                }
            };
            chartViewCommands.changeInstrumentChartTypeAsync(NDChartType.LINE_CHART, changeInstrumentChartTypeCallback);
        } else if (SHOW_VOLUME.equals(item)) {
            chartViewCommands.showVolume();
        } else if (HIDE_VOLUME.equals(item)) {
            chartViewCommands.hideVolume();
        } else if (SHOW_DEFAULT_TOOLBAR.equals(item)) {
            chartView.showToolbar();
        } else if (HIDE_DEFAULT_TOOLBAR.equals(item)) {
            chartView.hideToolbar();
        } else if (SHOW_MAIN_CHART_TITLE.equals(item)) {
            chartView.setChartTitleVisibility(NDChartTitleVisibility.MAIN_CHART_TITLE, true);
        } else if (HIDE_MAIN_CHART_TITLE.equals(item)) {
            chartView.setChartTitleVisibility(NDChartTitleVisibility.MAIN_CHART_TITLE, false);
        } else if (ADD_OVERLAY_USDJPY.equals(item)) {
            String instrumentSymbol = "USDJPY";
            String providerName = "netdania_fxa";
            String instrumentName = "USD/JPY";
            chartViewCommands.addOverlayInstrumentAsync(new NDChartInstrument(instrumentName, instrumentSymbol, providerName, NDChartInstrumentType.FOREX, NDChartField.BID, 5, 5));
        } else if (REMOVE_OVERLAY_USDJPY.equals(item)) {
            String instrumentSymbol = "USDJPY";
            String providerName = "netdania_fxa";
            String instrumentName = "USD/JPY";
            chartViewCommands.removeOverlayInstrumentAsync(new NDChartInstrument(instrumentName, instrumentSymbol, providerName, NDChartInstrumentType.FOREX, NDChartField.BID, 5, 5));
        } else if (EDIT_OVERLAY_USDJPY.equals(item)) {
            String instrumentSymbol = "USDJPY";
            String providerName = "netdania_fxa";
            String instrumentName = "USD/JPY";
            chartViewCommands.startEditingOverlayInstrument(new NDChartInstrument(instrumentName, instrumentSymbol, providerName, NDChartInstrumentType.FOREX, NDChartField.BID, 5, 5));
        } else if (FINISH_EDITING_OVERLAY.equals(item)) {
            chartViewCommands.finishEditingOverlayObject();
        } else if (SHOW_CROSSHAIR.equals(item)) {
            chartViewCommands.showCrossHair();
        } else if (HIDE_CROSSHAIR.equals(item)) {
            chartViewCommands.hideCrossHair();
        } else if (BLACK_THEME.equals(item)) {
            changeTheme("Black");
        } else if (WHITE_THEME.equals(item)) {
            changeTheme("White");
        } else if (BLUE_THEME.equals(item)) {
            changeTheme("Blue");
        } else if (GRAY_THEME.equals(item)) {
            changeTheme("Gray");
        } else if (SELECT_STUDY.equals(item)) {
            List<NDChartStudyObject> studiesOnChart = chartViewCommands.getStudiesOnChart();
            if (studiesOnChart == null || studiesOnChart.size() == 0) {
                Toast.makeText(getContext(), "There is no study on chart to select.", Toast.LENGTH_LONG).show();
            } else {
                NDChartStudyObject ndChartStudyObject = studiesOnChart.get(0);
                chartViewCommands.selectStudy(ndChartStudyObject);
            }
        } else if (SELECT_LINE.equals(item)) {
            List<NDChartTrendLineObject> trendlinesOnChart = chartViewCommands.getTrendlinesOnChart();
            if (trendlinesOnChart == null || trendlinesOnChart.size() == 0) {
                Toast.makeText(getContext(), "There is no line on chart to select.", Toast.LENGTH_LONG).show();
            } else {
                chartViewCommands.selectLine(trendlinesOnChart.get(0));
            }
        } else if (SELECT_FIBONACCI.equals(item)) {
            List<NDChartFibonacciObject> fibonacciLinesOnChart = chartViewCommands.getFibonacciLinesOnChart();
            if (fibonacciLinesOnChart == null || fibonacciLinesOnChart.size() == 0) {
                Toast.makeText(getContext(), "There is no fibonacci line on chart to select.", Toast.LENGTH_LONG).show();
            } else {
                chartViewCommands.selectFibonacciLine(fibonacciLinesOnChart.get(0));
            }
        } else if (SELECT_OVERLAY.equals(item)) {
            List<NDChartOverlayObject> overlayInstrumentsOnChart = chartViewCommands.getOverlayInstrumentsOnChart();
            if (overlayInstrumentsOnChart == null || overlayInstrumentsOnChart.size() == 0) {
                Toast.makeText(getContext(), "There is no overlay instrument on chart to select.", Toast.LENGTH_LONG).show();
            } else {
                chartViewCommands.selectOverlayObject(overlayInstrumentsOnChart.get(0));
            }
        } else if (GET_TIMESCALE.equals(item)) {
            Toast.makeText(getContext(), "Timescale: " + chartViewCommands.getTimeScale() + " seconds", Toast.LENGTH_LONG).show();
        } else if (GET_CHART_TYPE.equals(item)) {
            String chartType = "";
            switch (chartViewCommands.getChartType()) {
                case LINE_CHART:
                    chartType = "Line";
                    break;
                case OHLC:
                    chartType = "OHLC";
                case CANDLE_STICK:
                    chartType = "Normal candlestick";
                case LINE_DOT:
                    chartType = "Line dot";
                case HLC:
                    chartType = "HLC";
                case HEIKIN_ASHI:
                    chartType = "Heikin Ashi";

                default:
                    break;
            }
            Toast.makeText(getContext(), "Chart type: " + chartType, Toast.LENGTH_LONG).show();
        } else if (START_ADDING_ANALYSIS_UI.equals(item)) {

            chartViewCommands.startAddingAnalysisUI();

        } else if (SELECT_ANALYSIS.equals(item)) {

            List<NDChartAnalysisObject> displayedAnalyses = chartViewCommands.getDisplayedAnalyses();
            if (!displayedAnalyses.isEmpty()) {
                chartViewCommands.selectAnalysis(displayedAnalyses.get(0));
            }

        } else if (SHOW_ANALYSIS_STORY.equals(item)) {

            if (selectedChartObject instanceof NDChartAnalysisObject) {
                chartViewCommands.showAnalysisStoryUI((NDChartAnalysisObject) selectedChartObject);
            } else {
                for (NDChartObject o : addedFromUIChartObject) {
                    if (o instanceof NDChartAnalysisObject) {
                        chartViewCommands.showAnalysisStoryUI((NDChartAnalysisObject) o);
                        break;
                    }
                }
            }

        } else if (REMOVE_ANALYSIS.equals(item)) {

            List<NDChartAnalysisObject> displayedAnalyses = chartViewCommands.getDisplayedAnalyses();
            if (!displayedAnalyses.isEmpty()) {
                chartViewCommands.deleteChartObject(displayedAnalyses.get(0));
            }

        } else if (PLACE_TOOLBAR_DOWN.equals(item)) {

            NDChartToolbarLocation ndChartToolbarLocation = new NDChartToolbarLocation();
            ndChartToolbarLocation.setToolbarLocation(NDChartToolbarLocation.SHOW_TOOLBAR_BELOW);
            this.chartView.setToolbarPosition(ndChartToolbarLocation);

        } else if (PLACE_TOOLBAR_UP.equals(item)) {

            NDChartToolbarLocation ndChartToolbarLocation = new NDChartToolbarLocation();
            ndChartToolbarLocation.setToolbarLocation(NDChartToolbarLocation.SHOW_TOOLBAR_ABOVE);
            this.chartView.setToolbarPosition(ndChartToolbarLocation);

        } else if (ADD_LAST_ANALYSIS.equals(item)) {

            chartViewCommands.addLastAnalysis(NDChartAnalysisTimeFrame.INTRADAY);

        } else if (REMOVE_LAST_ANALYSIS.equals(item)) {

            chartViewCommands.removeLastAnalysis(NDChartAnalysisTimeFrame.INTRADAY);

        } else if (GET_DISPLAYED_ANALYSES.equals(item)) {

            StringBuilder sb = new StringBuilder();
            List<NDChartAnalysisObject> displayedAnalyses = chartViewCommands.getDisplayedAnalyses();
            for (NDChartAnalysisObject displayedAnalysis : displayedAnalyses) {
                sb.append(displayedAnalysis.getName());
                sb.append("\n");
            }
            Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();

        } else if (SELECT_ANALYSES_LANGUAGE.equals(item)) {

            chartViewCommands.setAnalysesLanguage("FR");

        } else if (FIT_CHART_Y.equals(item)) {

            chartViewCommands.fitChartVertically();

        } else if (GO_LAST_INDEX.equals(item)) {

            chartViewCommands.goToLastBar();

        }

    }

    private void changeTheme(String themeName) {
        if (changeThemeTask != null) {
            changeThemeTask.stopRunning();
        }
        changeThemeTask = new ChangeThemeTask(NDChartAPI.getInstance(), themeName);
        changeThemeTask.executeAsync(getBaseApplication().getThreadPool());
    }

    private void handleExportToImageCommand(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Intent intent = new Intent(this, ChartImageActivity.class);
        intent.putExtra("image", byteArray);
        startActivity(intent);
    }
}
