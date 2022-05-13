package com.elenaxar.moviebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener  {

    NavigationBarView navigationBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBarView = findViewById(R.id.bottomNavMenu);
        navigationBarView.setOnItemSelectedListener(this);
        navigationBarView.setSelectedItemId(R.id.moviesWatched);

        FloatingActionButton floatingBtnAddNew = findViewById(R.id.floatingBtnAddNew);

        floatingBtnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewMovieActivity.class);
                startActivity(intent);

            }
        });
    }

    WatchlistFragment watchlistFragment = new WatchlistFragment();
    MoviesWatchedFragment moviesWatchedFragment = new MoviesWatchedFragment();
    CategoryListFragment categoryListFragment = new CategoryListFragment();

    // fragment change
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.moviesWatched:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, moviesWatchedFragment).commit();
                return true;
            case R.id.watchlist:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, watchlistFragment).commit();
                return true;
            case R.id.categoryList:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, categoryListFragment).commit();
                return true;
        }
        return false;
    }
}