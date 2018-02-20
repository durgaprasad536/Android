package com.figsinc.app.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.figsinc.app.Constants;
import com.figsinc.app.R;
import com.figsinc.app.login.LoginActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    private SettingsAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.recyclerView)RecyclerView recyclerView;

    @BindView(R.id.textView_title)TextView textView_title;
    @BindView(R.id.textView_subtitle)TextView textView_subtitle;
    @BindView(R.id.linear_item)LinearLayout linear_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        listBind();
        Constants.setStatusBar(this,R.color.darkish_blue);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void listBind(){

        List<String> title = Arrays.asList(getResources().getStringArray(R.array.settings_title));
        List<String> subTitle = Arrays.asList(getResources().getStringArray(R.array.settings_sub_title));

        mAdapter = new SettingsAdapter(title,subTitle, SettingsActivity.this);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(SettingsActivity.this.getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(SettingsActivity.this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        textView_title.setText(R.string.title_settings_item);
        textView_subtitle.setText(R.string.subtitle_settings_item);
        linear_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SettingsActivity.this,SupportCenterActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.buttonLogout)
    public void logout(){

        SharedPreferences sharedPref = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        sharedPref.edit().remove("auth_token").commit();
        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

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
}
