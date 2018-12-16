package com.example.root.wallpaperapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class favfragment extends Fragment {

    List<wallpaper> favwalls;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    wallpaperadapter wallpaperadapter;
    DatabaseReference dbfav;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.favfragmentlayout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        favwalls = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerview);
        progressBar = view.findViewById(R.id.progressbar);



        wallpaperadapter = new wallpaperadapter(getActivity(),favwalls);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(wallpaperadapter);


        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentmain,new settingsfragment())
                    .commit();
            return;
        }


        dbfav = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("favourites");

        progressBar.setVisibility(View.VISIBLE);

        dbfav.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);

                for (DataSnapshot cat : dataSnapshot.getChildren()){
                    for (DataSnapshot wall : cat.getChildren()){

                        String id = wall.getKey();
                        String title = wall.child("title").getValue(String.class);
                        String Url = wall.child("Url").getValue(String.class);

                        wallpaper w = new wallpaper(id,Url,title,cat.getKey());

                            w.isFav = true;

                       favwalls.add(w);

                    }
                }
                wallpaperadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
