package com.example.carbuzz.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carbuzz.R;
import com.example.carbuzz.adapters.WishListCarAdapter;
import com.example.carbuzz.data.CarData;
import com.example.carbuzz.data.WishListData;
import com.example.carbuzz.firebaseRepo.FireBaseRepo;
import com.example.carbuzz.firebaseRepo.ServerResponse;
import com.example.carbuzz.utils.SessionData;

import java.util.ArrayList;

import static com.example.carbuzz.utils.Toasts.show;


public class CarWishFragment extends Fragment {

    private RecyclerView recyclerView;
    private WishListCarAdapter wishlistCarAdapter;
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
        recyclerView = view.findViewById(R.id.collection_recycler_view);
        initializeAdapter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        FireBaseRepo.I.wishListCars(SessionData.getInstance().getLocalData().getEmail(), new ServerResponse<ArrayList<WishListData>>() {
            @Override
            public void onSuccess(ArrayList<WishListData> body) {
                carList.clear();
                for (WishListData item : body) {
                    for (int i = 0; i < SessionData.getInstance().totalCarList.size(); i++) {
                        if (item.getCarId().equals(SessionData.getInstance().totalCarList.get(i).getId())) {
                            carList.add(SessionData.getInstance().totalCarList.get(i));
                            break;
                        }
                    }
                }
                wishlistCarAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                show.longMsg(getActivity(), error.getMessage());
            }
        });
    }

    private void initializeAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        wishlistCarAdapter = new WishListCarAdapter(getActivity(), carList);
        recyclerView.setAdapter(wishlistCarAdapter);
    }

}

