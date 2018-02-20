package com.figsinc.app.utils;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.figsinc.app.R;

import java.util.ArrayList;

public class ActionBarFragment extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener{
    private static final String STATE_QUERY = "q";
    private static final String STATE_MODEL = "m";
    private static final String[] items = {"lorem", "ipsum", "dolor",
            "sit", "amet", "consectetuer", "adipiscing", "elit", "morbi",
            "vel", "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam",
            "vel", "erat", "placerat", "ante", "porttitor", "sodales",
            "pellentesque", "augue", "purus"};
    private ArrayList<String> words = null;
    private ArrayAdapter<String> adapter = null;
    private CharSequence initialQuery = null;
    private SearchView sv = null;
    private ListView listview;


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        setContentView(R.layout.test);

       /* words=new ArrayList<String>();

        for (String s : items) {
            words.add(s);
        }
        listview = (ListView)findViewById(R.id.listview);
        adapter=
                new ArrayAdapter<String>(ActionBarFragment.this,
                        android.R.layout.simple_list_item_1,
                        words);

        listview.setAdapter(adapter);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        //searchView.setOnQueryTextListener(ActionBarFragment.this);

        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}