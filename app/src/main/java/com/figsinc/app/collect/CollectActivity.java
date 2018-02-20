package com.figsinc.app.collect;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.figsinc.app.Constants;
import com.figsinc.app.FigsApplication;
import com.figsinc.app.R;
import com.figsinc.app.SearchResultsActivity;
import com.figsinc.app.analyze.AnalyzeActivity;
import com.figsinc.app.analyze.trendinganalysts.TrendingAnalystsFragment;
import com.figsinc.app.learn.LearnActivity;
import com.figsinc.app.learn.filter.FilterActivity;
import com.figsinc.app.learn.theme.ThemeProfileActivity;
import com.figsinc.app.settings.SettingsActivity;
import com.figsinc.app.settings.SubscriptionActivity;
import com.figsinc.app.utils.FigsPopupActivity;
import com.figsinc.app.utils.TagLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.figsinc.app.Constants.collectGetUserDetails;
import static com.figsinc.app.Constants.trendingAnalystsProfileImageHolder;

public class CollectActivity extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener {


    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageViewAnalyse)
    ImageView imageViewAnalyse;
    @BindView(R.id.imageViewLearn)
    ImageView imageViewLearn;
    @BindView(R.id.imageViewCollect)
    ImageView imageViewCollect;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.fab)
    FloatingActionButton imgProfile;
    @BindView(R.id.textView_entity_header)
    TextView textView_entity_header;
    @BindView(R.id.textView_entity_subheader)
    TagLayout textView_entity_subheader;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.textCollect)
    TextView textCollect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Constants.setStatusBar(this, R.color.cobalt);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        setFooterIcons();
        getUserDetails();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.collect, menu);
        SearchView search = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchResultsActivity.class)));
        search.setQueryHint(getResources().getString(R.string.search_hint));

        return true;
    }

    /**
     * Adding custom view to tab
     */
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(R.string.title_Watchlist);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabSavedFunds = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabSavedFunds.setText(R.string.title_Saved_Funds);
        tabLayout.getTabAt(1).setCustomView(tabSavedFunds);


        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(R.string.title_Saved_Ideas);
        tabLayout.getTabAt(2).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(R.string.title_Saved_Analysts);
        tabLayout.getTabAt(3).setCustomView(tabThree);

    }

    /**
     * Adding fragments to ViewPager
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new WatichListFragment(), getResources().getString(R.string.title_Watchlist));
        adapter.addFrag(new SavedFundsFragment(), getResources().getString(R.string.title_Saved_Funds));
        adapter.addFrag(new SavedIdeasFragment(), getResources().getString(R.string.title_Saved_Ideas));

        TrendingAnalystsFragment trendingAnalystsFragment = new TrendingAnalystsFragment();
        Bundle bundleFeatures = new Bundle();
        bundleFeatures.putString("SavedAnalystsURL", Constants.collectSavedAnalysts);
        trendingAnalystsFragment.setArguments(bundleFeatures);

        adapter.addFrag(trendingAnalystsFragment, getResources().getString(R.string.title_Saved_Analysts));
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
            case R.id.settings:
                navigateToFilter();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToFilter() {
        Intent intent = new Intent(CollectActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.linearAnalyze)
    public void analyze(View view) {
        Intent intent = new Intent(this, AnalyzeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.linearCollect)
    public void collect(View view) {
        Intent intent = new Intent(this, CollectActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.linearFeed)
    public void feed(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.imageViewLearn)
    public void imageViewLearn(View view) {
        Intent intent = new Intent(this, LearnActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.imageViewFigs)
    public void imageViewFigs(View view) {
        Intent intent = new Intent(this, FigsPopupActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.linearLearn)
    public void learn(View view) {
        Intent intent = new Intent(this, LearnActivity.class);
        startActivity(intent);
    }

    private void setFooterIcons() {
        imageViewAnalyse.setBackground(getResources().getDrawable(R.mipmap.analyse_grey));
        imageViewLearn.setBackground(getResources().getDrawable(R.mipmap.learn_grey));
        imageViewCollect.setBackground(getResources().getDrawable(R.mipmap.bookmark_active));
        textCollect.setTextColor(ContextCompat.getColor(CollectActivity.this,R.color.cobalt));

    }

    private void getUserDetails() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(CollectActivity.this);


            //System.out.println(" **********  " + FigsApplication.getAuthToken());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, collectGetUserDetails,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                            System.out.println(" TrendingAnalysts  " + response);

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                textView_entity_header.setText(jsonObject.getString("First_Name") + jsonObject.getString("Last_Name"));
                                LayoutInflater layoutInflater = getLayoutInflater();
                                String tag = "";
                                for (int i = 0; i < 2; i++) {

                                    if (i == 0) {
                                        tag = jsonObject.getString("Risk_Appetite");
                                    } else if (i == 1) {
                                        tag = jsonObject.getString("Style_Investment");
                                    }
                                    View tagView = layoutInflater.inflate(R.layout.tag_layout, null, false);

                                    TextView tagTextView = (TextView) tagView.findViewById(R.id.tagTextView);
                                    tagTextView.setText(tag);
                                    textView_entity_subheader.addView(tagView);
                                }


                                String profileUrl = jsonObject.getString("Avatar");
                                if (profileUrl.equals("false")) {
                                    profileUrl = trendingAnalystsProfileImageHolder;
                                }
                                Glide.with(CollectActivity.this)
                                        .load(profileUrl)
                                        .apply(RequestOptions.circleCropTransform())
                                        .into(imgProfile);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                  //  Toast.makeText(CollectActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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
}

// to show item when collapsing the app bar layour or co-ordinator layout

// https://stackoverflow.com/questions/36473695/how-can-i-add-the-viewpager-and-tablayout-under-the-collapsing-toolbar-layout

 /* // final RoundedNetworkImageView getImgProfileTop = (RoundedNetworkImageView)findViewById(R.id.img_profile_top);
        final TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        AppBarLayout.OnOffsetChangedListener mListener = new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (collapsingToolbarLayout.getHeight() + verticalOffset < 1.5 * ViewCompat.getMinimumHeight(collapsingToolbarLayout)) {
                    //getImgProfileTop.animate().alpha(1).setDuration(400);
                    //user_follow_top.animate().alpha(1).setDuration(400);
                   // toolbar_title.setText("");
                    toolbar.animate().alpha(0).setDuration(400);
                } else {
                    // getImgProfileTop.animate().alpha(0).setDuration(400);
                    //user_follow_top.animate().alpha(0).setDuration(400);
                    // toolbar_title.setText("Rajesh");
                    toolbar.animate().alpha(1).setDuration(400);
                }
            }
        };

        appBarLayout.addOnOffsetChangedListener(mListener);*/