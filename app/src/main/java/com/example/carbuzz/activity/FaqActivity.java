package com.example.carbuzz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.carbuzz.R;
import com.example.carbuzz.adapters.FaqAdapter;
import com.example.carbuzz.data.FaqData;
import com.example.carbuzz.firebaseRepo.FireBaseRepo;
import com.example.carbuzz.firebaseRepo.ServerResponse;

import java.util.ArrayList;

public class FaqActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<FaqData> faqList = new ArrayList<>();
    private FaqAdapter faqAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        recyclerView = findViewById(R.id.recycler_view);
        initializeAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchFaqData();
    }

    private void fetchFaqData() {
        FireBaseRepo.I.fetchFaq(new ServerResponse<ArrayList<FaqData>>() {
            @Override
            public void onSuccess(ArrayList<FaqData> body) {
                faqList.clear();
                faqList.addAll(body);
                faqAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {

            }
        });
    }

    private void initializeAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FaqActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        faqAdapter = new FaqAdapter(FaqActivity.this, faqList);
        recyclerView.setAdapter(faqAdapter);
    }
}
