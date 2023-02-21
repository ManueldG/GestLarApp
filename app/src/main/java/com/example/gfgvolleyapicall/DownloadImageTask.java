package com.example.gfgvolleyapicall;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;


 class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }
     protected Bitmap doInBackground(String... urls) {

        Bitmap mIcon11 = null;
        try {
                if(urls != null){

                        InputStream in = new java.net.URL("https://gestionale.manueldellagala.it/img/" + urls[0]).openStream();

                        mIcon11 = BitmapFactory.decodeStream(in);

                }

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}