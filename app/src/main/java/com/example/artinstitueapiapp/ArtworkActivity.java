package com.example.artinstitueapiapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Paint;
import com.squareup.picasso.Picasso;

public class ArtworkActivity extends AppCompatActivity {

    private ImageView artworkImage, externalResourceIcon, logoimageartwork;
    private TextView artworkTitle, artworkDate, artistName, artistCountryYears, department, galleryTitle, location, typeMedium, dimensions, creditLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork);


        artworkImage = findViewById(R.id.artwork_image);
        externalResourceIcon = findViewById(R.id.external_resource_icon);
        artworkTitle = findViewById(R.id.artwork_title);
        artworkDate = findViewById(R.id.artwork_date);
        artistName = findViewById(R.id.artist_name);
        artistCountryYears = findViewById(R.id.artist_country_years);
        department = findViewById(R.id.department);
        galleryTitle = findViewById(R.id.gallery_title);
        location = findViewById(R.id.location);
        typeMedium = findViewById(R.id.type_medium);
        dimensions = findViewById(R.id.dimensions);
        creditLine = findViewById(R.id.credit_line);
        logoimageartwork = findViewById(R.id.logoimage);


        Artwork artwork = (Artwork) getIntent().getSerializableExtra("artwork");

        if (artwork != null) {

            artworkTitle.setText(artwork.getTitle());
            artworkDate.setText(artwork.getDateDisplay());
            department.setText(artwork.getDepartment());


            String[] artistParts = artwork.getArtistDisplay().split("\n", 2);


            artistName.setText(artistParts.length > 0 ? artistParts[0] : " ");
            artistCountryYears.setText(artistParts.length > 1 ? artistParts[1] : " ");


            galleryTitle.setText(artwork.getGalleryTitle());


            if (artwork.getGalleryTitle() != null && !artwork.getGalleryTitle().isEmpty()) {
                galleryTitle.setText(artwork.getGalleryTitle());


                if (artwork.getGalleryId() != null && !artwork.getGalleryId().isEmpty()) {

                    galleryTitle.setPaintFlags(galleryTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


                    galleryTitle.setOnClickListener(v -> {
                        String galleryUrl = "https://www.artic.edu/galleries/" + artwork.getGalleryId();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(galleryUrl));
                        startActivity(browserIntent);
                    });
                } else {

                    externalResourceIcon.setVisibility(View.GONE);
                }
            } else {

                galleryTitle.setVisibility(View.GONE);
                externalResourceIcon.setVisibility(View.GONE);
            }


            Picasso.get()
                    .load(artwork.getFullImageUrl())
                    .error(R.drawable.not_available) // Optional error image
                    .into(artworkImage);


            artworkImage.setOnClickListener(v -> {
                Intent intent = new Intent(ArtworkActivity.this, ImageActivity.class);
                intent.putExtra("title", artwork.getTitle()); // Pass the artwork title
                intent.putExtra("artist_display", artwork.getArtistDisplay()); // Pass the artist_display string
                intent.putExtra("image_url", artwork.getFullImageUrl()); // Pass the artwork image URL
                startActivity(intent);
            });


            location.setText(artwork.getPlaceOfOrigin());
            typeMedium.setText(artwork.getArtworkType() + " – " + artwork.getMediumDisplay());
            dimensions.setText(artwork.getDimensions());
            creditLine.setText(artwork.getCreditLine());

        } else {

            artworkTitle.setText("Artwork not found");
        }


        logoimageartwork.setOnClickListener(v -> {
            Intent intent = new Intent(ArtworkActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear back stack
            startActivity(intent);
            finish();
        });
    }
}
