package com.figsinc.app.collect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.figsinc.app.learn.Model.Sector;
import com.figsinc.app.learn.Model.Theme;
import com.figsinc.app.learn.sector.SectorAdapter;
import com.figsinc.app.learn.sector.SectorParse;
import com.figsinc.app.learn.theme.ThemeAdapter;
import com.figsinc.app.learn.theme.ThemeParse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavedIdeasFragment extends Fragment {

    String url = Constants.collectSavedideas;

    public SavedIdeasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collect_saved_ideas, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        network();
    }

    private void listBind(ArrayList<Sector> sectorArrayList) {
        try {
            System.out.println(" sectorArrayList " + sectorArrayList.size());
            RecyclerView recyclerView_SectorSaved = (RecyclerView) getActivity().findViewById(R.id.recyclerView_SectorSaved);
            SectorAdapter mAdapter = new SectorAdapter(sectorArrayList, getActivity());
            //recyclerView_SectorSaved.setHasFixedSize(true);
            recyclerView_SectorSaved.setNestedScrollingEnabled(true);
            recyclerView_SectorSaved.setHasFixedSize(false);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView_SectorSaved.setLayoutManager(mLayoutManager);
            // recyclerView_SectorSaved.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            recyclerView_SectorSaved.setItemAnimator(new DefaultItemAnimator());
            recyclerView_SectorSaved.setAdapter(mAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void listBindThemeatics(ArrayList<Theme> themeArrayList) {
        try {
        RecyclerView recyclerView_ThematicsSaved = (RecyclerView) getActivity().findViewById(R.id.recyclerView_ThematicsSaved);
        ThemeAdapter mAdapter = new ThemeAdapter(themeArrayList, getActivity());
        //recyclerView_ThematicsSaved.setHasFixedSize(true);
        recyclerView_ThematicsSaved.setNestedScrollingEnabled(false);
        recyclerView_ThematicsSaved.setHasFixedSize(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView_ThematicsSaved.setLayoutManager(mLayoutManager);
       // recyclerView_ThematicsSaved.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView_ThematicsSaved.setItemAnimator(new DefaultItemAnimator());
        recyclerView_ThematicsSaved.setAdapter(mAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void network() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

           // System.out.println(" **********  " + FigsApplication.getAuthToken());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                           // System.out.println(" 888888888888888888888888 ");
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray sectorArry=jsonObject.getJSONArray("Sector");
                                showJSON(sectorArry.toString());
                                JSONArray themesArray=jsonObject.getJSONArray("Themes");
                                showThemeJSON(themesArray.toString());
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
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
        SectorParse pj = new SectorParse(json);
        listBind(pj.parseJSON());
    }

    @Override
    public void onResume() {
        super.onResume();

       // System.out.println(" 888888888888888888888888 ");


    }

   /* private static List<Sector> filter(List<Sector> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Sector> filteredModelList = new ArrayList<>();
        for (Sector model : models) {
            final String text = model.getCategory_name().toLowerCase();
            final String rank = String.valueOf(model.getPotential_returns());
            if (text.contains(lowerCaseQuery) || rank.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }*/

    private void showThemeJSON(String json) {
        ThemeParse pj = new ThemeParse(json);
        listBindThemeatics(pj.parseJSON());
    }

}
