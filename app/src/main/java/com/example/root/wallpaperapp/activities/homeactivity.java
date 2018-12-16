package com.example.root.wallpaperapp.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.root.wallpaperapp.R;
import com.example.root.wallpaperapp.fragments.favfragment;
import com.example.root.wallpaperapp.fragments.fragmenthome;
import com.example.root.wallpaperapp.fragments.settingsfragment;

public class homeactivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


    public void displayfrag(Fragment fragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentmain,fragment)
                    .commit();
    }

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        displayfrag(new fragmenthome());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()){

           case R.id.navhome:
               fragment = new fragmenthome();
               break;
           case R.id.navfav:
               fragment = new favfragment();
               break;
           case R.id.navset:
               fragment = new settingsfragment();
               break;
           default:
               fragment = new fragmenthome();

       }
       displayfrag(fragment);
       return  true;
    }
}
