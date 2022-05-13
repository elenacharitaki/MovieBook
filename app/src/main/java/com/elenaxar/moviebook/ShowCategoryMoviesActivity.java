package com.elenaxar.moviebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ShowCategoryMoviesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_category_movies);

        int id = getIntent().getExtras().getInt("id");
        Category category = new Category(id, ShowCategoryMoviesActivity.this);
        ArrayList<Movie> movies = category.getCategoryMovies(ShowCategoryMoviesActivity.this);

        RecyclerView recyclerViewMovies = findViewById(R.id.recyclerViewMovies);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewMovies.setLayoutManager(layoutManager);

        MoviesWatchedAdapter adapter = new MoviesWatchedAdapter(ShowCategoryMoviesActivity.this, movies);
        recyclerViewMovies.setAdapter(adapter);
    }
}