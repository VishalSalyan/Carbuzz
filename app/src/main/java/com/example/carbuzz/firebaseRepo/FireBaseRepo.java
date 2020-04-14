package com.example.carbuzz.firebaseRepo;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.carbuzz.data.CarData;
import com.example.carbuzz.data.UserData;
import com.example.carbuzz.data.WishListData;
import com.example.carbuzz.utils.Constants;
import com.example.carbuzz.utils.SessionData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class FireBaseRepo {
    public static final FireBaseRepo I = new FireBaseRepo();

    private FireBaseRepo() {
    }

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference(Constants.USER);
    private DatabaseReference exploreCarRef = database.getReference(Constants.EXPLORE_CAR);
    private DatabaseReference newCarRef = database.getReference(Constants.NEW_CAR);
    private DatabaseReference carCollectionRef = database.getReference(Constants.CAR_COLLECTION);

    //File Storage
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference mStorageReference = storage.getReference();

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

    public void login(final String email, final String password, final ServerResponse<UserData> serverResponse) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserData user = snapshot.getValue(UserData.class);
                    if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                        serverResponse.onSuccess(user);
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
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                    userRef.child(key).child("email").setValue(userData.getEmail());
                                    userRef.child(key).child("name").setValue(userData.getName());
                                    userRef.child(key).child("phoneNumber").setValue(userData.getPhoneNumber());
                                    userRef.child(key).child("gender").setValue(userData.getGender());
                                    userRef.child(key).child("userImage").setValue(userData.getUserImage());
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
            case Constants.CAR_COLLECTION:
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
            case Constants.EXPLORE_CAR:
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
            case Constants.NEW_CAR:
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

    public void wishListCars(final String email, final ServerResponse<ArrayList<WishListData>> serverResponse) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserData userData = snapshot.getValue(UserData.class);
                    assert userData != null;
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

    public void setWishListCars(final boolean isDeleteMode, final String email, final String carId, final String carMode, final ServerResponse<String> serverResponse) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final UserData userData = snapshot.getValue(UserData.class);
                    assert userData != null;
                    if (userData.getEmail().equals(email)) {
                        final String key = snapshot.getKey();
                        assert key != null;

                        ArrayList<WishListData> carWishList = new ArrayList<>();
                        if (!isDeleteMode) {
                            WishListData wishListData = new WishListData();
                            wishListData.setCarId(carId);
                            wishListData.setMode(carMode);
                            carWishList.add(wishListData);
                            carWishList.addAll(SessionData.getInstance().getLocalData().getFavouriteCars());
                            userRef.child(key).child("favouriteCars").setValue(carWishList);
                            getUserData(email, new ServerResponse<UserData>() {
                                @Override
                                public void onSuccess(UserData body) {
                                    serverResponse.onSuccess(email);
                                }

                                @Override
                                public void onFailure(Throwable error) {
                                }
                            });
                        } else {
                            userRef./*child(key).child("favouriteCars").orderByChild("carId").equalTo(carId).*/addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        UserData userData1 = snapshot.getValue(UserData.class);
                                        assert userData1 != null;
                                        ArrayList<WishListData> wishList = new ArrayList<>();
                                        for (int i = 0; i < userData1.getFavouriteCars().size(); i++) {
                                            if (!userData1.getFavouriteCars().get(i).getCarId().equals(carId)) {
                                                wishList.add(userData1.getFavouriteCars().get(i));
                                            }
                                        }
                                        userRef.child(key).child("favouriteCars").setValue(null);
                                        userRef.child(key).child("favouriteCars").setValue(wishList);

                                        getUserData(email, new ServerResponse<UserData>() {
                                            @Override
                                            public void onSuccess(UserData body) {
                                                userRef.child(key).child("favouriteCars").setValue(body.getFavouriteCars());
                                                serverResponse.onSuccess(email);
                                            }

                                            @Override
                                            public void onFailure(Throwable error) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    serverResponse.onFailure(new Throwable(databaseError.toString()));
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void uploadFile(String fileNameWithExtension, Uri data, final ServerResponse<String> serverResponse) {
        final StorageReference sRef = mStorageReference.child(fileNameWithExtension);
        sRef.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressWarnings("VisibleForTests")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        serverResponse.onSuccess(uri.toString());
                    }
                });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        serverResponse.onFailure(new Throwable(exception.getMessage()));
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                        textViewStatus.setText((int) progress + "% Uploading...");
                    }
                });
    }

    public void getUserData(final String email, final ServerResponse<UserData> serverResponse) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserData userData = snapshot.getValue(UserData.class);

                    if (userData.getEmail().equals(email)) {
                        SessionData.getInstance().saveLocalData(userData);
                        serverResponse.onSuccess(userData);
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