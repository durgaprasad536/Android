package com.figsinc.app.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.figsinc.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinkedAccountActivity extends AppCompatActivity {

    @BindView(R.id.SwitchTwitter)Switch SwitchTwitter;
    @BindView(R.id.SwitchFacebook)Switch SwitchFacebook;
    @BindView(R.id.SwitchGplus)Switch SwitchGplus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.title_activity_linked_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SwitchTwitter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked){
                    Toast.makeText(LinkedAccountActivity.this, "Twitter Turned On.", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(LinkedAccountActivity.this, "Twitter Turned Off.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        SwitchFacebook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked){
                    Toast.makeText(LinkedAccountActivity.this, "Facebook Turned On.", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(LinkedAccountActivity.this, "Facebook Turned Off.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        SwitchGplus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked){
                    Toast.makeText(LinkedAccountActivity.this, "Gplus Turned On.", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(LinkedAccountActivity.this, "Gplus Turned Off.", Toast.LENGTH_SHORT).show();
                }
            }
        });


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
