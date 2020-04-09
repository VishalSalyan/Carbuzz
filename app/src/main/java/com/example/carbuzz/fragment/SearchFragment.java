package com.example.carbuzz.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.carbuzz.R;
import com.example.carbuzz.adapters.CollectionCarAdapter;
import com.example.carbuzz.adapters.SearchAdapter;
import com.example.carbuzz.data.CarData;
import com.example.carbuzz.firebaseRepo.FireBaseRepo;
import com.example.carbuzz.utils.SessionData;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import static com.example.carbuzz.utils.Toasts.show;


public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        MaterialSearchView searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.recycler_view);
        initializeAdapter();
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                filter(newText);
                return true;
            }
        });
        return view;
    }

    private void initializeAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchAdapter = new SearchAdapter(getActivity(), SessionData.getInstance().totalCarList);
        recyclerView.setAdapter(searchAdapter);
    }

    private void filter(String text) {
        ArrayList<CarData> filteredList = new ArrayList<>();

        for (CarData item : SessionData.getInstance().totalCarList) {
            if (item.getCarName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        searchAdapter.filterList(filteredList);
    }
}