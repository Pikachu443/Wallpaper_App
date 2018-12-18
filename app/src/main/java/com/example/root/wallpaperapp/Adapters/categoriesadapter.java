package com.example.root.wallpaperapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.root.wallpaperapp.R;
import com.example.root.wallpaperapp.activities.WallpaperActivity;
import com.example.root.wallpaperapp.models.Categories;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;
import java.util.Locale;

public class categoriesadapter extends RecyclerView.Adapter<categoriesadapter.Categoryviewholder> {


    private Context cntxt;
    private List<Categories> categorylist;
    private InterstitialAd mInterstitialAd;


    public categoriesadapter(Context cntxt, List<Categories> categorylist) {
        this.cntxt = cntxt;
        this.categorylist = categorylist;

        mInterstitialAd = new InterstitialAd(cntxt);
        mInterstitialAd.setAdUnitId("ca-app-pub-7635925033011324/9366991465");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


    }


    @Override
    public Categoryviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(cntxt).inflate(R.layout.recyclerviewcard , viewGroup ,false );
        return  new Categoryviewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Categoryviewholder categoryviewholder, int i) {

        Categories c = categorylist.get(i);
        categoryviewholder.textView.setText(c.name);
        Glide.with(cntxt)
                .load(c.thumbnail)
                .into(categoryviewholder.imageView);


    }

    @Override
    public int getItemCount() {

        return categorylist.size();
    }

    public class Categoryviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        ImageView imageView;


        public Categoryviewholder(@NonNull View itemView) {
            super(itemView);


            textView = itemView.findViewById(R.id.categorytext);
            imageView = itemView.findViewById(R.id.categoryimage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.i("TAG", "The interstitial wasn't loaded yet.");
            }


            int pos =  getAdapterPosition();

           Categories c = categorylist.get(pos);

            Intent intent = new Intent(cntxt ,WallpaperActivity.class);
            intent.putExtra("category", c.name);
            cntxt.startActivity(intent);

        }
    }
}
