package com.example.root.wallpaperapp.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.root.wallpaperapp.BuildConfig;
import com.example.root.wallpaperapp.R;
import com.example.root.wallpaperapp.models.Categories;
import com.example.root.wallpaperapp.models.wallpaper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class wallpaperadapter extends RecyclerView.Adapter<wallpaperadapter.Categoryviewholder> {


    private Context cntxt;
    private List<wallpaper> wallpaperList;


    public wallpaperadapter(Context cntxt, List<wallpaper> wallpaperList) {
        this.cntxt = cntxt;
        this.wallpaperList = wallpaperList;

    }


    @Override
    public Categoryviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(cntxt).inflate(R.layout.recyclerwallpaper , viewGroup ,false );
        return  new Categoryviewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Categoryviewholder categoryviewholder, int i) {

        wallpaper c = wallpaperList.get(i);
        categoryviewholder.textView.setText(c.title);
        Glide.with(cntxt)
                .load(c.Url)
                .into(categoryviewholder.imageView);

        if (c.isFav){
            categoryviewholder.checkBox.setChecked(true );
        }

    }

    @Override
    public int getItemCount() {

        return wallpaperList.size();
    }

    public class Categoryviewholder extends RecyclerView.ViewHolder implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {

        TextView textView;
        ImageView imageView;

        CheckBox checkBox;
        ImageButton share,download;


        public Categoryviewholder(@NonNull View itemView) {
            super(itemView);


            textView = itemView.findViewById(R.id.text);

            checkBox = itemView.findViewById(R.id.fav);
            share = itemView.findViewById(R.id.share);
            download = itemView.findViewById(R.id.download);
            imageView = itemView.findViewById(R.id.image);
            
            checkBox.setOnCheckedChangeListener(this);
            share.setOnClickListener(this);
            download.setOnClickListener(this);
            
        }

        @Override
        public void onClick(View v) {

             switch (v.getId()){
                 case R.id.share:
                     sharewall(wallpaperList.get(getAdapterPosition()));
                     break;

                 case R.id.download:
                     downloadwall(wallpaperList.get(getAdapterPosition()));
                     break;
             }

        }


        private void downloadwall(final wallpaper w){

            ((Activity)cntxt).findViewById(R.id.progressbar).setVisibility(View.VISIBLE);
            Glide.with(cntxt)
                    .asBitmap()
                    .load(w.Url)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            ((Activity)cntxt).findViewById(R.id.progressbar).setVisibility(View.GONE);
                            Log.i("MSG","FUCK");
                            Intent intent = new Intent(Intent.ACTION_VIEW);

                            Uri uri = savewall(resource,w.id);

                            if (uri != null){
                                intent.setDataAndType(uri,"image/*");
                                cntxt.startActivity(Intent.createChooser(intent,"select"));

                            }


                        }
                    });

        }

        private Uri savewall(Bitmap bitmap ,String id){

            if (ContextCompat.checkSelfPermission(cntxt,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) cntxt,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);


                    Uri uri = Uri.fromParts("package",cntxt.getPackageName(),null);

                    intent.setData(uri);
                    cntxt.startActivity(intent);

                }else {

                    ActivityCompat.requestPermissions((Activity) cntxt,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);

                }
                return null;

            }

            File file = new File(Environment.getExternalStorageDirectory().toString()+"/wallpapers");
            file.mkdir();

            File file1 = new File(file,id + ".jpg");
            try {
                FileOutputStream out = new FileOutputStream(file1);

                bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
                out.flush();
                out.close();

                  } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return FileProvider.getUriForFile(cntxt, cntxt.getApplicationContext().getPackageName()+".provider",file);

        }

        private void sharewall(wallpaper w){

            ((Activity)cntxt).findViewById(R.id.progressbar).setVisibility(View.VISIBLE);
            Glide.with(cntxt)
                    .asBitmap()
                    .load(w.Url)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            ((Activity)cntxt).findViewById(R.id.progressbar).setVisibility(View.GONE);
                            Log.i("MSG","FUCK");
                                 Intent intent = new Intent(Intent.ACTION_SEND);

                                intent.putExtra(Intent.EXTRA_STREAM,getLocalBitmapUri(resource));
                                intent.setType("image/*");
                                cntxt.startActivity(Intent.createChooser(intent,"select"));


                        }
                    });

        }
        public Uri getLocalBitmapUri(Bitmap bmp) {
            Uri bmpUri = null;
            try {
                File file =  new File(cntxt.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "image_" + System.currentTimeMillis() + ".png");
                FileOutputStream out = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.close();
                bmpUri = FileProvider.getUriForFile(cntxt, cntxt.getApplicationContext().getPackageName()+".provider",file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmpUri;
        }


        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        
            if (FirebaseAuth.getInstance().getCurrentUser() == null){
                Toast.makeText(cntxt, "Login First!", Toast.LENGTH_SHORT).show();
                buttonView.setChecked(false);
                return;
            }



            int position = getAdapterPosition();
            wallpaper w = wallpaperList.get(position);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("favourites")
                    .child(w.category);

            if (isChecked){

               reference.child(w.id).setValue(w);

            }else {
                reference.child(w.id).setValue(null);

            }
            
            
        }
    }
}
