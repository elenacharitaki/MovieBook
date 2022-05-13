package com.elenaxar.moviebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MoviesWatchedAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Movie> movies;

    public MoviesWatchedAdapter(Context context, ArrayList<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.watched_movie_item, parent, false);
        MyViewHolder item = new MyViewHolder(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ((MyViewHolder)holder).textViewWatchedMovieTitle.setText(movies.get(position).getTitle());
        ((MyViewHolder)holder).watchedMovieRatingBar.setRating(movies.get(position).getRate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowMovieActivity.class);

                int id = movies.get(position).getId();
                intent.putExtra("id", id);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewWatchedMovieTitle;
        RatingBar watchedMovieRatingBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWatchedMovieTitle = itemView.findViewById(R.id.textViewWatchedMovieTitle);
            watchedMovieRatingBar = itemView.findViewById(R.id.watchedMovieRatingBar);
        }
    }
}
