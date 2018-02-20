package com.figsinc.app.learn.theme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.figsinc.app.Constants;
import com.figsinc.app.R;
import com.figsinc.app.analyze.trendingfunds.TrendingFundsFragment;
import com.figsinc.app.analyze.trendingstocks.TrendingStocksFragment;

import static com.figsinc.app.Constants.trendingStocksByThemeID;

public class ThemeDetailsSubActivity extends AppCompatActivity {

    private int position =0;
    public static String KEY_COLORCODE="KEY_COLORCODE";
    public static String KEY_TITLE="KEY_TITLE";
    public static String KEY_SECTOR_ID="KEY_SECTOR_ID";
    public static String KEY_POSITION="KEY_POSITION";
    public static String sectorId="";
    private String title;
    private int colorCode=R.color.iris;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sector_details_sub);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sectorId = bundle.getString(KEY_SECTOR_ID);
            title = bundle.getString(KEY_TITLE);
            position = bundle.getInt(KEY_POSITION,0);
            colorCode = bundle.getInt(KEY_COLORCODE,R.color.iris);
            toolbar.setBackgroundColor(ContextCompat.getColor(ThemeDetailsSubActivity.this,colorCode));
            ThemeDetailsSubActivity.this.setTitle(title);
            Constants.setStatusBar(this,colorCode);

        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        switch (position) {
            case 0:
                Bundle bundleFragment = new Bundle();
                bundleFragment.putString(KEY_SECTOR_ID,trendingStocksByThemeID+sectorId);
                Fragment fragment = new TrendingStocksFragment();
                fragment.setArguments(bundleFragment);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName())/*.addToBackStack(null)*/.commit();
                break;

            case 1:
                Bundle bundleFunds = new Bundle();
                bundleFunds.putString(KEY_SECTOR_ID,sectorId);
                Fragment fragmentFunds = new TrendingFundsFragment();
                fragmentFunds.setArguments(bundleFunds);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_frame, fragmentFunds, fragmentFunds.getClass().getSimpleName())/*.addToBackStack(null)*/.commit();
                break;

            case 2:
                Bundle bundleSector = new Bundle();
                bundleSector.putString(KEY_SECTOR_ID, Constants.thmeDetailsTopFiveThemes);
                Fragment otherIdeas = new ThemeFragment();
                otherIdeas.setArguments(bundleSector);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_frame, otherIdeas, otherIdeas.getClass().getSimpleName())/*.addToBackStack(null)*/.commit();
                break;

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id ==android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
