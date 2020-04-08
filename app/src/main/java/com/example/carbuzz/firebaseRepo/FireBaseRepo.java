package com.example.carbuzz.firebaseRepo;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;

import com.example.carbuzz.data.CarData;
import com.example.carbuzz.data.UserData;
import com.example.carbuzz.utils.SessionData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireBaseRepo {
    public static final FireBaseRepo I = new FireBaseRepo();

    private FireBaseRepo() {
    }

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference("user");
    private DatabaseReference exploreCarRef = database.getReference("explore_car");
    private DatabaseReference newCarRef = database.getReference("new_car");
    private DatabaseReference carCollectionRef = database.getReference("car_collection");

    public void signUp(final UserData userData, final ServerResponse<Boolean> serverResponse) {
        userRef.push().setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                serverResponse.onSuccess(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                serverResponse.onFailure(new Throwable(e.getMessage()));
            }
        });
    }

    public void login(final UserData userData, final ServerResponse<Boolean> serverResponse) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserData user = snapshot.getValue(UserData.class);
                    assert user != null;
                    if (user.getName().equals(userData.getName()) && user.getPassword().equals(userData.getPassword())) {
                        serverResponse.onSuccess(true);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void fetchExploreCar(final ServerResponse<ArrayList<CarData>> serverResponse) {
        exploreCarRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<CarData> carList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CarData carData = snapshot.getValue(CarData.class);
                    carList.add(carData);
                }
                serverResponse.onSuccess(carList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void fetchNewCar(final ServerResponse<ArrayList<CarData>> serverResponse) {
        newCarRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<CarData> carList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CarData carData = snapshot.getValue(CarData.class);
                    carList.add(carData);
                }
                serverResponse.onSuccess(carList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void fetchCollection(final ServerResponse<ArrayList<CarData>> serverResponse) {
        carCollectionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<CarData> carList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CarData carData = snapshot.getValue(CarData.class);
                    carList.add(carData);
                }
                serverResponse.onSuccess(carList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void setProfile(final UserData userData, final ServerResponse<String> serverResponse) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserData user = snapshot.getValue(UserData.class);
                    assert user != null;
                    if (user.getEmail().equals(userData.getEmail())) {
                        userRef.orderByChild("email").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    String key = childSnapshot.getKey();
                                    assert key != null;
                                    userRef.child(key).setValue(userData);
                                }
                                serverResponse.onSuccess("Update Successfully");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getCarDetails(final String id, String mode, final ServerResponse<CarData> serverResponse) {
        switch (mode) {
            case "Collection":
                carCollectionRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            CarData carData = snapshot.getValue(CarData.class);
                            assert carData != null;
                            if (carData.getId().equals(id)) {
                                serverResponse.onSuccess(carData);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        serverResponse.onFailure(new Throwable(databaseError.getMessage()));
                    }
                });
                break;
            case "explore_car":
                exploreCarRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            CarData carData = snapshot.getValue(CarData.class);
                            assert carData != null;
                            if (carData.getId().equals(id)) {
                                serverResponse.onSuccess(carData);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        serverResponse.onFailure(new Throwable(databaseError.getMessage()));
                    }
                });
                break;
            case "new_car":
                newCarRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            CarData carData = snapshot.getValue(CarData.class);
                            assert carData != null;
                            if (carData.getId().equals(id)) {
                                serverResponse.onSuccess(carData);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        serverResponse.onFailure(new Throwable(databaseError.getMessage()));
                    }
                });
                break;
        }
    }

    public void searchCar(final ServerResponse<String> serverResponse) {
        SessionData.getInstance().totalCarList.clear();
        exploreCarRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CarData carData = snapshot.getValue(CarData.class);
                    SessionData.getInstance().totalCarList.add(carData);
                }
                serverResponse.onSuccess("Explore Car Added");
                newCarRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            CarData carData = snapshot.getValue(CarData.class);
                            SessionData.getInstance().totalCarList.add(carData);
                        }
                        serverResponse.onSuccess("New Car Added");

                        carCollectionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    CarData carData = snapshot.getValue(CarData.class);
                                    SessionData.getInstance().totalCarList.add(carData);
                                }
                                serverResponse.onSuccess("Collection Car Added");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                serverResponse.onFailure(new Throwable(databaseError.getMessage()));

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        serverResponse.onFailure(new Throwable(databaseError.getMessage()));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void favouriteCars(final String email, final ServerResponse<ArrayList<String>> serverResponse) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserData userData = snapshot.getValue(UserData.class);
                    if (userData.getEmail().equals(email)) {
                        serverResponse.onSuccess(userData.getFavouriteCars());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }
}
