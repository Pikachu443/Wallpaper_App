package com.example.root.wallpaperapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;

import com.example.root.wallpaperapp.Adapters.wallpaperadapter;
import com.example.root.wallpaperapp.R;
import com.example.root.wallpaperapp.models.wallpaper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WallpaperActivity extends AppCompatActivity {


    List<wallpaper> wallpaperList;
    List<wallpaper> wallpaperfavList;

    RecyclerView recyclerView;
   wallpaperadapter wallpaperadapter;

   DatabaseReference databaseReference,dbfav;
   ProgressBar bar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        Intent intent = getIntent();

        final String cat = intent.getStringExtra("category");

        wallpaperfavList = new ArrayList<>();
        wallpaperList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        wallpaperadapter = new wallpaperadapter(this,wallpaperList);

        recyclerView.setAdapter(wallpaperadapter);
        bar = findViewById(R.id.progressbar);



        databaseReference = FirebaseDatabase.getInstance().getReference("Images")
            .child(cat);

        dbfav = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("favourites")
                .child(cat);
        bar.setVisibility(View.VISIBLE);

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            fetchfav(cat);
        }
        else {
            fetchall(cat);
        }


    }

    private void fetchfav(final String category){

       dbfav.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {bar.setVisibility(View.INVISIBLE);


                if (dataSnapshot.exists()){

                    for (DataSnapshot wall : dataSnapshot.getChildren()){

                        String id = wall.getKey();
                        String title = wall.child("title").getValue(String.class);
                        String Url = wall.child("Url").getValue(String.class);

                        wallpaper w = new wallpaper(id,Url,title,category);
                        wallpaperfavList.add(w);
                    }
                }

            fetchall(category);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
    private void fetchall(final String category){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {bar.setVisibility(View.INVISIBLE);


                if (dataSnapshot.exists()){

                    for (DataSnapshot wall : dataSnapshot.getChildren()){

                        String id = wall.getKey();
                        String title = wall.child("title").getValue(String.class);
                        String Url = wall.child("Url").getValue(String.class);

                        wallpaper w = new wallpaper(id,Url,title,category);

                        if (isFav(w)){
                            w.isFav = true;
                        }

                        wallpaperList.add(w);
                    }
                    wallpaperadapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private  boolean isFav(wallpaper w){

        for (wallpaper r : wallpaperfavList){
            if (r.id.equals(w.id)){
                return true;
            }

        }
        return false;
    }
}
