package com.figsinc.app.analyze.sentiments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.figsinc.app.analyze.BarChart.BarChartData;
import com.figsinc.app.analyze.model.Sentiments;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.figsinc.app.analyze.sentiments.SentimentsDetailsActivity.KEY_FLAG_POSITION;
import static com.figsinc.app.analyze.sentiments.SentimentsDetailsActivity.KEY_SENTIMENTS_DETAILS;


public class SentimentsFragment extends Fragment {

    private SentimentsRecyclerViewAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;

    @BindView(R.id.marker_nasdaq)
    ImageView marker_nasdaq;
    @BindView(R.id.marker_nyse)
    ImageView marker_nyse;
    @BindView(R.id.marker_japan)
    ImageView marker_japan;
    @BindView(R.id.marker_taiwan)
    ImageView marker_taiwan;
    @BindView(R.id.marker_HongKong)
    ImageView marker_HongKong;
    @BindView(R.id.marker_singapore)
    ImageView marker_singapore;

    public SentimentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analyze_sentiments, container, false);
        ButterKnife.bind(this, view);
        // TODO Use fields...
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.listSentiments);
        network();
    }

    private void listBind(ArrayList<Sentiments> sentimentsArrayList,final ArrayList<ArrayList<BarChartData>> listOLists){
        mAdapter = new SentimentsRecyclerViewAdapter(sentimentsArrayList,getActivity(),listOLists);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void network() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String url = Constants.sentimentsIndex;

           // System.out.println(" **********  " + FigsApplication.getAuthToken());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                            //System.out.println(" **********  " + response);
                            showJSON(response);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // Toast.makeText(getActivity(), "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization",FigsApplication.getAuthToken());
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

    private void showJSON(String json){
        SentimentsParse pj = new SentimentsParse(json);
        listBind(pj.parseJSON(),pj.getListofList());
    }



    /**
     * {@link RecyclerView.Adapter} that can display a {@link Sentiments} and makes a call to the
     * <p>
     * TODO: Replace the implementation with code for your data type.
     */
    public class SentimentsRecyclerViewAdapter extends RecyclerView.Adapter<SentimentsRecyclerViewAdapter.ViewHolder> {

        private final ArrayList<Sentiments> mValues;
        private ArrayList<ArrayList<BarChartData>> listOLists = new ArrayList<ArrayList<BarChartData>>();
        private Context context;


        public SentimentsRecyclerViewAdapter(ArrayList<Sentiments> items, final Context context, final ArrayList<ArrayList<BarChartData>> listOLists) {
            mValues = items;
            this.context = context;
            this.listOLists = listOLists;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_analyse_sentiments, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            Collections.sort(mValues, new Comparator<Sentiments>() {
                public int compare(Sentiments v1, Sentiments v2) {
                    return v1.getName().compareTo(v2.getName());
                }
            });

            String name = mValues.get(position).getName();

            switch (name) {

                case "NAS":
                    name = "United States (NASDAQ)";
                    holder.imageFlag.setBackgroundResource(R.mipmap.us);
                    if(isGreen(mValues.get(position).getSentiment_index()))
                        marker_nasdaq.setBackgroundResource(R.mipmap.green_pin);
                    break;
                case "JAPAN":
                    name = "Japan (TSE)";
                    holder.imageFlag.setBackgroundResource(R.mipmap.japan);
                    if(isGreen(mValues.get(position).getSentiment_index()))
                        marker_japan.setBackgroundResource(R.mipmap.green_pin);
                    break;
                case "NYS":
                    name = "United States (NYS)";
                    holder.imageFlag.setBackgroundResource(R.mipmap.us);
                    if(isGreen(mValues.get(position).getSentiment_index()))
                        marker_nyse.setBackgroundResource(R.mipmap.green_pin);
                    break;
                case "HK":
                    name = "Hong Kong";
                    holder.imageFlag.setBackgroundResource(R.mipmap.hongkong);
                    if(isGreen(mValues.get(position).getSentiment_index()))
                        marker_HongKong.setBackgroundResource(R.mipmap.green_pin);
                    break;
                case "SGX":
                    name = "Singapore (SGX)";
                    holder.imageFlag.setBackgroundResource(R.mipmap.singapore);
                    if(isGreen(mValues.get(position).getSentiment_index()))
                        marker_singapore.setBackgroundResource(R.mipmap.green_pin);
                    break;
                case "TAIWAN":
                    name = "Taiwan (TWSE)";
                    holder.imageFlag.setBackgroundResource(R.mipmap.taiwan);
                    if(isGreen(mValues.get(position).getSentiment_index()))
                         marker_taiwan.setBackgroundResource(R.mipmap.green_pin);
                    break;

            }


            holder.entitiesCountry.setText(name);

            String percentage = String.format("%.1f", new BigDecimal(mValues.get(position).getSentiment_index()));
            holder.buttonPercent.setText(percentage + "%");

            final ArrayList<BarChartData> lineChart = listOLists.get(position);
            if (Float.parseFloat(mValues.get(position).getSentiment_index()) > 50f) {
                holder.buttonPercent.setBackgroundColor(ContextCompat.getColor(context, R.color.greenBlueTwo));
                LineData data = getData(lineChart, R.color.greenBlueTwo, name);
                setupChart(holder.lineChart, data);
            } else {
                holder.buttonPercent.setBackgroundColor(ContextCompat.getColor(context, R.color.rouge));
                LineData data = getData(lineChart, R.color.rouge, name);
                setupChart(holder.lineChart, data);
            }

            holder.text3MPreturn.setText(mValues.get(position).getPotential_return3m() + "%");
            holder.text6MPreturn.setText(mValues.get(position).getPotential_return6m() + "%");
            holder.text9MPreturn.setText(mValues.get(position).getPotential_return9m() + "%");
            holder.text12MPreturn.setText(mValues.get(position).getPotential_return12m() + "%");

            holder.text3Mploss.setText(mValues.get(position).getPotential_loss3m() + "%");
            holder.text6Mploss.setText(mValues.get(position).getPotential_loss6m() + "%");
            holder.text9Mploss.setText(mValues.get(position).getPotential_loss9m() + "%");
            holder.text12Mploss.setText(mValues.get(position).getPotential_loss12m() + "%");

            holder.text3Mrlr.setText(mValues.get(position).getReturnriskratio3m() + "%");
            holder.text6Mrlr.setText(mValues.get(position).getReturnriskratio6m() + "%");
            holder.text9Mrlr.setText(mValues.get(position).getReturnriskratio9m() + "%");
            holder.text12Mrlr.setText(mValues.get(position).getReturnriskratio12m() + "%");

            holder.linear_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,SentimentsDetailsActivity.class);
                    intent.putParcelableArrayListExtra(KEY_SENTIMENTS_DETAILS, mValues);
                    intent.putExtra(KEY_FLAG_POSITION,position);
                    context.startActivity(intent);
                }
            });
        }

        private void setupChart(LineChart chart, LineData data) {

            chart.setDrawGridBackground(false);

            // no description text
            chart.getDescription().setEnabled(false);

            // enable touch gestures
            chart.setTouchEnabled(true);

            // enable scaling and dragging
            chart.setDragEnabled(false);
            chart.setScaleEnabled(false);

            // if disabled, scaling can be done on x- and y-axis separately
            chart.setPinchZoom(false);

            // create a custom MarkerView (extend MarkerView) and specify the layout
            // to use for it
            MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view);
            mv.setChartView(chart); // For bounds control
            chart.setMarker(mv); // Set the marker to the chart

            chart.getAxisRight().setEnabled(false);
            chart.getAxisLeft().setEnabled(true);
            chart.getXAxis().setEnabled(false);
            chart.getAxisLeft().setDrawLimitLinesBehindData(false);
            chart.getAxisLeft().enableGridDashedLine(5f, 5f, 0f);

            // add data
            chart.setData(data);
            chart.getLegend().setEnabled(false);
            chart.animateX(2500);

            // get the legend (only possible after setting data)
            Legend l = chart.getLegend();

            // modify the legend ...
            l.setForm(Legend.LegendForm.DEFAULT);
        }

        private LineData getData(ArrayList<BarChartData> lineChart, int color, String name) {

            ArrayList<Entry> yVals = new ArrayList<Entry>();

            for (int i = 0; i < lineChart.size(); i++) {
                yVals.add(new Entry(i, Float.parseFloat(lineChart.get(i).getValue())));
            }
            // create a dataset and give it a type
            LineDataSet set1 = new LineDataSet(yVals, "" + name);

            set1.setLineWidth(1.75f);
            set1.setCircleRadius(3f);
            set1.setCircleHoleRadius(1.5f);
            set1.setColor(ContextCompat.getColor(context, color));
            set1.setCircleColor(Color.GRAY);
            set1.setDrawCircleHole(false);
            set1.setDrawCircles(false);
            set1.setHighLightColor(ContextCompat.getColor(context, R.color.tealish));
            set1.setDrawValues(false);
            // create a data object with the datasets
            LineData data = new LineData(set1);

            return data;
        }

        private boolean isGreen(String sentimentsIndex){
            return  Float.parseFloat(sentimentsIndex) > 50f;
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            @BindView(R.id.text_three_month_preturn)
            AppCompatTextView text3MPreturn;
            @BindView(R.id.text_six_month_preturn)
            AppCompatTextView text6MPreturn;
            @BindView(R.id.text_nine_month_preturn)
            AppCompatTextView text9MPreturn;
            @BindView(R.id.text_twelve_month_preturn)
            AppCompatTextView text12MPreturn;

            @BindView(R.id.text_three_month_ploss)
            AppCompatTextView text3Mploss;
            @BindView(R.id.text_six_month_ploss)
            AppCompatTextView text6Mploss;
            @BindView(R.id.text_nine_month_ploss)
            AppCompatTextView text9Mploss;
            @BindView(R.id.text_twelve_month_ploss)
            AppCompatTextView text12Mploss;

            @BindView(R.id.text_three_month_rlr)
            AppCompatTextView text3Mrlr;
            @BindView(R.id.text_six_month_rlr)
            AppCompatTextView text6Mrlr;
            @BindView(R.id.text_nine_month_rlr)
            AppCompatTextView text9Mrlr;
            @BindView(R.id.text_twelve_month_rlr)
            AppCompatTextView text12Mrlr;

            @BindView(R.id.text_date1)
            AppCompatTextView textDate1;
            @BindView(R.id.text_date2)
            AppCompatTextView textDate2;
            @BindView(R.id.text_date3)
            AppCompatTextView textDate3;
            @BindView(R.id.text_date4)
            AppCompatTextView textDate4;

            @BindView(R.id.entities_Country)
            TextView entitiesCountry;
            @BindView(R.id.button_percent)
            AppCompatButton buttonPercent;

            @BindView(R.id.lineChart)
            LineChart lineChart;

            @BindView(R.id.linear_new_map)
            LinearLayout linear_new_map;

            @BindView(R.id.imageFlag)
            ImageView imageFlag;


            @BindView(R.id.linear_item)
            LinearLayout linear_item;



            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                mView = view;

            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        menu.findItem(R.id.miFilter).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
}


