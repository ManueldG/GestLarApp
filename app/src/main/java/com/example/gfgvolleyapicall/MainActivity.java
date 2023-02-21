package com.example.gfgvolleyapicall;

import static android.content.ContentValues.TAG;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();
    }

    private void getData() {
        // RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        // String Request initialized


        String url = "https://gestionale.manueldellagala.it/api/posts/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {

                    //final TextView Text = (TextView) findViewById(R.id.textView3);
                    //final ImageView View = findViewById(R.id.imageView3);

                    String img;

                    try {
                        JSONArray obj = response.getJSONArray("data");

                        StringBuilder txt;
                        for (int x = 0 ; x < obj.length() ; x++){

                            txt = new StringBuilder("\n");
                            txt.append(obj.getJSONObject(x).getString("title")).append("\n");
                            txt.append(obj.getJSONObject(x).getString("description")).append("\n");

                            img = obj.getJSONObject(x).getString("file");

                            txt.append(obj.getJSONObject(x).getJSONObject("categories").getString("name")).append("\n");

                            for(int y = 0 ; y < obj.getJSONObject(x).getJSONArray("tag").length() ; y++){

                                txt.append(obj.getJSONObject(x).getJSONArray("tag").getJSONObject(y).getString("name")).append("\n");

                            }

                            //Toast.makeText(getApplicationContext(),txt , Toast.LENGTH_SHORT).show();

                            final TextView textView = new TextView(this);
                            textView.setLayoutParams(new LinearLayout.LayoutParams(600, 500));
                            textView.setText(txt.toString());

                            final ImageView imageView = new ImageView(this);
                            imageView.setLayoutParams(new LinearLayout.LayoutParams(600, 300));
                            AsyncTask<String, Void, Bitmap> image = new DownloadImageTask(imageView)
                                    .execute(img);
                            imageView.setImageBitmap( image.get() );

                            LinearLayout linearLayout = findViewById(R.id.linearLayout3);


                            // Add ImageView to LinearLayout
                            if (linearLayout != null) {

                                linearLayout.addView(imageView);
                                linearLayout.addView(textView);
                                //Toast.makeText(getApplicationContext(),(imageView.getId())+"" , Toast.LENGTH_SHORT).show();

                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }, error -> Log.i(TAG, "Error :" + error.toString()));

        mRequestQueue.add(jsonObjectRequest);
    }
}
