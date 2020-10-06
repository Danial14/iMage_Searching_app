package com.example.noman_000.flickr_app;


import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class GetFlickrJsonData {
    private static GetFlickrJsonData getFlickrJsonData;
    private List<Photo> photoList;
    private NotifyEventHandler onJsonParsingCompleteListener;
    private DownloadStatus status;

    private GetFlickrJsonData(){
    }
    public static GetFlickrJsonData getFlickrJson(String baseUrl, boolean tagMode, String... tags){
        if(getFlickrJsonData == null){
            getFlickrJsonData = new GetFlickrJsonData();
        }
        getFlickrJsonData.photoList = new ArrayList<>();
        NotifyEventHandler notifyUser = new NotifyEventHandler(){
            @Override
            public void downloadStatusAndStartParsingJson(String downloadedData, DownloadStatus status) {
                if(status == DownloadStatus.DOWNLOAD_SUCCESSFUL) {
                    getFlickrJsonData.parseJSON(downloadedData);
                }
                else{
                    Log.e(TAG, "downloadStatusAndStartParsingJson: " + status.toString());
                }
            }
        };
        String destinationUrl = getFlickrJsonData.parseUri(baseUrl, tagMode, tags);
        new GetRawData(notifyUser).execute(destinationUrl);
        return getFlickrJsonData;
    }
    public void setOnJsonParsingCompleteListener(NotifyEventHandler onJsonParsingCompleteListener){
        this.onJsonParsingCompleteListener = onJsonParsingCompleteListener;
    }
    private String parseUri(String baseUri, boolean tagMode, String... tags){
        StringBuilder teMpTags = new StringBuilder();
        for(int i = 0; i < tags.length - 1; i++){
            teMpTags.append(tags[i]).append(",");
        }
        teMpTags.append(tags[tags.length - 1]);
        Uri uri = Uri.parse(baseUri);
        Log.d(TAG, "parseUri: " + uri.toString());
        Uri.Builder builder = uri.buildUpon().
                appendQueryParameter("tags", teMpTags.toString()).
                appendQueryParameter("tagmode", tagMode ? "any" : "all").
                appendQueryParameter("format", "json").
                appendQueryParameter("nojsoncallback", "1");

        Log.d(TAG, "parseUri: " + builder.build().toString());
        return builder.build().toString();
    }
    private void parseJSON(String downloadedData){
        try{
            JSONObject jsonObject = new JSONObject(downloadedData);
            JSONArray items = jsonObject.getJSONArray("items");
            String title;
            String author;
            String authorId;
            String link;
            String iMageUrl;
            String tags;

            for(int i = 0; i < items.length(); i++){
                jsonObject = items.getJSONObject(i);
                title = jsonObject.getString("title");
                author = jsonObject.getString("author");
                authorId = jsonObject.getString("author_id");
                iMageUrl = jsonObject.getJSONObject("media").getString("m");
                link = iMageUrl.replaceFirst("_m", "_b");
                tags = jsonObject.getString("tags");
                Photo photo = new Photo(title, author, authorId, link, iMageUrl, tags);
                photoList.add(i, photo);
            }
            status = DownloadStatus.DOWNLOAD_SUCCESSFUL;
        }
        catch(JSONException ex){
            status = DownloadStatus.DOWNLOAD_FAIL;
            Log.e(TAG, "parseJSON: error in parsing json " + ex.getMessage());
        }
        if(onJsonParsingCompleteListener != null) {
            onJsonParsingCompleteListener.jsonParsingCoMpleted(photoList, status);
        }
    }
}
