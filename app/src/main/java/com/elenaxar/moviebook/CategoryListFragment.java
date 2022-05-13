package com.elenaxar.moviebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryListFragment extends Fragment {
    RecyclerView recyclerViewCategoryList;

    public CategoryListFragment() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        recyclerViewCategoryList = view.findViewById(R.id.recyclerViewCategoryList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewCategoryList.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ArrayList<Category> categories = Category.getAll(getContext());
        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categories);
        recyclerViewCategoryList.setAdapter(categoryAdapter);
    }
}