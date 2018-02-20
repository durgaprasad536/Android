package com.figsinc.app.analyze.search;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.figsinc.app.Constants;
import com.figsinc.app.FigsApplication;
import com.figsinc.app.R;
import com.figsinc.app.SearchResultsActivity;
import com.figsinc.app.analyze.AnalyzeActivity;
import com.figsinc.app.analyze.model.Analyst;
import com.figsinc.app.analyze.model.AnalystSearchModel;
import com.figsinc.app.analyze.model.Funds;
import com.figsinc.app.analyze.model.RecentSearchModel;
import com.figsinc.app.analyze.model.Stock;
import com.figsinc.app.analyze.trendinganalysts.AnalystInfoActivity;
import com.figsinc.app.analyze.trendingfunds.FundsInfoActivity;
import com.figsinc.app.analyze.trendingfunds.FundsLipperScoreCardActivity;
import com.figsinc.app.analyze.trendingstocks.StockInfoActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.figsinc.app.Constants.PREF_NAME;
import static com.figsinc.app.analyze.trendingfunds.FundsInfoActivity.KEY_EXTRA_FUNDS_LIPPER_NO;
import static com.figsinc.app.analyze.trendingstocks.StockInfoActivity.KEY_EXTRA_CF_RIC;
import static com.figsinc.app.analyze.trendingstocks.StockInfoActivity.KEY_EXTRA_SUB_INDUSTRY_CODE;
import static com.figsinc.app.analyze.trendingstocks.StockInfoActivity.KEY_EXTRA_TICKER_EXCHANGE;

