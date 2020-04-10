package com.example.carbuzz.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carbuzz.R;
import com.example.carbuzz.adapters.SearchAdapter;
import com.example.carbuzz.data.CarData;
import com.example.carbuzz.utils.SessionData;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private ArrayList<CarData> carList = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        initializeAdapter();
        return view;
    }

    public void filterList() {
        searchAdapter.filterList(SessionData.getInstance().filteredList);
    }

    private void initializeAdapter() {
        carList.addAll(SessionData.getInstance().totalCarList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchAdapter = new SearchAdapter(getActivity(), carList);
        recyclerView.setAdapter(searchAdapter);
    }

}