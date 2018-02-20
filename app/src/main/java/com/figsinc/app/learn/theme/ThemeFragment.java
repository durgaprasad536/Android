package com.figsinc.app.learn.theme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.figsinc.app.analyze.search.SearchActivity;
import com.figsinc.app.learn.Model.Theme;
import com.figsinc.app.learn.filter.FilterActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.figsinc.app.learn.filter.FilterActivity.IS_FROM_THEMES;
import static com.figsinc.app.learn.filter.FilterActivity.KEY_FILTER_CATAGORY_URL;


public class ThemeFragment extends Fragment {

    static final int PICK_FILTER_REQUEST = 1;  // The request code
    ArrayList<Theme> themeArrayList;
    private RecyclerView recyclerView;

    public ThemeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_analyse, menu);
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.miFilter).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.miFilter:
                navigateToFilter();
                return true;
            case R.id.action_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recylerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        network();
    }

    private void listBind(ArrayList<Theme> sectorArrayList) {

        final ThemeAdapter mAdapter = new ThemeAdapter(sectorArrayList, getActivity());
        recyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void network() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String url = Constants.thematicsList;

            //  System.out.println(" **********  " + FigsApplication.getAuthToken());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                            showJSON(response);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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

    private void showJSON(String json) {
        ThemeParse pj = new ThemeParse(json);
        themeArrayList = pj.parseJSON();
        listBind(themeArrayList);
    }

    private void navigateToFilter() {
        Intent intent = new Intent(getActivity(), FilterActivity.class);
        intent.putExtra(KEY_FILTER_CATAGORY_URL, Constants.thematicsFilterCategory);
        intent.putExtra(IS_FROM_THEMES, true);
        startActivityForResult(intent, PICK_FILTER_REQUEST);
    }

    int[] returnsLimit = {0, 10, 20};

    private ArrayList<Theme> filter(ArrayList<Theme> models, String query, String region, int returns) {
        final String lowerCaseQuery = query.toLowerCase();

        final ArrayList<Theme> filteredModelList = new ArrayList<>();
        for (Theme model : models) {
            final String text = model.getCategory_name().toLowerCase();
            final String rank = String.valueOf(model.getPotential_returns());
            float modelReturn = Float.parseFloat(model.getPotential_returns());
            if (text.contains(lowerCaseQuery) || rank.contains(lowerCaseQuery)) {
                if (returns == 0) {
                    filteredModelList.add(model);
                } else if (returns == 1 && modelReturn < 0) {
                    filteredModelList.add(model);
                } else if (returns == 2 && modelReturn >= 0 && modelReturn < 10) {
                    filteredModelList.add(model);
                } else if (returns == 3 && modelReturn >= 10 && modelReturn < 20) {
                    filteredModelList.add(model);
                } else if (returns == 4 && modelReturn >= 20) {
                    filteredModelList.add(model);
                }
            }
        }
        return filteredModelList;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_FILTER_REQUEST) {

            if (resultCode == Activity.RESULT_OK) {
                //String result = data.getStringExtra("result");
                String category = data.getStringExtra("CATEGORY");
                String region = data.getStringExtra("REGION");
                int returns = data.getIntExtra("RETURNS", 0);

                if (category == null || category.equals(null) || category.equals("All")) {
                    listBind(themeArrayList);
                } else {

                    final ArrayList<Theme> filteredArrayList = filter(themeArrayList, category, region, returns);
                    listBind(filteredArrayList);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}
