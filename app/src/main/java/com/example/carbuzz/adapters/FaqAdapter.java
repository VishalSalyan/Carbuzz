package com.example.carbuzz.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbuzz.R;
import com.example.carbuzz.activity.CarDetailActivity;
import com.example.carbuzz.data.CarData;
import com.example.carbuzz.data.FaqData;
import com.example.carbuzz.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> {

    private ArrayList<FaqData> faqList;
    private Context context;

    // data is passed into the constructor
    public FaqAdapter(Context context, ArrayList<FaqData> faqList) {

        this.context = context;
        this.faqList = faqList;

    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_collection, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final FaqData faqData = faqList.get(position);

        holder.question.setText(faqData.getQuestion());
        holder.answer.setText(faqData.getAnswer());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (faqList == null) {
            return 0;
        }
        return faqList.size();
    }


    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView question;
        private TextView answer;

        ViewHolder(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.tv_question);
            answer = itemView.findViewById(R.id.tv_answer);
        }

    }


}