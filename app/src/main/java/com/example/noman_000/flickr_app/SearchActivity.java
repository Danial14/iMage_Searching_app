package com.example.noman_000.flickr_app;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class SearchActivity extends BaseActivity {
    private SearchView searchView;
    private static final String TAG = "SearchActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        activateToolBar(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
        searchView.setIconified(false); // opens the SearchView autoMatically
        Log.d(TAG, "onCreateOptionsMenu: " + getComponentName().toString());
        Log.d(TAG, "onCreateOptionsMenu: hint is " + searchView.getQueryHint());
        Log.d(TAG, "onCreateOptionsMenu: searchableInfo is " + searchableInfo.toString());
        Log.d(TAG, "onCreateOptionsMenu: " + searchableInfo.getSuggestIntentData());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit: user query is " + s);
                SharedPreferences sharedPreferences = PreferenceManager.
                        getDefaultSharedPreferences(getApplicationContext());
                sharedPreferences.edit().putString(FLICK_QUERY, s).apply();
                finish();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finish();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
