package com.example.wallpaper.Listeners;

import com.example.wallpaper.Models.CuratedApiResponse;

public interface CuratedResponseListener {
    void onFetch(CuratedApiResponse response, String message);
    void onError(String message);
}
