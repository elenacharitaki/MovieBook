package com.elenaxar.moviebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WatchlistFragment extends Fragment {
    RecyclerView recyclerViewWatchlist;

    public WatchlistFragment() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);
        recyclerViewWatchlist = view.findViewById(R.id.recyclerViewWatchlist);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewWatchlist.setLayoutManager(layoutManager);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<Movie> watchlist = Movie.getAllWatchlist(getContext());
        WatchlistMovieAdapter watchlistMovieAdapter = new WatchlistMovieAdapter(getContext(), watchlist);
        recyclerViewWatchlist.setAdapter(watchlistMovieAdapter);
    }
}