package com.figsinc.app.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.figsinc.app.Constants;
import com.figsinc.app.R;
import com.figsinc.app.login.LoginActivity;
import com.figsinc.app.login.SocialMediaActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnboardingActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.linear_bottomLayout)
    LinearLayout linear_bottomLayout;
    @BindView(R.id.buttonLogIn)
    Button buttonLogIn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);

        setupViewPager(pager);
        tabLayout.setupWithViewPager(pager);

        linear_bottomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnboardingActivity.this, LoginActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnboardingActivity.this, SocialMediaActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        if (Constants.isDebug) {
            Intent intent = new Intent(OnboardingActivity.this, LoginActivity.class);
            startActivity(intent);
            // finish();
        }

        Constants.setStatusBar(this, R.color.darkish_blue);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OnboardFragmentOne(), "");
        adapter.addFrag(new OnboardFragmentTwo(), "");
        adapter.addFrag(new OnboardFragmentThree(), "");
        adapter.addFrag(new OnboardFragmentFour(), "");
        adapter.addFrag(new OnboardFragmentFive(), "");
        adapter.addFrag(new OnboardFragmentSix(), "");

        viewPager.setAdapter(adapter);
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


}
