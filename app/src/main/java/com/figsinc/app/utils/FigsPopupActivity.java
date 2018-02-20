package com.figsinc.app.utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.figsinc.app.Constants;
import com.figsinc.app.R;
import com.figsinc.app.analyze.AnalyzeActivity;
import com.figsinc.app.calculator.CalculatorActivity;
import com.figsinc.app.collect.CollectActivity;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FigsPopupActivity extends AppCompatActivity {

    @BindView(R.id.menu_bull)
    FloatingActionMenu menuBull;
    @BindView(R.id.menu_calculator)
    FloatingActionMenu menuCalculator;
    @BindView(R.id.menu_close)
    FloatingActionMenu menuClose;
    @BindView(R.id.menu_eye)
    FloatingActionMenu menuEye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figs_popup);
        ButterKnife.bind(this);
        Constants.setStatusBar(this, R.color.purpleyGrey);

        menuBull.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                menuBull();
            }
        });

        menuCalculator.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                menuCalculator();
            }
        });

        menuClose.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        menuEye.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuEye();
            }
        });

    }

    private void menuBull() {
        Intent intent = new Intent(FigsPopupActivity.this, AnalyzeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void menuCalculator() {
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void menuEye() {
        Intent intent = new Intent(this, CollectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }



}
