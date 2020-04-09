package com.example.carbuzz.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.carbuzz.firebaseRepo.FireBaseRepo;
import com.example.carbuzz.firebaseRepo.ServerResponse;
import com.example.carbuzz.fragment.CarWishFragment;
import com.example.carbuzz.fragment.CollectionFragment;
import com.example.carbuzz.fragment.HomeFragment;
import com.example.carbuzz.R;
import com.example.carbuzz.fragment.SearchFragment;
import com.example.carbuzz.utils.SessionData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.carbuzz.utils.GoTo.go;
import static com.example.carbuzz.utils.Toasts.show;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    HomeFragment homeFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        HomeFragment homeFragment = new HomeFragment();
        openFragment(homeFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FireBaseRepo.I.searchCar(new ServerResponse<String>() {
            @Override
            public void onSuccess(String body) {

            }

            @Override
            public void onFailure(Throwable error) {
                show.longMsg(HomeActivity.this, error.getMessage());
            }
        });
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {


                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.bottom_home:
                            show.shortMsg(HomeActivity.this, "Home");
                            homeFragment = new HomeFragment();
                            openFragment(homeFragment);
                            break;
                        case R.id.bottom_category:
                            CollectionFragment collectionFragment = new CollectionFragment();
                            openFragment(collectionFragment);
                            show.shortMsg(HomeActivity.this, "Collection");
                            break;
                        case R.id.bottom_favorites:
                            CarWishFragment carWishFragment = new CarWishFragment();
                            openFragment(carWishFragment);
                            show.shortMsg(HomeActivity.this, "WishList");
                            break;
                        case R.id.bottom_search:
                            SearchFragment searchFragment = new SearchFragment();
                            openFragment(searchFragment);
                            show.shortMsg(HomeActivity.this, "Search");
                            break;
                    }
                    return true;
                }
            };

    public void openFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem itemLogOut = menu.findItem(R.id.menu_log_out);
        itemLogOut.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_log_out) {
            SessionData.getInstance().saveLogin(false);
            SessionData.getInstance().clearSessionData();
            go.to(HomeActivity.this, LoginActivity.class);
            return (true);
        }
        return (super.onOptionsItemSelected(item));
    }
}