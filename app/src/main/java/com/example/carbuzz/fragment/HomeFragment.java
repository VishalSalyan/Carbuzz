package com.example.carbuzz.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.carbuzz.adapters.ExploreCarAdapter;
import com.example.carbuzz.R;
import com.example.carbuzz.adapters.NewCarAdapter;
import com.example.carbuzz.data.CarData;
import com.example.carbuzz.firebaseRepo.FireBaseRepo;
import com.example.carbuzz.firebaseRepo.ServerResponse;

import java.util.ArrayList;

import static com.example.carbuzz.utils.Toasts.show;


public class HomeFragment extends Fragment {
    private TextView tvExploreCar, tvNewCarCollection;
    private RecyclerView mRecyclerView;
    private RecyclerView newCarsRecyclerView;

    private ExploreCarAdapter exploreCarAdapter;
    private NewCarAdapter newCarAdapter;

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
        tvExploreCar = view.findViewById(R.id.tv_explore_car);
        tvNewCarCollection = view.findViewById(R.id.tv_new_car_collection);

        initializeExploreAdapter();
        initializeNewCarsAdapter();
        fetchExploreCar();
        fetchNewCar();

        return view;
    }

    private void fetchNewCar() {
        FireBaseRepo.I.fetchNewCar(new ServerResponse<ArrayList<CarData>>() {
            @Override
            public void onSuccess(ArrayList<CarData> body) {
                if (body.size() == 0) {
                    tvNewCarCollection.setVisibility(View.GONE);
                }
                newCarList.clear();
                newCarList.addAll(body);
                newCarAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                show.longMsg(getActivity(), error.toString());
            }
        });
    }

    private void fetchExploreCar() {
        FireBaseRepo.I.fetchExploreCar(new ServerResponse<ArrayList<CarData>>() {
            @Override
            public void onSuccess(ArrayList<CarData> body) {
                if (body.size() == 0) {
                    tvExploreCar.setVisibility(View.GONE);
                }
                carList.clear();
                carList.addAll(body);
                exploreCarAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                show.longMsg(getActivity(), error.toString());
            }
        });
    }

    private void initializeNewCarsAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        newCarsRecyclerView.setLayoutManager(gridLayoutManager);

        // Initialize the adapter and attach it to the RecyclerView
        newCarAdapter = new NewCarAdapter(getActivity(), newCarList);
        newCarsRecyclerView.setAdapter(newCarAdapter);
    }

    private void initializeExploreAdapter() {
        // set up the RecyclerView in horizontal and vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // Initialize the adapter and attach it to the RecyclerView
        exploreCarAdapter = new ExploreCarAdapter(getActivity(), carList);
        mRecyclerView.setAdapter(exploreCarAdapter);
    }

}


