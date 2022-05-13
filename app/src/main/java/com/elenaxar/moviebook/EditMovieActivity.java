package com.elenaxar.moviebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EditMovieActivity extends AppCompatActivity {
    EditText editTextTitle;
    CheckBox checkBoxToWatchedMovies;
    Spinner categorySpinner1;
    Spinner categorySpinner2;
    Spinner categorySpinner3;
    RatingBar ratingBar;
    TextView watchedAt;
    EditText editTextDateWatched;
    EditText editTextComments;
    Button btnUpdate;
    LinearLayout linearLayoutRate;
    FloatingActionButton btnDelete;
    TextView textViewComments;

    Movie movie;
    Integer categoryId1;
    Integer categoryId2;
    Integer categoryId3;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        editTextTitle = findViewById(R.id.editTextShowTitle);
        checkBoxToWatchedMovies = findViewById(R.id.checkBoxToWatchedMovies);
        categorySpinner1 = findViewById(R.id.categorySpinner1);
        categorySpinner2 = findViewById(R.id.categorySpinner2);
        categorySpinner3 = findViewById(R.id.categorySpinner3);
        ratingBar = findViewById(R.id.ratingBar);
        watchedAt = findViewById(R.id.textViewWatchedAt);
        editTextDateWatched = findViewById(R.id.editTextDateWatched);
        editTextComments = findViewById(R.id.editTextComments);
        btnUpdate = findViewById(R.id.btnUpdate);
        linearLayoutRate = findViewById(R.id.linearLayoutRate);
        btnDelete = findViewById(R.id.btnDelete);
        textViewComments = findViewById(R.id.textViewComments);

        //load the movie from the database
        int id = getIntent().getExtras().getInt("id");

        movie = new Movie(id, EditMovieActivity.this);
        if(movie.getWatched().equals("true")){
            checkBoxToWatchedMovies.setChecked(true);
        }

        editTextTitle.setText(movie.getTitle());

        if(movie.getComments() != null && !movie.getComments().equals("")){
            editTextComments.setText(movie.getComments());
        } else {
            editTextComments.setHint("Χωρίς σχόλια");
        }

        ratingBar.setRating(movie.getRate());

        if(movie.getDateWatched() != null && !movie.getDateWatched().equals("")){
            editTextDateWatched.setText(movie.getDateWatched());
        } else {
            editTextDateWatched.setHint("Χωρίς ημερομηνία");
        }


        isWatched(checkBoxToWatchedMovies);

        //3 spinners with categories to choose from
        ArrayList<Category> categories = Category.getAll(EditMovieActivity.this);
        ArrayList<String> categoryTitles = new ArrayList<>();
        categoryTitles.add(0,"Επέλεξε κατηγορία...");
        for(Category category : categories){
            categoryTitles.add(category.getTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(EditMovieActivity.this, R.layout.category_spinner_item,R.id.spinnerCategoryTitle, categoryTitles);
        categorySpinner1.setAdapter(adapter);
        categorySpinner2.setAdapter(adapter);
        categorySpinner3.setAdapter(adapter);

        ArrayList<Integer> movieCategories = movie.getAllMovieCategories(id, EditMovieActivity.this);
        if(movieCategories.size() > 0){
            categorySpinner1.setSelection(movieCategories.get(0));
        }
        if(movieCategories.size() > 1){
            categorySpinner2.setSelection(movieCategories.get(1));
        }
        if(movieCategories.size() > 2){
            categorySpinner3.setSelection(movieCategories.get(2));
        }

        ArrayList<Integer> categoriesId = new ArrayList<>(); //arraylist for all the category id's of the movie
        categorySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position != 0){
                    categoryId1 = Category.searchCategory(new String[] {categories.get(position-1).getTitle()}, EditMovieActivity.this);
                } else {
                    categoryId1 = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        categorySpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position != 0){
                    categoryId2 = Category.searchCategory(new String[] {categories.get(position-1).getTitle()}, EditMovieActivity.this);
                } else {
                    categoryId2 = -1;
                }
                            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        categorySpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position != 0){
                    categoryId3 = Category.searchCategory(new String[] {categories.get(position-1).getTitle()}, EditMovieActivity.this);
                } else {
                    categoryId3 = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //datepicker
        editTextDateWatched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog= new AlertDialog.Builder(EditMovieActivity.this);
                View calendarView = getLayoutInflater().inflate(R.layout.date_picker_alert_dialog, null);
                dialog.setView(calendarView);
                dialog.setTitle("Επιλέξτε ημερομηνία προβολής");

                CalendarView calendar = (CalendarView)calendarView.findViewById(R.id.calendar);
                calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {
                        String day = String.valueOf(d);
                        String month = String.valueOf(m);
                        String year = String.valueOf(y);
                        changeDate(day, month, year);
                    }
                });

                dialog.setPositiveButton("ΟΚ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editTextDateWatched.setText(date);
                    }
                });
                dialog.setNegativeButton("ΑΚΥΡΟ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });

        // save changes
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoriesId.add(categoryId1);
                categoriesId.add(categoryId2);
                categoriesId.add(categoryId3);
                if(checkBoxToWatchedMovies.isChecked()){
                    movie.setTitle(editTextTitle.getText().toString());
                    movie.setWatched("true");
                    movie.setWatchlist("false");
                    movie.setComments(editTextComments.getText().toString());
                    movie.setRate(ratingBar.getRating());
                    movie.setDateWatched(editTextDateWatched.getText().toString());
                    movie.update(EditMovieActivity.this, id, categoriesId);
                } else {
                    movie.setTitle(editTextTitle.getText().toString());
                    movie.setWatched("false");
                    movie.setWatchlist("true");
                    movie.setComments(null);
                    movie.setRate(0.0f);
                    movie.setDateWatched(null);
                    movie.update(EditMovieActivity.this, id, categoriesId);
                }

                EditMovieActivity.this.finish();
            }
        });

        //delete button
        //deletes the movie from the database
        //alert dialog before delete
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(EditMovieActivity.this)
                        .setTitle("Διαγραφή")
                        .setMessage("Θέλετε σίγουρα να διαγράψετε αυτήν την ταινία;")
                .setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(ShowMovieActivity.instance != null){
                            try{
                                ShowMovieActivity.instance.finish();
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        EditMovieActivity.this.finish();
                       movie.delete(EditMovieActivity.this);
                    }
                })
                .setNegativeButton("Όχι", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

            }
        });
    }

    public void isWatched(View view){
        boolean checked = ((CheckBox) view).isChecked();

        if(checked){
            linearLayoutRate.setVisibility(View.VISIBLE);
            watchedAt.setVisibility(View.VISIBLE);
            editTextDateWatched.setVisibility(View.VISIBLE);
            editTextComments.setVisibility(View.VISIBLE);
            textViewComments.setVisibility(View.VISIBLE);

        } else {
            linearLayoutRate.setVisibility(View.GONE);
            watchedAt.setVisibility(View.INVISIBLE);
            editTextDateWatched.setVisibility(View.INVISIBLE);
            editTextComments.setVisibility(View.INVISIBLE);
            textViewComments.setVisibility(View.INVISIBLE);
        }

    }

    public void changeDate(String day, String month, String year){
        date = day + "/" + month + "/" + year;
    }
}