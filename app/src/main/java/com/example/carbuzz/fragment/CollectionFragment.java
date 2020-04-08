package com.example.carbuzz.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carbuzz.R;
import com.example.carbuzz.adapters.CollectionCarAdapter;

import com.example.carbuzz.data.CarData;
import com.example.carbuzz.firebaseRepo.FireBaseRepo;
import com.example.carbuzz.firebaseRepo.ServerResponse;

import java.util.ArrayList;


public class CollectionFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CollectionCarAdapter collectionAdapter;
    private ArrayList<CarData> carList = new ArrayList<>();

    public CollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_collection, container, false);
        mRecyclerView = view.findViewById(R.id.collection_recycler_view);

        initializeAdapter();
        fetchCarList();
        CarData carData = new CarData();
        carData.setCarName("Electric");
        carList.add(carData);

        return view;
    }

    private void fetchCarList() {
        FireBaseRepo.I.fetchCollection(new ServerResponse<ArrayList<CarData>>() {
            @Override
            public void onSuccess(ArrayList<CarData> body) {

            }

            @Override
            public void onFailure(Throwable error) {

            }
        });
    }

    private void initializeAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        collectionAdapter = new CollectionCarAdapter(getActivity(), carList);
        mRecyclerView.setAdapter(collectionAdapter);
    }

}

