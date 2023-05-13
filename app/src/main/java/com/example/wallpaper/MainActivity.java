package com.example.wallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wallpaper.Adapters.CuratedAdapters;
import com.example.wallpaper.Listeners.CuratedResponseListener;
import com.example.wallpaper.Listeners.OnRecyclerClickListener;
import com.example.wallpaper.Models.CuratedApiResponse;
import com.example.wallpaper.Models.Photo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnRecyclerClickListener {

    RecyclerView recyclerView_home;
    CuratedAdapters adapter;
    ProgressDialog dialog;
    RequestManager manager;
    FloatingActionButton fab_next, fab_prev;
    int page;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab_next = findViewById(R.id.fab_next);
        fab_prev =findViewById(R.id.fab_prev);
        
        dialog= new ProgressDialog(this);
        dialog.setTitle("loading...");

        manager = new RequestManager(this);
        manager.getCuratedWallpapers(listener,"1");

        fab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String next_page = String.valueOf(page+1);
                manager.getCuratedWallpapers(listener, next_page);
                dialog.show();

            }
        });
        fab_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page>1){
                    String prev_page = String.valueOf(page-1);
                    manager.getCuratedWallpapers(listener, prev_page);
                    dialog.show();
                }
            }
        });
    }
     
    private  final CuratedResponseListener listener = new CuratedResponseListener() {
        @Override
        public void onFetch(CuratedApiResponse response, String message) {
            if (response.getPhotos().isEmpty()){
                Toast.makeText(MainActivity.this, "No Image Found!!", Toast.LENGTH_SHORT).show();
                return;
            }
            page = response.getPage();
            showData(response.getPhotos());
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

        }
    };

    private void showData(List<Photo> photos) {
        recyclerView_home = findViewById(R.id.recycler_home);
        recyclerView_home.setHasFixedSize(true);
        recyclerView_home.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new CuratedAdapters(MainActivity.this,photos,this);
        recyclerView_home.setAdapter(adapter);
    }

    @Override
    public void onClick(Photo photo) {
        Toast.makeText(MainActivity.this, photo.getPhotographer(), Toast.LENGTH_SHORT).show();
        
    }
}