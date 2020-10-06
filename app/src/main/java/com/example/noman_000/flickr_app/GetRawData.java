package com.example.noman_000.flickr_app;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus{
    DOWNLOAD_SUCCESSFUL, DOWNLOAD_IN_PROCESS, DOWNLOAD_FAIL, NO_INTERNET_PERMISSION;
}


public class GetRawData extends AsyncTask<String, Void, String> {
    private static final String TAG = "RawData";
    private DownloadStatus downloadStatus;
    private final NotifyUser notifyUser;

    public GetRawData(NotifyUser notifyUser){
        this.notifyUser = notifyUser;
    }
    @Override
    protected String doInBackground(String... strings) {
        return downloadData(strings[0]);
    }

    @Override
    protected void onPostExecute(String downloadedData){
        super.onPostExecute(downloadedData);
        if(notifyUser != null){
            notifyUser.downloadStatusAndStartParsingJson(downloadedData, downloadStatus);
        }
    }
    private String downloadData(String resourceUrl){
        StringBuilder data = null;
        BufferedReader bufferedReader = null;
        try{
            URL url = new URL(resourceUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            data = new StringBuilder();
            downloadStatus = DownloadStatus.DOWNLOAD_IN_PROCESS;

            while((line = bufferedReader.readLine()) != null){
                data.append(line).append("\n");
            }

            downloadStatus = DownloadStatus.DOWNLOAD_SUCCESSFUL;
        }
        catch(MalformedURLException ex){
            downloadStatus = DownloadStatus.DOWNLOAD_FAIL;
            Log.e(TAG, "downloadData: invlaid url" + ex.getMessage());
        }
        catch(IOException ex){
            downloadStatus = DownloadStatus.DOWNLOAD_FAIL;
            Log.e(TAG, "downloadData: soMething went with input / output" + ex.getMessage());
        }
        catch(SecurityException ex){
            downloadStatus = DownloadStatus.NO_INTERNET_PERMISSION;
            Log.e(TAG, "downloadData: Missing internet perMission");
        }
        finally {
            try {
                bufferedReader.close();
            }
            catch(IOException ex){
                Log.e(TAG, "downloadData: soMething went with input / output" + ex.getMessage());
            }
            catch(NullPointerException ex){
                Log.e(TAG, "downloadData " + ex.getMessage());
            }
        }
        if(data != null){
            return data.toString();
        }

        return null;
    }

}
