package com.example.wallpaper;

import android.content.Context;
import android.widget.Toast;

import com.example.wallpaper.Listeners.CuratedResponseListener;
import com.example.wallpaper.Models.CuratedApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit =new Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getCuratedWallpapers(CuratedResponseListener listener, String page){
        CallWallpaperList callWallpaperList=retrofit.create(CallWallpaperList.class);
        Call<CuratedApiResponse> call=callWallpaperList.getWallpapers(page, "20");

        call.enqueue(new Callback<CuratedApiResponse>() {
            @Override
            public void onResponse(Call<CuratedApiResponse> call, Response<CuratedApiResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context, "An Error Occured!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<CuratedApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    private interface CallWallpaperList{
        @Headers({
                "Accept: application/json",
                "Authorization: QAcUbrzEVB1ZeHoCCQtGZj0RqNiTGSFU8BCdTygkGEYZF1oPWwfwVUr9"
        })
        @GET("curated/")
        Call<CuratedApiResponse> getWallpapers(
                @Query("page")String page,
                @Query("per_page")String per_page
        );
    }
}
