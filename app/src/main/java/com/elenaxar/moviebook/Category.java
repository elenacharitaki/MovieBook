package com.elenaxar.moviebook;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Category {
    private int id;
    private String title;
    private ArrayList<Movie> movies;

    Category(){

    }

    Category(int id, Context context){
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor cursor = database.query(SQLiteHelper.TABLE_CATEGORIES, SQLiteHelper.TABLE_CATEGORIES_COLUMNS, SQLiteHelper.CATEGORIES_COLUMN_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);
        cursor.moveToFirst();

        this.id = id;
        this.title = cursor.getString(1);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    //find the category by its title
    public static int searchCategory(String[] title, Context context){
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor cursor = database.query(SQLiteHelper.TABLE_CATEGORIES, SQLiteHelper.TABLE_CATEGORIES_COLUMNS, "title=?", title, null, null, null);
        cursor.moveToFirst();

        int id = cursor.getInt(0);
        return id;
    }

    // get all categories
    public static ArrayList<Category> getAll(Context context){
        ArrayList<Category> categories = new ArrayList<>();

        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor cursor = database.query(SQLiteHelper.TABLE_CATEGORIES, SQLiteHelper.TABLE_CATEGORIES_COLUMNS, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Category category = new Category();
            category.setId(cursor.getInt(0));
            category.setTitle(cursor.getString(1));

            categories.add(category);
            cursor.moveToNext();
        }
        return categories;
    }

    //get all the movies of a category
    //id = id of the category
    public ArrayList<Movie> getCategoryMovies(Context context) {
        ArrayList<Movie> movies = new ArrayList<>();

        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        //returns all the movies of one category
        Cursor cursor = database.query(SQLiteHelper.TABLE_MOVIE_CATEGORIES, SQLiteHelper.TABLE_MOVIE_CATEGORIES_COLUMNS, SQLiteHelper.MOVIE_CATEGORIES_COLUMN_CATEGORY_ID+"=?", new String[] {String.valueOf(this.id)}, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Movie movie = new Movie(cursor.getInt(0), context);
            if(movie.getWatched().equals("true")){
                movies.add(movie);  // only the movies from list "watched" are displayed in a category
            }
            cursor.moveToNext();
        }

        return movies;
    }

    //get the number of the movies in a category
    public String getMoviesCount(Context context){
        String num = String.valueOf(this.getCategoryMovies(context).size());
        return num;
    }
}