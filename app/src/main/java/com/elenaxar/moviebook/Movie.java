package com.elenaxar.moviebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

public class Movie {
    private int id;
    private String title;
    private String dateCreated;
    private String comments;
    private String watchlist;
    private String watched;
    private float rate;
    private String dateWatched;

    public Movie(){

    }

    /**
     * find the movie in the database with the id,then construct the object
     * @param id
     * @param context
     */
    public Movie(int id, Context context){
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor cursor = database.query(SQLiteHelper.TABLE_MOVIES, SQLiteHelper.TABLE_MOVIES_COLUMNS, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        cursor.moveToFirst();

        this.id = id;
        this.title = cursor.getString(1);
        this.dateCreated = cursor.getString(2);
        this.comments = cursor.getString(3);
        this.watchlist = cursor.getString(4);
        this.watched = cursor.getString(5);
        this.rate = cursor.getFloat(6);
        this.dateWatched = cursor.getString(7);
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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(String watchlist) {
        this.watchlist = watchlist;
    }

    public String getWatched() {
        return watched;
    }

    public void setWatched(String watched) {
        this.watched = watched;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getDateWatched(){
        return this.dateWatched;
    }

    public void setDateWatched(String dateWatched){
        this.dateWatched = dateWatched;
    }

    /**
     * search in database (watchlist = true) and get all movies in watchlist
     * @param context
     * @return an arraylist with the movies
     */
    public static ArrayList<Movie> getAllWatchlist(Context context){
        ArrayList<Movie> movies = new ArrayList<>();
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor cursor = database.query(SQLiteHelper.TABLE_MOVIES, SQLiteHelper.TABLE_MOVIES_COLUMNS, "watchlist=?", new String[]{"true"}, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Movie movie = new Movie();
            movie.setId(cursor.getInt(0));
            movie.setTitle(cursor.getString(1));
            movie.setDateCreated(cursor.getString(2));
            movie.setComments(cursor.getString(3));
            movie.setWatchlist(cursor.getString(4));
            movie.setWatched(cursor.getString(5));
            movie.setRate(cursor.getFloat(6));
            movie.setDateWatched(cursor.getString(7));

            movies.add(movie);
            cursor.moveToNext();
        }

        return movies;
    }

    /**
     * search in database (watched = true) and get all movies in watchlist
     * @param context
     * @return an arraylist with the movies
     */
    public static ArrayList<Movie> getAllMoviesWatched(Context context){
        ArrayList<Movie> movies = new ArrayList<>();
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor cursor = database.query(SQLiteHelper.TABLE_MOVIES, SQLiteHelper.TABLE_MOVIES_COLUMNS, "watched=?", new String[]{"true"}, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Movie movie = new Movie();
            movie.setId(cursor.getInt(0));
            movie.setTitle(cursor.getString(1));
            movie.setDateCreated(cursor.getString(2));
            movie.setComments(cursor.getString(3));
            movie.setWatchlist(cursor.getString(4));
            movie.setWatched(cursor.getString(5));
            movie.setRate(cursor.getFloat(6));
            movie.setDateWatched(cursor.getString(7));

            movies.add(movie);
            cursor.moveToNext();
        }

        return movies;
    }

    /**
     * insert movie in the database only if title and one checkbox (watched, watchlist) are not null
     * if they are null show a toast message to the user
     * after the insert search for the movie with its title and get the id
     * insert all movie's categories in table movie_categories
     * @param context
     * @param categories arraylist with ids of movies categories
     * @return true if insert was successful, else false
     */
    public boolean save(Context context, ArrayList<Integer> categories){
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SQLiteHelper.MOVIES_COLUMN_TITLE, this.title);
        values.put(SQLiteHelper.MOVIES_COLUMN_WATCHLIST, this.watchlist);
        values.put(SQLiteHelper.MOVIES_COLUMN_WATCHED, this.watched);

        try{
            database.insertOrThrow(SQLiteHelper.TABLE_MOVIES, null, values);
            Cursor cursor = database.query(SQLiteHelper.TABLE_MOVIES, SQLiteHelper.TABLE_MOVIES_COLUMNS, SQLiteHelper.MOVIES_COLUMN_TITLE+"=?", new String[] {this.title}, null, null, null);
            cursor.moveToFirst();
            int movieId = cursor.getInt(0);

            ContentValues values2 = new ContentValues();
            for(int categoryId : categories){
                if(categoryId != -1 && categoryId != 0){
                    values2.put(SQLiteHelper.MOVIE_CATEGORIES_COLUMN_MOVIE_ID, movieId);
                    values2.put(SQLiteHelper.MOVIE_CATEGORIES_COLUMN_CATEGORY_ID, categoryId);
                    database.insert(SQLiteHelper.TABLE_MOVIE_CATEGORIES, null, values2);
                }
            }

            return true;
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
            Toast.makeText(context, "Δεν έχουν συμπληρωθεί τα απαραίτητα στοιχεία", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    /**
     * update movie's data in the database
     * then delete all movies categories from table movie_categories and insert the new ones
     * @param context
     * @param id the id of the movie
     * @param categories arraylist with ids of movie categories
     */
    public void update(Context context, int id, ArrayList<Integer> categories){
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.MOVIES_COLUMN_TITLE, this.title);
        values.put(SQLiteHelper.MOVIES_COLUMN_WATCHED, this.watched);
        values.put(SQLiteHelper.MOVIES_COLUMN_WATCHLIST, this.watchlist);
        values.put(SQLiteHelper.MOVIES_COLUMN_RATE, this.rate);
        values.put(SQLiteHelper.MOVIES_COLUMN_DATE_WATCHED, this.dateWatched);
        values.put(SQLiteHelper.MOVIES_COLUMN_COMMENTS, this.comments);

        try{
            database.update(SQLiteHelper.TABLE_MOVIES, values, "id=?", new String[]{String.valueOf(id)});
            database.delete(SQLiteHelper.TABLE_MOVIE_CATEGORIES, SQLiteHelper.MOVIE_CATEGORIES_COLUMN_MOVIE_ID +"=?", new String[]{String.valueOf(id)});

            ContentValues values2 = new ContentValues();
            for(int categoryId : categories){
                if(categoryId != -1 && categoryId != 0){
                    values2.put(SQLiteHelper.MOVIE_CATEGORIES_COLUMN_MOVIE_ID, id);
                    values2.put(SQLiteHelper.MOVIE_CATEGORIES_COLUMN_CATEGORY_ID, categoryId);
                    database.insert(SQLiteHelper.TABLE_MOVIE_CATEGORIES, null, values2);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * delete movie from database
     * @param context
     */
    public void delete(Context context){
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        database.delete(SQLiteHelper.TABLE_MOVIES, "id=?",new String[]{String.valueOf(this.id)});
        database.delete(SQLiteHelper.TABLE_MOVIE_CATEGORIES, SQLiteHelper.MOVIE_CATEGORIES_COLUMN_MOVIE_ID +"=?", new String[]{String.valueOf(id)});

    }

    /**
     * find movie's categories from the database
     * @param id movie's id
     * @param context
     * @return arraylist with ids of the categories
     */
    public ArrayList<Integer> getAllMovieCategories(int id, Context context){
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor cursor = database.query(SQLiteHelper.TABLE_MOVIE_CATEGORIES, SQLiteHelper.TABLE_MOVIE_CATEGORIES_COLUMNS, SQLiteHelper.MOVIE_CATEGORIES_COLUMN_MOVIE_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        cursor.moveToFirst();

        ArrayList<Integer> categories = new ArrayList<>();
        while(!cursor.isAfterLast()){
            categories.add(cursor.getInt(1));
            cursor.moveToNext();
        }

        return categories;
    }
}