/**
 * Created by Dinash on 24-12-2017.
 */

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.searchListView)
    ListView searchListView;


    @BindView(R.id.noRecords)
    TextView noRecords;

    ArrayList<Object> objectArrayList = new ArrayList<>();

    private SearchView search;

    String SearchAutoCompleteResponse = "";

    AutoSuggestDropdownAdapter autoSuggestDropdownAdapter;
    private String query;

    public void DoSearchNetworkCall(String newText) {
        query = newText;
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
            String url = Constants.searchAutoCompleteAnalyse + newText;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            SearchAutoCompleteResponse = response;
                            showAutoSuggestionJSON(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
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

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    25000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        objectArrayList = getRecentSearchAsList();
        Constants.setStatusBar(this, R.color.darkish_blue);

        if (objectArrayList != null && objectArrayList.size() > 0) {
            objectArrayList.add(0, getString(R.string.recent_search));
        }

        autoSuggestDropdownAdapter = new AutoSuggestDropdownAdapter(objectArrayList, this);
        searchListView.setEmptyView(noRecords);
        searchListView.setAdapter(autoSuggestDropdownAdapter);
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (view.getTag() == null) {
                    return;
                }
                addToRecentSearch(view.getTag());
                if (view.getTag() instanceof Analyst) {
                    //Toast.makeText(SearchActivity.this, "Analyst id : " + ((Analyst) view.getTag()).getAnalystid(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SearchActivity.this, AnalystInfoActivity.class);
                    intent.putExtra("AnalystID", ((Analyst) view.getTag()).getAnalystid());
                    intent.putExtra("imageUrl", Constants.trendingAnalystsProfileImage + ((Analyst) view.getTag()).getAnalystid() + ".png");
                    startActivity(intent);
                } else if (view.getTag() instanceof Stock) {
                    // Toast.makeText(SearchActivity.this, "Stock : " + ((Stock) view.getTag()).getCompanyName(), Toast.LENGTH_SHORT).show();
                    Bundle b = new Bundle();
                    b.putString(KEY_EXTRA_TICKER_EXCHANGE, ((Stock) view.getTag()).getTickerExchange());
                    b.putString(KEY_EXTRA_SUB_INDUSTRY_CODE, "" + ((Stock) view.getTag()).getSubIndustryCode());
                    b.putString(KEY_EXTRA_CF_RIC, "" + ((Stock) view.getTag()).getRic());
                    SearchActivity.this.startActivity(new Intent(SearchActivity.this, StockInfoActivity.class).putExtras(b));
                } else if (view.getTag() instanceof Funds) {
                    // Toast.makeText(SearchActivity.this, "Funds : " + ((Funds) view.getTag()).getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SearchActivity.this, FundsInfoActivity.class);
                    intent.putExtra(KEY_EXTRA_FUNDS_LIPPER_NO, ((Funds) view.getTag()).getLippernumber());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.analyse, menu);
        search = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        search.setIconifiedByDefault(false);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchResultsActivity.class)));
        search.setQueryHint(getResources().getString(R.string.search_hint));

        search.requestFocus();

        search.setOnQueryTextListener(onQueryTextListener);
        return true;
    }

    final SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {

            if (newText != null && newText.length() >= 3) {
                //new MyNetworkAsync(newText).execute();
                DoSearchNetworkCall(newText);
            } else {
                if (objectArrayList != null)
                    objectArrayList.clear();
                if (autoSuggestDropdownAdapter != null)
                    autoSuggestDropdownAdapter.notifyDataSetChanged();
            }
            search.requestFocus();

            return false;
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class AutoSuggestDropdownAdapter extends BaseAdapter {

        ArrayList<Object> objectArrayList = new ArrayList<>();
        LayoutInflater layoutInflater;

        public AutoSuggestDropdownAdapter(ArrayList<Object> suggestionList, Context ctx) {
            this.objectArrayList = suggestionList;
            layoutInflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return objectArrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return objectArrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean isEnabled(int position) {
            if (objectArrayList.get(position) instanceof String) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View convertView = view;

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_anlyses_search_dropdown, null);
            }


            String content = "";
            String type = "";
            if (objectArrayList.get(i) instanceof Analyst) {
                content = ((Analyst) objectArrayList.get(i)).getAnalystname();
                type = getString(R.string.analyst_search);
            } else if (objectArrayList.get(i) instanceof RecentSearchModel && ((RecentSearchModel) objectArrayList.get(i)).getAnalyst() != null) {
                content = ((RecentSearchModel) objectArrayList.get(i)).getAnalyst().getAnalystname();
                type = getString(R.string.analyst_search);
                convertView.setTag(((RecentSearchModel) objectArrayList.get(i)).getAnalyst());
            } else if (objectArrayList.get(i) instanceof Stock) {
                content = ((Stock) objectArrayList.get(i)).getCompanyName();
                type = getString(R.string.stock_search);
            } else if (objectArrayList.get(i) instanceof RecentSearchModel && ((RecentSearchModel) objectArrayList.get(i)).getStock() != null) {
                content = ((RecentSearchModel) objectArrayList.get(i)).getStock().getCompanyName();
                convertView.setTag(((RecentSearchModel) objectArrayList.get(i)).getStock());
                type = getString(R.string.stock_search);
            } else if (objectArrayList.get(i) instanceof Funds) {
                content = ((Funds) objectArrayList.get(i)).getName();
                type = getString(R.string.funds_search);
            } else if (objectArrayList.get(i) instanceof RecentSearchModel && ((RecentSearchModel) objectArrayList.get(i)).getFunds() != null) {
                content = ((RecentSearchModel) objectArrayList.get(i)).getFunds().getName();
                type = getString(R.string.funds_search);
                convertView.setTag(((RecentSearchModel) objectArrayList.get(i)).getFunds());

            } else if (objectArrayList.get(i) instanceof String) {
                content = objectArrayList.get(i).toString();
            }

            ((TextView) convertView.findViewById(R.id.textContent)).setText(content);
            ((TextView) convertView.findViewById(R.id.textType)).setText(type);
////////////
            String filter = query;

            String itemValue = "";
            try {
                itemValue = ((TextView) convertView.findViewById(R.id.textContent)).getText().toString().trim();
                if (itemValue != null && itemValue.length() > 0 && filter!=null && filter.length() > 3) {
                    int startPos = itemValue.toLowerCase(Locale.US).indexOf(filter.toLowerCase(Locale.US));
                    int endPos = startPos + filter.length();

                    if (startPos != -1) // This should always be true, just a sanity check
                    {
                        Spannable spannable = new SpannableString(itemValue);
                        ColorStateList blueColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.BLACK});
                        TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null);

                        spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        ((TextView) convertView.findViewById(R.id.textContent)).setText(spannable);
                    } else
                        ((TextView) convertView.findViewById(R.id.textContent)).setText(content);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
 ////////////////
            if (!(objectArrayList.get(i) instanceof RecentSearchModel)) {
                convertView.setTag(objectArrayList.get(i));
            }

            return convertView;
        }
    }

    private void showAutoSuggestionJSON(String response) {
        Gson gson = new Gson();
        AnalystSearchModel[] myTypes = gson.fromJson(response, AnalystSearchModel[].class);

        ArrayList<Object> stringArrayList = new ArrayList<>();
        if (myTypes != null && myTypes.length > 0 && (myTypes[0].getFunds()!=null || myTypes[0].getStock()!=null || myTypes[0].getAnalysts()!=null)) {

            ArrayList<Object> recentSearch = getRecentSearchAsList();

            if (recentSearch != null && recentSearch.size() > 0) {
                stringArrayList.add(getString(R.string.recent_search));
                stringArrayList.addAll(recentSearch);
            }

            if (myTypes[0].getAnalysts() != null) {
                stringArrayList.addAll(myTypes[0].getAnalysts());
            }
            if (myTypes[0].getStock() != null) {
                stringArrayList.addAll(myTypes[0].getStock());
            }
            if (myTypes[0].getFunds() != null) {
                stringArrayList.addAll(myTypes[0].getFunds());
            }
            if (stringArrayList.size() > 0) {
                objectArrayList.clear();
                objectArrayList.addAll(stringArrayList);
                autoSuggestDropdownAdapter.notifyDataSetChanged();
            }
        }
        else {
            objectArrayList.clear();
            autoSuggestDropdownAdapter.notifyDataSetChanged();
        }

    }

