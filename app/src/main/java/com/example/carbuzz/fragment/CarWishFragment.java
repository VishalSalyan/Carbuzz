package com.example.carbuzz.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carbuzz.R;
import com.example.carbuzz.adapters.WishlistCarAdapter;
import com.example.carbuzz.data.CarData;

import java.util.ArrayList;


public class CarWishFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private WishlistCarAdapter mWishlistAdapter;
    private ArrayList<CarData> carList = new ArrayList<>();

    public CarWishFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_collection, container, false);
        mRecyclerView = view.findViewById(R.id.collection_recycler_view);

        CarData carData = new CarData();
        carData.setCarName("Electric");
        carList.add(carData);



        initializeCollectionAdapter();

        return view;
    }



    private void initializeCollectionAdapter() {
        // set up the RecyclerView in horizontal and vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // Initialize the adapter and attach it to the RecyclerView
        mWishlistAdapter = new WishlistCarAdapter(getActivity(), carList);
        mRecyclerView.setAdapter(mWishlistAdapter);
    }

}

