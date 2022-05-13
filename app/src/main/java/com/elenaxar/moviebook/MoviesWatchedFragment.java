package com.elenaxar.moviebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MoviesWatchedFragment extends Fragment {
    RecyclerView recyclerViewMoviesWatched;

    public MoviesWatchedFragment() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_watched, container, false);
        recyclerViewMoviesWatched = view.findViewById(R.id.recyclerViewMoviesWatched);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewMoviesWatched.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ArrayList<Movie> moviesWatched = Movie.getAllMoviesWatched(getContext());
        MoviesWatchedAdapter moviesWatchedAdapter = new MoviesWatchedAdapter(getContext(), moviesWatched);
        recyclerViewMoviesWatched.setAdapter(moviesWatchedAdapter);
    }
}