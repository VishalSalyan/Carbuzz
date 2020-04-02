package com.example.carbuzz.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carbuzz.adapters.ExploreCarAdapter;
import com.example.carbuzz.R;
import com.example.carbuzz.adapters.NewCarAdapter;
import com.example.carbuzz.data.CarData;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView newCarsRecyclerView;

    private ExploreCarAdapter mAdapter;
    private NewCarAdapter carAdapter;

    private ArrayList<CarData> carList = new ArrayList<>();
    private ArrayList<CarData> newCarList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = view.findViewById(R.id.explore_recyclerView);
        newCarsRecyclerView = view.findViewById(R.id.new_cars_recycler_view);

        carList.clear();
        newCarList.clear();

        CarData carData = new CarData();
        carData.setName("Electric");
        carList.add(carData);

        CarData carData1 = new CarData();
        carData.setName("Electric");
        newCarList.add(carData1);

        CarData carData2 = new CarData();
        carData.setName("Hybrid");
        newCarList.add(carData2);

        initializeExploreAdapter();
        initializeNewCarsAdapter();

        return view;
    }

    private void initializeNewCarsAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        newCarsRecyclerView.setLayoutManager(gridLayoutManager);

        // Initialize the adapter and attach it to the RecyclerView
        carAdapter = new NewCarAdapter(getActivity(), newCarList);
        newCarsRecyclerView.setAdapter(carAdapter);
    }

    private void initializeExploreAdapter() {
        // set up the RecyclerView in horizontal and vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new ExploreCarAdapter(getActivity(), carList);
        mRecyclerView.setAdapter(mAdapter);
    }

}


