package com.figsinc.app.netdhania;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
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
import com.figsinc.app.analyze.BarChart.BarChartData;
import com.figsinc.app.analyze.trendingfunds.FundsInfoActivity;
import com.figsinc.app.analyze.trendingfunds.FundsInfoChartParse;
import com.github.mikephil.charting.data.LineData;
import com.netdania.common.NDChartAvailableInstrumentsCallback;
import com.netdania.common.NDChartDataFeedProtocol;
import com.netdania.common.NDChartField;
import com.netdania.common.NDChartInstrument;
import com.netdania.common.NDChartInstrumentType;
import com.netdania.common.NDChartMonitorRequestCallback;
import com.netdania.common.NDChartMonitorUpdateResponse;
import com.netdania.common.NDChartSnapshotRequestCallback;
import com.netdania.common.NDChartSnapshotResponse;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alexandra Pistol on 21.02.2017.
 */

public class DemoDataFeedService implements NDChartDataFeedProtocol {

    ScheduledExecutorService execService;

    List<PriceBar> priceBars;

    public DemoDataFeedService(final Context context,List<PriceBar> priceBars) {

       /* PriceBar priceBar = new PriceBar();
        priceBar.setOpen(113.105);
        priceBar.setHigh(113.174);
        priceBar.setLow(113.009);
        priceBar.setClose(113.078);
        priceBar.setVolume(53.388);
        PriceBar oldBar = priceBar;
        for (int i = 0; i < 1000; i++) {
            oldBar = generateRandomBar(oldBar);
            priceBars.add(oldBar);
        }*/
        this.priceBars = priceBars;

    }

    @Override
    public int snapshotChart(String symbol, final String provider, int timeScale, final int requestedPoints, final int startDateInSeconds, final int endDateInSeconds, final NDChartField field, final NDChartSnapshotRequestCallback snapshotChartRequestCallback) {


        final NDChartSnapshotResponse snapshotResponse = new NDChartSnapshotResponse() {
            @Override
            public double[] getTimeStamps() {
                return generateTimestamps(requestedPoints, endDateInSeconds);
            }

            @Override
            public double[] getOpens() {
                double[] opens = new double[requestedPoints];
                try {

                    for (int i = 0; i < requestedPoints; i++) {
                     // System.out.println(" 11111111111111111 ");
                        opens[i] = priceBars.get(i).getOpen();
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                return opens;
            }

            @Override
            public double[] getCloses() {
                double[] closes = new double[requestedPoints];
                for (int i = 0; i < requestedPoints; i++) {
                    if(priceBars.size()>0)
                    closes[i] = priceBars.get(i).getClose();
                }
                return closes;
            }

            @Override
            public double[] getHighs() {
                double[] highs = new double[requestedPoints];
                for (int i = 0; i < requestedPoints; i++) {
                    if(priceBars.size()>0)
                    highs[i] = priceBars.get(i).getHigh();
                }
                return highs;
            }

            @Override
            public double[] getLows() {
                double[] lows = new double[requestedPoints];
                for (int i = 0; i < requestedPoints; i++) {
                    if(priceBars.size()>0)
                    lows[i] = priceBars.get(i).getLow();
                }
                return lows;
            }

            @Override
            public double[] getVolumes() {
                double[] volumes = new double[requestedPoints];
                for (int i = 0; i < requestedPoints; i++) {
                    if(priceBars.size()>0)
                    volumes[i] = priceBars.get(i).getVolume();
                }
                return volumes;
            }
        };

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    snapshotChartRequestCallback.snapshotChartResponse(2, snapshotResponse);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

            }
        }, 1000);

        return 2;
    }

    @Override
    public int startMonitorChart(String symbol, String provider, int timeScale, NDChartField field, final NDChartMonitorRequestCallback monitorChartRequestCallback) {
        final NDChartMonitorUpdateResponse monitorUpdateResponse = new NDChartMonitorUpdateResponse() {
            @Override
            public double getTimeStamp() {
                return System.currentTimeMillis() / 1000;
            }

            @Override
            public double getOpenInterest() {
                double close = priceBars.get(priceBars.size() - 1).getClose();
                return getRandomPrice(close);
            }

            @Override
            public double getClose() {
                double close = priceBars.get(priceBars.size() - 1).getClose();
                return getRandomPrice(close);
            }

            @Override
            public double getVolume() {
                double volume = priceBars.get(priceBars.size() - 1).getVolume();
                return getRandomPrice(volume);
            }
        };

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                execService = Executors.newScheduledThreadPool(5);
                execService.scheduleWithFixedDelay(new Runnable() {
                    @Override
                    public void run() {
                        monitorChartRequestCallback.monitorChartResponse(3, monitorUpdateResponse);
                    }
                }, 0, 5 * 1000, TimeUnit.MILLISECONDS);
            }
        }, 4000);
        return 3;
    }

    @Override
    public void stopMonitorChart(int requestId) {
        if (execService != null) {
            execService.shutdown();
        }
    }

    @Override
    public long[] getAvailableTimescales() {
        long[] timeScales = {0, 1, 2, 3, 4, 5, 10, 15, 30, 60, 120, 180, 240, 300, 600, 900, 1800, 3600/*, 7200, 10800, 14400, 21600, 28800, 43200, 86400, 604800, 2592000*/};
        return timeScales;
    }

    @Override
    public int requestInstrumentList(NDChartAvailableInstrumentsCallback callback) throws JSONException {

        List<NDChartInstrument> availableInstruments = new ArrayList<NDChartInstrument>();
        NDChartInstrument chartInstrument = new NDChartInstrument("EUR/USD", "EURUSD", "test", NDChartInstrumentType.FOREX, NDChartField.BID, 5, 5);
        availableInstruments.add(chartInstrument);

        callback.availableInstrumentsResponse(1, availableInstruments);

        return 1;
    }

    private double getRandomPrice(double price) {
        double rangeMin = (price * -.02);
        double rangeMax = (price * .02);
        Random r = new Random();

        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        double value = price + randomValue;
        return value;
    }

    private double[] generateTimestamps(int requestedPoints, int endDateInSeconds) {
        Calendar cal = Calendar.getInstance();
        if (endDateInSeconds == 0) {
            cal.add(Calendar.DATE, -requestedPoints);
        } else {
            cal.setTimeInMillis((long) (endDateInSeconds * 1000L));
            cal.add(Calendar.DATE, -requestedPoints);
        }

        Date startTime = cal.getTime();
        Date endTime = new Date();

        double[] timestamps = new double[requestedPoints];

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(startTime);
        int i = 0;
        while (calendar.getTime().before(endTime)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            if (i > requestedPoints - 1) {
                return timestamps;
            }
            timestamps[i] = calendar.getTimeInMillis() / 1000;
            i++;
        }

        return timestamps;
    }


}
