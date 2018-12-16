package com.example.root.wallpaperapp.fragments;

import android.content.Context;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.root.wallpaperapp.Adapters.categoriesadapter;
import com.example.root.wallpaperapp.R;
import com.example.root.wallpaperapp.models.Categories;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Locale.Category;

public class fragmenthome extends Fragment {


    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
    private List<Categories> categories;
    private RecyclerView recyclerView;
    private categoriesadapter Categoriesadapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragmenthome,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        categories = new ArrayList<>();
       Categoriesadapter = new categoriesadapter(getActivity(), categories);

        recyclerView.setAdapter(Categoriesadapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Categories");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.INVISIBLE);

                if (dataSnapshot.exists()) {


                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Log.i("MSG","FOUND");

                        String name = d.getKey();
                        String description = d.child("desc").getValue(String.class);
                        String thumbnail = d.child("thumbnailurl").getValue(String.class);

                        Categories category = new Categories(name, description, thumbnail);
                        categories.add(category);

                    }

                    Categoriesadapter.notifyDataSetChanged();
                }
                else {
                    Log.i("MSG","NOT FOUND");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
