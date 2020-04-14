package com.example.carbuzz.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.brouding.simpledialog.SimpleDialog;
import com.example.carbuzz.data.CarData;
import com.example.carbuzz.firebaseRepo.FireBaseRepo;
import com.example.carbuzz.firebaseRepo.ServerResponse;
import com.example.carbuzz.fragment.CarWishFragment;
import com.example.carbuzz.fragment.CollectionFragment;
import com.example.carbuzz.fragment.HomeFragment;
import com.example.carbuzz.R;
import com.example.carbuzz.fragment.SearchFragment;
import com.example.carbuzz.utils.SessionData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import static com.example.carbuzz.utils.GoTo.go;
import static com.example.carbuzz.utils.Toasts.show;

public class HomeActivity extends AppCompatActivity {

    private MaterialSearchView searchView;
    private MenuItem itemSearch;
    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView = findViewById(R.id.search_view);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                filter(newText);
                return true;
            }
        });

        HomeFragment homeFragment = new HomeFragment();
        openFragment(homeFragment);
    }

    private void filter(String text) {
        SessionData.getInstance().filteredList.clear();
        for (CarData item : SessionData.getInstance().totalCarList) {
            if (item.getCarName().toLowerCase().contains(text.toLowerCase())) {
                SessionData.getInstance().filteredList.add(item);
            }
        }
        searchFragment.filterList();
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
                            itemSearch.setVisible(false);
                            show.shortMsg(HomeActivity.this, "Home");
                            HomeFragment homeFragment = new HomeFragment();
                            openFragment(homeFragment);
                            break;
                        case R.id.bottom_category:
                            itemSearch.setVisible(false);
                            CollectionFragment collectionFragment = new CollectionFragment();
                            openFragment(collectionFragment);
                            show.shortMsg(HomeActivity.this, "Collection");
                            break;
                        case R.id.bottom_favorites:
                            itemSearch.setVisible(false);
                            CarWishFragment carWishFragment = new CarWishFragment();
                            openFragment(carWishFragment);
                            show.shortMsg(HomeActivity.this, "WishList");
                            break;
                        case R.id.bottom_more:
                            popupMenuExample();


                            break;
                    }
                    return true;
                }
            };

    public void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        itemSearch = menu.findItem(R.id.action_search);
        searchView.setMenuItem(itemSearch);
        itemSearch.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return (super.onOptionsItemSelected(item));
    }

    private void popupMenuExample() {
        PopupMenu p = new PopupMenu(HomeActivity.this, findViewById(R.id.bottom_more));
        p.getMenuInflater().inflate(R.menu.popup_menu, p.getMenu());
        p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popup_profile:
                        go.to(HomeActivity.this, ProfileActivity.class);
                        break;
                    case R.id.popup_help:
                        break;
                    case R.id.popup_search:
                        itemSearch.setVisible(true);
                        searchFragment = new SearchFragment();
                        openFragment(searchFragment);
                        break;
                    case R.id.popup_log_out:
                        showLogOutDialog();

                        break;
                }
                return true;
            }
        });
        p.show();
    }

    private void showLogOutDialog() {
        new SimpleDialog.Builder(HomeActivity.this)
                .setTitle("Log Out")
                .setContent("Are you Sure ?")
                .setBtnConfirmText("Log Out")
                .setBtnCancelText("Cancel")
                .setCancelable(true)
                .onConfirm(new SimpleDialog.BtnCallback() {
                    @Override
                    public void onClick(@NonNull SimpleDialog dialog, @NonNull SimpleDialog.BtnAction which) {
                        SessionData.getInstance().saveLogin(false);
                        SessionData.getInstance().clearSessionData();
                        go.to(HomeActivity.this, LoginActivity.class);
                        dialog.dismiss();
                    }
                })
                .show();

    }

}