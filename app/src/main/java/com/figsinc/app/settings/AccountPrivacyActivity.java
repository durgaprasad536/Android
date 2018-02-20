package com.figsinc.app.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.figsinc.app.R;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountPrivacyActivity extends AppCompatActivity {

    private AccuntPrivacyAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.recyclerView)RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_privacy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        listBind();
    }

    private void listBind(){

        List<String> title = Arrays.asList(getResources().getStringArray(R.array.account_privacy_title));
        List<String> subTitle = Arrays.asList(getResources().getStringArray(R.array.account_privacy_subtitle));

        mAdapter = new AccuntPrivacyAdapter(title,subTitle, AccountPrivacyActivity.this);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(AccountPrivacyActivity.this.getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(AccountPrivacyActivity.this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }
}
