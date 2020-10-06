package com.example.noman_000.flickr_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends BaseActivity implements RecyclerIteMClickListener.onRecyclerClickListener{

    private static final String TAG = "MainActivity";

    public static final String FLICKR_BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
    private RecyclerView recyclerView;
    private FlickrRecyclerViewAdapter flickrRecyclerViewAdapter;

    public MainActivity(){
        Log.d(TAG, "MainActivity: constructor");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: starts");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        flickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(this);
        recyclerView.setAdapter(flickrRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recyclerView.addOnItemTouchListener(new RecyclerIteMClickListener(this, recyclerView, this));
        activateToolBar(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.
                getDefaultSharedPreferences(getApplicationContext());

        String result = preferences.getString(FLICK_QUERY, "");
        if(result.length() > 0){
            GetFlickrJsonData getFlickrJsonData = GetFlickrJsonData.
                    getFlickrJson(FLICKR_BASE_URL, true, result);
            getFlickrJsonData.setOnJsonParsingCompleteListener(new NotifyEventHandler(){
                @Override
                public void jsonParsingCoMpleted(List<Photo> photoList, DownloadStatus status) {
                    if(status == DownloadStatus.DOWNLOAD_SUCCESSFUL){
                        flickrRecyclerViewAdapter.loadNewData(photoList);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "probleM", Toast.LENGTH_LONG).
                                show();
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Search_photo) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "onIteMClick position " + position, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        intent.putExtra(PHOTO_TRANSFER, flickrRecyclerViewAdapter.getPhoto(position));
        intent.putExtra("NUMBER", "12");
       // getFlickrJsonData.cancelDownloading(true);
        startActivity(intent);
    }
}
