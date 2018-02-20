package com.figsinc.app.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.figsinc.app.Constants;
import com.figsinc.app.R;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmailNotificationActivity extends AppCompatActivity {

    private EmailNotificationAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.recyclerView)RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        listBind();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Constants.setStatusBar(this,R.color.darkish_blue);
    }

    private void listBind(){

        List<String> title = Arrays.asList(getResources().getStringArray(R.array.settings_title));
        List<String> subTitle = Arrays.asList(getResources().getStringArray(R.array.settings_sub_title));

        mAdapter = new EmailNotificationAdapter(title,subTitle, EmailNotificationActivity.this);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(EmailNotificationActivity.this.getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(EmailNotificationActivity.this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

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
