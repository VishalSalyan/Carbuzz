package com.example.carbuzz.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carbuzz.R;
import com.example.carbuzz.data.CarData;
import com.example.carbuzz.data.UserData;
import com.example.carbuzz.firebaseRepo.FireBaseRepo;
import com.example.carbuzz.firebaseRepo.ServerResponse;
import com.example.carbuzz.utils.Constants;
import com.example.carbuzz.utils.SessionData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.carbuzz.utils.GoTo.go;
import static com.example.carbuzz.utils.Toasts.show;

public class CarDetailActivity extends AppCompatActivity {

    private TextView tvCarName, tvDescription, tvTransmission, tvMileage, tvColor, tvBodyType, tvYear,
            tvMake, tvFuelType, tvModel, tvOwnerName, tvOwnerPhoneNumber, tvOwnerEmail, tvOwnerAddress;
    private ImageView carImage;
    private Button addToWishList;


    private String id;
    private String mode;
    private boolean isDeleteMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Car Details");

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        id = bundle.getString("carId");
        mode = bundle.getString("mode");
        checkWishList();
        fetchCarDetails();
    }

    private void checkWishList() {
        if (SessionData.getInstance().getLocalData() != null)
            if (SessionData.getInstance().getLocalData().getFavouriteCars() != null || SessionData.getInstance().getLocalData().getFavouriteCars().size() != 0) {
                for (int i = 0; i < SessionData.getInstance().getLocalData().getFavouriteCars().size(); i++) {
                    if (id.equals(SessionData.getInstance().getLocalData().getFavouriteCars().get(i).getCarId())) {
                        isDeleteMode = true;
                        addToWishList.setText(getResources().getString(R.string.remove_from_wish_list));
                    }
                }
            }
    }

    private void fetchCarDetails() {
        FireBaseRepo.I.getCarDetails(id, mode, new ServerResponse<CarData>() {
            @Override
            public void onSuccess(CarData body) {
                setData(body);
            }

            @Override
            public void onFailure(Throwable error) {
                show.longMsg(CarDetailActivity.this, error.getMessage());
            }
        });
    }

    private void setData(CarData carData) {
        Picasso.get().load(carData.getCarImage()).into(carImage);
        tvCarName.setText(carData.getCarName());
        tvDescription.setText(carData.getDescription());
        tvTransmission.setText(carData.getTransmission());
        tvMileage.setText(carData.getMileage());
        tvColor.setText(carData.getColor());
        tvBodyType.setText(carData.getBodyType());
        tvYear.setText(carData.getYear());
        tvMake.setText(carData.getMake());
        tvFuelType.setText(carData.getFuelType());
        tvModel.setText(carData.getModel());
        tvOwnerName.setText(carData.getOwnerName());
        tvOwnerPhoneNumber.setText(carData.getOwnerPhoneNumber());
        tvOwnerEmail.setText(carData.getOwnerEmail());
        tvOwnerAddress.setText(carData.getOwnerAddress());
    }

    private void initViews() {
        addToWishList = findViewById(R.id.btn_add_to_wish_list);
        carImage = findViewById(R.id.iv_car_image);
        tvCarName = findViewById(R.id.tv_car_name);
        tvDescription = findViewById(R.id.tv_description);
        tvTransmission = findViewById(R.id.tv_transmission);
        tvMileage = findViewById(R.id.tv_mileage);
        tvColor = findViewById(R.id.tv_color);
        tvBodyType = findViewById(R.id.tv_body_type);
        tvYear = findViewById(R.id.tv_year);
        tvMake = findViewById(R.id.tv_make);
        tvFuelType = findViewById(R.id.tv_fuel_type);
        tvModel = findViewById(R.id.tv_model);
        tvOwnerName = findViewById(R.id.tv_owner_name);
        tvOwnerPhoneNumber = findViewById(R.id.tv_owner_phone_number);
        tvOwnerEmail = findViewById(R.id.tv_owner_email);
        tvOwnerAddress = findViewById(R.id.tv_owner_address);

        addToWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FireBaseRepo.I.setWishListCars(isDeleteMode, SessionData.getInstance().getLocalData().getEmail(), id, mode, new ServerResponse<String>() {
                    @Override
                    public void onSuccess(String body) {
                        if (isDeleteMode) {
                            isDeleteMode = false;
                            addToWishList.setText(getResources().getString(R.string.add_to_wishlist));
                        } else {
                            show.longMsg(CarDetailActivity.this, "Added successfully to WishList");
                        }
                        checkWishList();
                    }

                    @Override
                    public void onFailure(Throwable error) {

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
//            go.to(CarDetailActivity.this, HomeActivity.class);
            return (true);
        }
        return (super.onOptionsItemSelected(item));
    }
}