//    class MyNetworkAsync extends AsyncTask<Void, Void, Void> {
//
//        String requestQuery = "";
//
//        public MyNetworkAsync(String requestQuery) {
//            this.requestQuery = requestQuery;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            showAutoSuggestionJSON(SearchAutoCompleteResponse);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            DoSearchNetworkCall(requestQuery);
//            return null;
//        }
//    }

    private String getRecentSearches() {


        SharedPreferences shared = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String fundInfoFundsPerfrormance = shared.getString(getString(R.string.recent_search_key), "");
        return fundInfoFundsPerfrormance;
    }

    private ArrayList<Object> getRecentSearchAsList() {
        Gson gson = new Gson();
        ArrayList<Object> objects1 = new ArrayList<>();

        RecentSearchModel[] objects = null;//new ArrayList<>();
        String current = getRecentSearches();
        if (current != null && current.length() > 0) {
            objects = gson.fromJson(current, RecentSearchModel[].class);
            for (RecentSearchModel o : objects) {
                objects1.add(o);
            }
        }

        return objects1;

    }

    private void addToRecentSearch(Object object) {
        Gson gson = new Gson();
        String currentAllItems = getRecentSearches();//Getting the recent search items from pref as string
        RecentSearchModel[] myTypes = null;

        //Converting the string recent search history to RecentSearchModel array
        if (currentAllItems != null && currentAllItems.length() > 0) {
            myTypes = gson.fromJson(currentAllItems, RecentSearchModel[].class);
        }

        RecentSearchModel recentSearchModel = new RecentSearchModel();
        if (object instanceof Analyst) {
            recentSearchModel.setAnalyst((Analyst) object);
        } else if (object instanceof Stock) {
            recentSearchModel.setStock((Stock) object);
        } else if (object instanceof Funds) {
            recentSearchModel.setFunds((Funds) object);
        }

        boolean isDuplicate=false;


        ArrayList<RecentSearchModel> objects = new ArrayList<>();
        //Iterate the RecentSearchModel array and convert into the array list and also do the check for duplicate entry
        if (myTypes != null) {
            for (RecentSearchModel analystSearchModel : myTypes) {
                objects.add(analystSearchModel);
                if(analystSearchModel.getAnalyst()!=null && object instanceof Analyst) {
                    if(analystSearchModel.getAnalyst().getAnalystid().equalsIgnoreCase(((Analyst) object).getAnalystid())){
                        isDuplicate=true;
                    }
                }
                else if(analystSearchModel.getFunds()!=null && object instanceof Funds){
                    if(analystSearchModel.getFunds().getLippernumber().equalsIgnoreCase(((Funds) object).getLippernumber())){
                        isDuplicate=true;
                    }
                }
                else if(analystSearchModel.getStock()!=null && object instanceof  Stock){
                    if(analystSearchModel.getStock().getTicker().equalsIgnoreCase(((Stock) object).getTicker())){
                        isDuplicate=true;
                    }
                }
            }


        }


        if(!isDuplicate) {//Add only if it not a duplicate entry.
            objects.add(recentSearchModel);
        }

        if (objects.size() > 10) {
            objects.remove(0);//Removing the oldest item from the list.
        }

        if (objects.size() > 0) {
            String updatedList = gson.toJson(objects);

            SharedPreferences sharedPref = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.recent_search_key), updatedList);
            editor.commit();
        }

    }
}
