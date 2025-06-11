package com.example.artinstitueapiapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
public class ImageActivity extends AppCompatActivity {

    private TextView zoomscale;
    private ImageView logoImageActivityImageActivity;
    private Handler handler;
    private Runnable updateZoomScaleRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        zoomscale=findViewById(R.id.zoomscaletextview);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String artistDisplay = intent.getStringExtra("artist_display");
        String imageUrl = intent.getStringExtra("image_url"); // Get the image URL from Intent
        logoImageActivityImageActivity=findViewById(R.id.logoImageActivity);

        String[] artistData = artistDisplay.split(",", 2);
        String artistName = artistData[0];
        String artistCountryYear = artistData[1];


        ((TextView) findViewById(R.id.artworkTitleImageActivity)).setText(title);
        ((TextView) findViewById(R.id.artistNameImageActivity)).setText(artistName);
        ((TextView) findViewById(R.id.artistCountryYearImageActivity)).setText(artistCountryYear);

        PhotoView photoView = findViewById(R.id.artworkImageViewImageActivity);

        Picasso.get()
                .load(imageUrl)
                .error(R.drawable.not_available)
                .into(photoView);

        handler = new Handler();
        updateZoomScaleRunnable = new Runnable() {
            @Override
            public void run() {
                float scale = photoView.getScale();
                float zoomPercentage = scale * 100;
                zoomscale.setText(String.format("Scale: %.2f%%", zoomPercentage));
                handler.postDelayed(this, 100);
            }
        };
        handler.post(updateZoomScaleRunnable);



        findViewById(R.id.logoImageActivity).setOnClickListener(v -> finish());

        logoImageActivityImageActivity.setOnClickListener(v -> {
            Intent intent1 = new Intent(ImageActivity.this, MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent1);
            finish();
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateZoomScaleRunnable);
    }
}
