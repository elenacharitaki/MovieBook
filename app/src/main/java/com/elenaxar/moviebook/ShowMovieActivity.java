package com.elenaxar.moviebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShowMovieActivity extends AppCompatActivity {


    TextView textViewShowTitle;
    TextView textViewShowCategory;
    RatingBar showRate;
    TextView textViewShowDateWatched;
    TextView textViewShowComments;
    FloatingActionButton btnEdit;
    public static ShowMovieActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this; //to finish this activity from an other activity

        setContentView(R.layout.activity_show_movie);
        textViewShowTitle = findViewById(R.id.textViewShowTitle);
        textViewShowCategory = findViewById(R.id.textViewShowCategory);
        showRate = findViewById(R.id.showRate);
        textViewShowDateWatched = findViewById(R.id.textViewShowDateWatched);
        textViewShowComments = findViewById(R.id.textViewShowComments);
        btnEdit = findViewById(R.id.floatingBtnEdit);

    }

    @Override
    protected void onStart() {
        super.onStart();

        //get the id, find the movie in the database and load the data
        int id = getIntent().getExtras().getInt("id");
        Movie movie = new Movie(id, ShowMovieActivity.this);

        //all the categories of this movie
        ArrayList<Integer> categories = movie.getAllMovieCategories(id, ShowMovieActivity.this);
        String text = "";
        for(Integer categoryId : categories){
            Category category = new Category(categoryId,  ShowMovieActivity.this);
            text += category.getTitle()+"\n";
        }
        textViewShowTitle.setText(movie.getTitle());
        textViewShowCategory.setText(text);
        showRate.setRating(movie.getRate());
        if(movie.getDateWatched() != null && !movie.getDateWatched().equals("")){
            textViewShowDateWatched.setText(movie.getDateWatched());
        } else {
            textViewShowDateWatched.setText("Χωρίς ημερομηνία");
        }

        if(movie.getComments() != null && !movie.getComments().equals("")){
            textViewShowComments.setText(movie.getComments());
        } else {
            textViewShowComments.setText("Χωρίς σχόλια");
        }

        //edit button
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowMovieActivity.this, EditMovieActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }
}