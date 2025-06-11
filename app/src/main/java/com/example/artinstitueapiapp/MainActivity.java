package com.example.artinstitueapiapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText search_edittext;
    private Button search_button, random_button;
    private RecyclerView recycler_view;
    private ProgressBar progress_bar;
    private ArrayList<Artwork> artworkList;
    private ArtworkAdapter adapter;
    private TextView copyrightNotice;
    private ImageView crossImageView;
    private static final int MAX_PAGES = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        search_edittext = findViewById(R.id.searchForEditText);
        search_button = findViewById(R.id.searchButton);
        random_button = findViewById(R.id.randomButton);
        recycler_view = findViewById(R.id.recyclerView);
        progress_bar = findViewById(R.id.progressBar);
        copyrightNotice = findViewById(R.id.copyrighttextView2);
        crossImageView = findViewById(R.id.imageViewCross);


        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        artworkList = new ArrayList<>();
        adapter = new ArtworkAdapter(artworkList, this::onArtworkSelected);
        recycler_view.setAdapter(adapter);


        recycler_view.setBackgroundResource(R.drawable.bwlions);


        search_button.setOnClickListener(v -> performSearch());


        copyrightNotice.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CopyrightActivity.class);
            startActivity(intent);
        });


        random_button.setOnClickListener(v -> fetchRandomArtwork());
        crossImageView.setOnClickListener(v -> search_edittext.setText(""));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.logo)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void fetchRandomArtwork() {
        progress_bar.setVisibility(View.VISIBLE);
        Random random = new Random();
        int randomPage = random.nextInt(MAX_PAGES) + 1;


        String url = "https://api.artic.edu/api/v1/artworks/search?q=&limit=15&page=" + randomPage + "&fields=title,date_display,artist_display,medium_display,artwork_type_title,image_id,dimensions,department_title,credit_line,place_of_origin,gallery_title,gallery_id,id";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray data = response.getJSONArray("data");
                if (data.length() > 0) {
                    progress_bar.setVisibility(View.GONE);
                    int randomIndex = random.nextInt(data.length());
                    JSONObject randomArtwork = data.getJSONObject(randomIndex);

                    Artwork artwork = new Artwork(
                            randomArtwork.optString("title", " "),
                            randomArtwork.optString("date_display", " "),
                            randomArtwork.optString("artist_display", " "),
                            randomArtwork.optString("medium_display", " "),
                            randomArtwork.optString("artwork_type_title", " "),
                            randomArtwork.optString("image_id", " "),
                            randomArtwork.optString("dimensions", " "),
                            randomArtwork.optString("department_title", " "),
                            randomArtwork.optString("credit_line", " "),
                            randomArtwork.optString("place_of_origin", " "),
                            randomArtwork.optString("gallery_title", " "),
                            randomArtwork.optString("gallery_id", " ")
                    );

                    artworkList.clear();
                    artworkList.add(artwork);
                    adapter.notifyDataSetChanged();

                    if (!artworkList.isEmpty()) {
                        recycler_view.setBackground(null);
                    } else {
                        recycler_view.setBackgroundResource(R.drawable.bwlions);
                    }
                } else {
                    Toast.makeText(this, "No artworks found on this page. Try again!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error fetching random artwork", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            if (!isNetworkConnected()) {
                showDialog("No Network Connection", "No network connection present - cannot contact Art Institute API server.");
            } else {
                Toast.makeText(this, "Error fetching random artwork", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(request);
    }

    private void performSearch() {

        progress_bar.setVisibility(View.VISIBLE);

        String query = search_edittext.getText().toString().trim();
        if (query.isEmpty() || query.length() < 3) {

            progress_bar.setVisibility(View.GONE);
            showDialog("Search String too short", "Please try a longer search string.");
            return;
        }

        String url = "https://api.artic.edu/api/v1/artworks/search?q=" + query + "&limit=15&page=1&fields=title,date_display,artist_display,medium_display,artwork_type_title,image_id,dimensions,department_title,credit_line,place_of_origin,gallery_title,gallery_id,id,api_link";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            progress_bar.setVisibility(View.GONE);

            try {
                JSONArray data = response.getJSONArray("data");
                if (data.length() == 0) {
                    showDialog("No search Results Found", "No results found for '" + query + "'. Please try another search string.");
                } else {
                    parseArtworkResponse(response);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        }, error -> {

            progress_bar.setVisibility(View.GONE);

            if (!isNetworkConnected()) {
                showDialog("No Network Connection", "No network connection present - cannot contact Art Institute API server.");
            } else {
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(request);
    }

    private void parseArtworkResponse(JSONObject response) {
        artworkList.clear();
        try {
            JSONArray data = response.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject item = data.getJSONObject(i);
                Artwork artwork = new Artwork(
                        item.optString("title", " "),
                        item.optString("date_display", " "),
                        item.optString("artist_display", " "),
                        item.optString("medium_display", " "),
                        item.optString("artwork_type_title", " "),
                        item.optString("image_id", " "),
                        item.optString("dimensions", " "),
                        item.optString("department_title", " "),
                        item.optString("credit_line", " "),
                        item.optString("place_of_origin", " "),
                        item.optString("gallery_title", " "),
                        item.optString("gallery_id", " ")
                );
                artworkList.add(artwork);
            }
            if (!artworkList.isEmpty()) {
                recycler_view.setBackground(null);
            } else {
                recycler_view.setBackgroundResource(R.drawable.bwlions);
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onArtworkSelected(Artwork artwork) {
        Intent intent = new Intent(MainActivity.this, ArtworkActivity.class);
        intent.putExtra("artwork", artwork);
        startActivity(intent);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
