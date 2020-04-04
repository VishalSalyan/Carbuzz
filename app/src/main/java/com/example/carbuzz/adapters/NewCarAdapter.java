package com.example.carbuzz.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carbuzz.R;
import com.example.carbuzz.data.CarData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewCarAdapter extends RecyclerView.Adapter<NewCarAdapter.ViewHolder> {

    private ArrayList<CarData> carList;
    private Context context;

    // data is passed into the constructor
    public NewCarAdapter(Context context, ArrayList<CarData> carList) {
        this.context = context;
        this.carList = carList;

    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_new_car, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final CarData carData = carList.get(position);

        holder.name.setText(carData.getName());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        Picasso.get().load(user.getCarImage()).into(holder.carImage);
        Picasso.get().load(R.drawable.electric).into(holder.carImage);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (carList == null) {
            return 0;
        }
        return carList.size();
    }


    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private CardView container;
        private ImageView carImage;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_car_name);
            container = itemView.findViewById(R.id.cv_container);
            carImage = itemView.findViewById(R.id.iv_car_image);
        }
    }

}