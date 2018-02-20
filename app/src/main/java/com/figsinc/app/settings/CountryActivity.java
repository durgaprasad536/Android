package com.figsinc.app.settings;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.figsinc.app.Constants;
import com.figsinc.app.R;
import com.figsinc.app.settings.Model.Country;

import java.util.ArrayList;
import android.support.v7.widget.SearchView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryActivity extends AppCompatActivity {

    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerViewCountry;
    private SearchView searchView;
    private String selectedName;

    private CountryRecyclerViewAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Country> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Constants.setStatusBar(this, R.color.darkish_blue);

        items = getIntent().getParcelableArrayListExtra("Country");

        System.out.println(" ^^^^^^^^^=========>>>>> " + items.size());

        mAdapter = new CountryRecyclerViewAdapter(items);
        recyclerViewCountry.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCountry.setLayoutManager(mLayoutManager);
        recyclerViewCountry.setNestedScrollingEnabled(false);
        recyclerViewCountry.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewCountry.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCountry.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CountryRecyclerViewAdapter.SingleClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                selectedName = mAdapter.selectedItem();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        if(id== android.R.id.home){
             setCountry();
        }


        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }

        super.onBackPressed();
    }*/

    private void setCountry(){
        Intent resultIntent = new Intent();
        // TODO Add extras or a data URI to this intent as appropriate.
        resultIntent.putExtra("SelectedCountry", selectedName);
        System.out.println(" ^^^^^^^^^ " + selectedName);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
