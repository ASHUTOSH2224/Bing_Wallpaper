package com.example.wallpaper.Listeners;

import com.example.wallpaper.Models.SearchApiResponse;

public interface SearchResponseListener {
    void onFetch(SearchApiResponse response,String message);
    void onError(String message);
}
