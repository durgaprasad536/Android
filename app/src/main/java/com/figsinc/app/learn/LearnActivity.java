package com.figsinc.app.learn;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.figsinc.app.Constants;
import com.figsinc.app.R;
import com.figsinc.app.analyze.AnalyzeActivity;
import com.figsinc.app.analyze.filter.AnalyseFilterActivity;
import com.figsinc.app.analyze.search.SearchActivity;
import com.figsinc.app.collect.CollectActivity;
import com.figsinc.app.learn.sector.SectorFragment;
import com.figsinc.app.learn.theme.ThemeFragment;
import com.figsinc.app.settings.SettingsActivity;
import com.figsinc.app.utils.FigsPopupActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.figsinc.app.analyze.filter.AnalyseFilterActivity.CURRENT_INDEX;

public class LearnActivity extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener{

    @BindView(R.id.imageViewAnalyse)
    ImageView imageViewAnalyse;
    @BindView(R.id.imageViewLearn)
    ImageView imageViewLearn;
    @BindView(R.id.imageViewCollect)
    ImageView imageViewCollect;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textLearn)
    TextView textLearn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setBackgroundColor(ContextCompat.getColor(LearnActivity.this,R.color.tealish));
        Constants.setStatusBar(this,R.color.tealish);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(LearnActivity.this,R.color.tealish));

        setupViewPager(viewPager);


        tabLayout.setupWithViewPager(viewPager);
       // setupTabIcons();
        setFooterIcons();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position ==0){
                    toolbar.setBackgroundColor(ContextCompat.getColor(LearnActivity.this,R.color.tealish));
                    Constants.setStatusBar(LearnActivity.this,R.color.tealish);
                    tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(LearnActivity.this,R.color.tealish));
                    tabLayout.setTabTextColors(ContextCompat.getColor(LearnActivity.this,R.color.blueyGreyThree),ContextCompat.getColor(LearnActivity.this,R.color.tealish));
                }else  if(position ==1){
                    toolbar.setBackgroundColor(ContextCompat.getColor(LearnActivity.this,R.color.iris));
                    Constants.setStatusBar(LearnActivity.this,R.color.iris);
                    tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(LearnActivity.this,R.color.iris));
                    tabLayout.setTabTextColors(ContextCompat.getColor(LearnActivity.this,R.color.blueyGreyThree),ContextCompat.getColor(LearnActivity.this,R.color.iris));

                }

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

   /* *//**
     * Adding custom view to tab
     *//*
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Sector");

        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Theme");

        tabLayout.getTabAt(1).setCustomView(tabTwo);

    }
*/
    /**
     * Adding fragments to ViewPager
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new SectorFragment(), "Sector");
        adapter.addFrag(new ThemeFragment(), "Theme");
       // adapter.addFrag(new ThreeFragment(), "Guru");
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

    @OnClick(R.id.linearAnalyze)
    public void analyze(View view) {
        Intent intent = new Intent(this, AnalyzeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.linearCollect)
    public void collect(View view) {
        Intent intent = new Intent(this, CollectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.linearFeed)
    public void feed(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.imageViewFigs)
    public void imageViewFigs(View view) {
        Intent intent = new Intent(this, FigsPopupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void setFooterIcons() {
        imageViewAnalyse.setBackground(getResources().getDrawable(R.mipmap.analyse_grey));
        imageViewLearn.setBackground(getResources().getDrawable(R.mipmap.learn_active));
        textLearn.setTextColor(ContextCompat.getColor(LearnActivity.this,R.color.cobalt));
        imageViewCollect.setBackground(getResources().getDrawable(R.mipmap.bookmark_grey));

    }

}
