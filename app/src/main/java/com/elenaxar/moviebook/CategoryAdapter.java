package com.elenaxar.moviebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Category> categories;

    public CategoryAdapter(Context context, ArrayList<Category> categories){
        this.context = context;
        this.categories = categories;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.category_item, parent, false);
        MyViewHolder item = new MyViewHolder(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int id = categories.get(position).getId();
        Category category = new Category(id, context);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowCategoryMoviesActivity.class);

                intent.putExtra("id", id);

                context.startActivity(intent);
            }
        });
        ((CategoryAdapter.MyViewHolder)holder).textViewCategoryTitle.setText(categories.get(position).getTitle());
        String num = category.getMoviesCount(context);
        if(num.equals("0")){
            ((CategoryAdapter.MyViewHolder)holder).textViewnumOfMovies.setText("Χωρίς ταινίες");
        } else if(num.equals("1")){
            ((CategoryAdapter.MyViewHolder)holder).textViewnumOfMovies.setText(category.getMoviesCount(context) + " ταινία");
        } else {
            ((CategoryAdapter.MyViewHolder)holder).textViewnumOfMovies.setText(category.getMoviesCount(context) + " ταινίες");
        }
    }

    @Override
    public int getItemCount() {
      return this.categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewCategoryTitle;
        TextView textViewnumOfMovies;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategoryTitle = itemView.findViewById(R.id.textViewCategoryTitle);
            textViewnumOfMovies = itemView.findViewById(R.id.numOfMovies);
        }
    }
}
