package com.elenaxar.moviebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddNewMovieActivity extends AppCompatActivity {
    EditText editTextMovieTitle;
    CheckBox checkBoxWatchlist;
    CheckBox checkBoxWatched;
    String checkWatchlist;
    String checkWatched;
    Spinner addCategorySpinner1;
    Spinner addCategorySpinner2;
    Spinner addCategorySpinner3;
    int categoryId1;
    int categoryId2;
    int categoryId3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_movie);

        editTextMovieTitle = findViewById(R.id.editTextMovieTitle);
        checkBoxWatchlist = findViewById(R.id.checkBoxWatchlist);
        checkBoxWatched = findViewById(R.id.checkBoxWatched);
        addCategorySpinner1 = findViewById(R.id.addCategorySpinner1);
        addCategorySpinner2 = findViewById(R.id.addCategorySpinner2);

        addCategorySpinner3 = findViewById(R.id.addCategorySpinner3);

        ArrayList<Category> categories = Category.getAll(AddNewMovieActivity.this);
        ArrayList<String> categoryTitles = new ArrayList<>();
        categoryTitles.add(0,"Χωρίς κατηγορία...");
        for(Category category : categories){
            categoryTitles.add(category.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddNewMovieActivity.this, R.layout.category_spinner_item,R.id.spinnerCategoryTitle, categoryTitles);

        addCategorySpinner1.setAdapter(adapter);
        addCategorySpinner2.setAdapter(adapter);
        addCategorySpinner3.setAdapter(adapter);

        ArrayList<Integer> categoriesId = new ArrayList<>(); // arraylist for all the category id's of the movie
        addCategorySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position != 0){
                    categoryId1 = Category.searchCategory(new String[] {categories.get(position-1).getTitle()}, AddNewMovieActivity.this);
                } else {
                    categoryId1 = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        addCategorySpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position != 0){
                    categoryId2 = Category.searchCategory(new String[] {categories.get(position-1).getTitle()}, AddNewMovieActivity.this);
                } else {
                    categoryId2 = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        addCategorySpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position != 0){
                    categoryId3 = Category.searchCategory(new String[] {categories.get(position-1).getTitle()}, AddNewMovieActivity.this);
                } else {
                    categoryId3 = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //save button
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoriesId.add(categoryId1);
                categoriesId.add(categoryId2);
                categoriesId.add(categoryId3);

                Movie movie = new Movie();
                if(editTextMovieTitle.getText().toString().equals("")){
                    movie.setTitle(null); //to throw exception, must be not null
                } else {
                    movie.setTitle(editTextMovieTitle.getText().toString());
                }
                movie.setWatched(checkWatched);
                movie.setWatchlist(checkWatchlist);

                if(movie.save(AddNewMovieActivity.this, categoriesId)){
                    AddNewMovieActivity.this.finish();
                }
            }
        });
    }

    //method for the behaviour of the two checkboxes
    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()){
            case R.id.checkBoxWatchlist:
                if(checked){
                    checkBoxWatched.setChecked(false);
                    checkWatchlist = "true";
                    checkWatched = "false";
                }
                break;
            case R.id.checkBoxWatched:
                if(checked){
                    checkBoxWatchlist.setChecked(false);
                    checkWatchlist = "false";
                    checkWatched = "true";
                }
                break;
        }
    }
}