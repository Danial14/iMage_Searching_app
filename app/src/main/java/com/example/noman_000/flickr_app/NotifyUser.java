package com.example.noman_000.flickr_app;


import java.util.List;

public interface NotifyUser {
    void jsonParsingCoMpleted(List<Photo> photoList, DownloadStatus status);
    void downloadStatusAndStartParsingJson(String downloadedData, DownloadStatus status);
}
