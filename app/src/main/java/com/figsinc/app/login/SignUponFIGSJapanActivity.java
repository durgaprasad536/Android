package com.figsinc.app.login;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.figsinc.app.Constants;
import com.figsinc.app.R;
import com.figsinc.app.utils.language.LanguageUtil;
import com.figsinc.app.utils.language.LocaleHelper;
import com.figsinc.app.utils.language.MyContextWrapper;
import com.figsinc.app.utils.language.SPUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUponFIGSJapanActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.language_spinner)
    Spinner spinner;

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("======","attachBaseContext");
        Locale languageType = LanguageUtil.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upon_figsjapan);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbarTitle.setText(getResources().getString(R.string.action_Sign_Up));
        Constants.setStatusBar(SignUponFIGSJapanActivity.this, R.color.darkish_blue);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelected(false);  // must
        spinner.setSelection(0,true);  //must
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (userIsInteracting) {
                    {
                        try {
                            if (i == 0) {
                                LanguageUtil.changeLanguageType(SignUponFIGSJapanActivity.this, Locale.ENGLISH);
                               /// Toast.makeText(SignUponFIGSJapanActivity.this, "ENGLISH", Toast.LENGTH_SHORT).show();
                            } else {
                                LanguageUtil.changeLanguageType(SignUponFIGSJapanActivity.this, Locale.JAPANESE);
                               // Toast.makeText(SignUponFIGSJapanActivity.this, "日本語", Toast.LENGTH_SHORT).show();
                            }
                            SPUtils.setLocalLanguageType(SignUponFIGSJapanActivity.this, i == 0 ? "EngLish" : "日本語");
                            recreate();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        LocaleHelper.setLocale(SignUponFIGSJapanActivity.this, "en");
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userIsInteracting = true;
    }

    private boolean userIsInteracting;

}
