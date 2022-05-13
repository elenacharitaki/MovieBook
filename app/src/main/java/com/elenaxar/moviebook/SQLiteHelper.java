package com.elenaxar.moviebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movies_db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_MOVIES = "movies";
    public static final String MOVIES_COLUMN_ID = "id";
    public static final String MOVIES_COLUMN_TITLE = "title";
    public static final String MOVIES_COLUMN_DATE_CREATED = "date_created";
    public static final String MOVIES_COLUMN_COMMENTS = "comments";
    public static final String MOVIES_COLUMN_WATCHLIST = "watchlist";
    public static final String MOVIES_COLUMN_WATCHED = "watched";
    public static final String MOVIES_COLUMN_RATE = "rate";
    public static final String MOVIES_COLUMN_DATE_WATCHED = "date_watched";

    public static final String TABLE_CATEGORIES = "categories";
    public static final String CATEGORIES_COLUMN_ID = "id";
    public static final String CATEGORIES_COLUMN_TITLE = "title";

    public static final String TABLE_MOVIE_CATEGORIES = "movie_categories";
    public static final String MOVIE_CATEGORIES_COLUMN_MOVIE_ID = "movie_id";
    public static final String MOVIE_CATEGORIES_COLUMN_CATEGORY_ID = "category_id";

    public static final String[] TABLE_MOVIES_COLUMNS = {MOVIES_COLUMN_ID, MOVIES_COLUMN_TITLE, MOVIES_COLUMN_DATE_CREATED, MOVIES_COLUMN_COMMENTS, MOVIES_COLUMN_WATCHLIST, MOVIES_COLUMN_WATCHED, MOVIES_COLUMN_RATE, MOVIES_COLUMN_DATE_WATCHED};
    public static final String[] TABLE_CATEGORIES_COLUMNS = {CATEGORIES_COLUMN_ID, CATEGORIES_COLUMN_TITLE};
    public static final String[] TABLE_MOVIE_CATEGORIES_COLUMNS = {MOVIE_CATEGORIES_COLUMN_MOVIE_ID, MOVIE_CATEGORIES_COLUMN_CATEGORY_ID};


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_MOVIES+" ("+
                MOVIES_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                MOVIES_COLUMN_TITLE +" TEXT NOT NULL, "+
                MOVIES_COLUMN_DATE_CREATED +" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, "+
                MOVIES_COLUMN_COMMENTS +" TEXT, " +
                MOVIES_COLUMN_WATCHLIST + " BOOLEAN NOT NULL, " +
                MOVIES_COLUMN_WATCHED + " BOOLEAN NOT NULL, " +
                MOVIES_COLUMN_RATE + " DOUBLE DEFAULT 0.0, " +
                MOVIES_COLUMN_DATE_WATCHED + " VARCHAR(63))");

        db.execSQL("CREATE TABLE " + TABLE_CATEGORIES + " ("+
                CATEGORIES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                CATEGORIES_COLUMN_TITLE + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_MOVIE_CATEGORIES + " ("+
                MOVIE_CATEGORIES_COLUMN_MOVIE_ID + " INTEGER, "+
                MOVIE_CATEGORIES_COLUMN_CATEGORY_ID + " INTEGER)");

        ContentValues values = new ContentValues();
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Δράσης");
        categories.add("Κωμωδία");
        categories.add("Θρίλερ");
        categories.add("Μυστηρίου");
        categories.add("Επιστημονικής φαντασίας");
        categories.add("Αστυνομική");
        categories.add("Ρομαντική");
        categories.add("Αθλητική");
        categories.add("Δραματική");
        categories.add("Ιστορική");
        categories.add("Τρόμου");
        categories.add("Φαντασίας");
        categories.add("Βιογραφία");
        categories.add("Μιούσικαλ");

        for(int i = 0; i < categories.size(); i++){
            values.put(CATEGORIES_COLUMN_TITLE, categories.get(i));
            db.insert(TABLE_CATEGORIES, null, values);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
