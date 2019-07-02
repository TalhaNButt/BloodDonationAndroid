package com.example.sikandar.blooddonation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

public class Registeractivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private RelativeLayout rlayout, rlayout1, rlayout2, rlayout3, rlayout4, rmaps, rpermissions;
    private Animation animation;
    private String verificarionId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    //zero screen
    //private Button btn_grant;

    //first screen
    private EditText name, username, password, cpassword;
    private Button nextbutton;

    //second screen
    private Button agroup, angroup, bgroup, bngroup, ogroup, ongroup;
    private Button male, female;
    private String gender;
    private String bloodgroup;
    private Button bloodbutton;

    //third screen
    private ImageButton location;
    private Button signup;
    double latitude;
    double longitude;
    String placeName;

    //maps
    private Button btn_saveloc;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;

    private Location mLastKnownLocation;
    private LocationCallback locationCallback;

    private MaterialSearchBar materialSearchBar;
    private View mapView;
    private RippleBackground rippleBg;

    private final float DEFAULT_ZOOM = 15;
    private Location currentLocation;
    private static final int LOCATION_REQUEST_CODE = 101;

    //fourth screen
    private Button confirm;
    private EditText number;
    private Spinner spinner;
    private String ph;
    //private Button confirm;

    //final screen
    private Button verify;
    private EditText verifycode;
    private EditText code;
    //private Button verify;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);

        Toolbar toolbar = findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //rpermissions = findViewById(R.id.rpermissions);
        rlayout = findViewById(R.id.rlayout);
        rlayout1 = findViewById(R.id.rlayout1);
        rlayout2 = findViewById(R.id.rlayout2);
        rlayout3 = findViewById(R.id.rlayout3);
        rlayout4 = findViewById(R.id.rlayout4);
        rmaps = findViewById(R.id.rmaps);
        rlayout.setVisibility(View.VISIBLE);

        animation = AnimationUtils.loadAnimation(this, R.anim.uptodowndiagonal);
        rlayout.setAnimation(animation);

        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        /**zero screen
        btn_grant = findViewById(R.id.btn_grant);
        btn_grant.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(Registeractivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return;
        }
**/

        //first screen
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        //email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.cpassword);
        nextbutton = findViewById(R.id.nextbutton);
        nextbutton.setOnClickListener(this);

        //second layout
        agroup = findViewById(R.id.agroup);
        angroup = findViewById(R.id.angroup);
        bgroup = findViewById(R.id.bgroup);
        bngroup = findViewById(R.id.bngroup);
        ogroup = findViewById(R.id.ogroup);
        ongroup = findViewById(R.id.ongroup);
        bloodbutton = findViewById(R.id.bloodbutton);
        agroup.setOnClickListener(this);
        angroup.setOnClickListener(this);
        bgroup.setOnClickListener(this);
        bngroup.setOnClickListener(this);
        ogroup.setOnClickListener(this);
        ongroup.setOnClickListener(this);

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        male.setOnClickListener(this);
        female.setOnClickListener(this);

        bloodbutton.setOnClickListener(this);

        //third screen
        location = findViewById(R.id.location);
        location.setOnClickListener(this);
        signup = findViewById(R.id.signupbutton);
        signup.setOnClickListener(this);

        //maps
        materialSearchBar = findViewById(R.id.searchBar);
        rippleBg = findViewById(R.id.ripple_bg);
        btn_saveloc = findViewById(R.id.btn_saveLoc);
        btn_saveloc.setOnClickListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_register);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Registeractivity.this);
        Places.initialize(Registeractivity.this, getString(R.string.google_maps_key));
        placesClient = Places.createClient(this);
        final AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();


        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString(), true, null, true);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {
                    //opening or closing a navigation drawer
                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                    materialSearchBar.disableSearch();
                }
            }
        });

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
                        .setCountry("pk")
                        .setTypeFilter(TypeFilter.ADDRESS)
                        .setSessionToken(token)
                        .setQuery(s.toString())
                        .build();
                placesClient.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
                        if (task.isSuccessful()) {
                            FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                            if (predictionsResponse != null) {
                                predictionList = predictionsResponse.getAutocompletePredictions();
                                List<String> suggestionsList = new ArrayList<>();
                                for (int i = 0; i < predictionList.size(); i++) {
                                    AutocompletePrediction prediction = predictionList.get(i);
                                    suggestionsList.add(prediction.getFullText(null).toString());
                                }
                                materialSearchBar.updateLastSuggestions(suggestionsList);
                                if (!materialSearchBar.isSuggestionsVisible()) {
                                    materialSearchBar.showSuggestionsList();
                                }
                            }
                        } else {
                            Log.i("mytag", "prediction fetching task unsuccessful");
                        }
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setSuggstionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClickListener(int position, View v) {
                if (position >= predictionList.size()) {
                    return;
                }
                AutocompletePrediction selectedPrediction = predictionList.get(position);
                String suggestion = materialSearchBar.getLastSuggestions().get(position).toString();
                materialSearchBar.setText(suggestion);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialSearchBar.clearSuggestions();
                    }
                }, 1000);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(materialSearchBar.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                final String placeId = selectedPrediction.getPlaceId();
                List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG,Place.Field.NAME);

                FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build();
                placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                    @Override
                    public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                        Place place = fetchPlaceResponse.getPlace();
                        Log.i("mytag", "Place found: " + place.getName());
                        LatLng latLngOfPlace = place.getLatLng();
                        placeName = place.getName();
                        latitude = Objects.requireNonNull(latLngOfPlace).latitude;
                        longitude = latLngOfPlace.longitude;

                        if (latLngOfPlace != null) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace, DEFAULT_ZOOM));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            apiException.printStackTrace();
                            int statusCode = apiException.getStatusCode();
                            Log.i("mytag", "place not found: " + e.getMessage());
                            Log.i("mytag", "status code: " + statusCode);
                        }
                    }
                });
            }

            @Override
            public void OnItemDeleteListener(int position, View v) {

            }
        });


        //fourth screen
        spinner = findViewById(R.id.cc);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CountryData.countryAreaCodes));
        confirm = findViewById(R.id.confirmbutton);
        confirm.setOnClickListener(this);
        number = findViewById(R.id.phone);
        //number = findViewById(R.id.phone);
        //String phonenumber = number.getText().toString();

        //final screen
        verifycode = findViewById(R.id.verifycode);
        progressBar = findViewById(R.id.progressbar);
        verify = findViewById(R.id.verifybutton);
        verify.setOnClickListener(this);
        //code = findViewById(R.id.code);
        //confirm = findViewById(R.id.confirmbutton);
        //confirm.setOnClickListener(this);
        //String confirmcode = code.getText().toString();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        if (v == nextbutton) {
            String sname = name.getText().toString();
            String susername = username.getText().toString();
            String spassword = password.getText().toString();
            String scpassword = cpassword.getText().toString();

            Toast.makeText(Registeractivity.this, "In Next Button", Toast.LENGTH_LONG).show();

            if (sname.isEmpty()) {
                name.setError("Enter Your Name");
                name.requestFocus();
                return;
            }
            if (susername.isEmpty()) {
                username.setError("Enter Your Username");
                username.requestFocus();
                return;
            }
            if (spassword.isEmpty() || spassword.length() < 6) {
                password.setError("Enter Valid Password");
                password.requestFocus();
                return;
            }
            if (scpassword.isEmpty()) {
                cpassword.setError("Password does not match");
                cpassword.requestFocus();
                return;
            }
            if (!scpassword.contentEquals(spassword)) {
                cpassword.setError("Password does not match");
                cpassword.requestFocus();
                return;
            }

            rlayout.setVisibility(View.GONE);
            rlayout1.setVisibility(View.VISIBLE);
        }

        if (v == agroup) {
            Toast.makeText(Registeractivity.this, "In Group Button", Toast.LENGTH_LONG).show();
            agroup.setBackground(getDrawable(R.drawable.bg_color));
            agroup.setTextColor(getResources().getColor(R.color.white));
            angroup.setBackground(getDrawable(R.drawable.bg_button_back));
            angroup.setTextColor(getResources().getColor(R.color.colorRed));
            bgroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bgroup.setTextColor(getResources().getColor(R.color.colorRed));
            bngroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bngroup.setTextColor(getResources().getColor(R.color.colorRed));
            ogroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ogroup.setTextColor(getResources().getColor(R.color.colorRed));
            ongroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ongroup.setTextColor(getResources().getColor(R.color.colorRed));

            bloodgroup = "A+";
        }

        if (v == angroup) {
            angroup.setBackground(getDrawable(R.drawable.bg_color));
            angroup.setTextColor(getResources().getColor(R.color.white));
            agroup.setBackground(getDrawable(R.drawable.bg_button_back));
            agroup.setTextColor(getResources().getColor(R.color.colorRed));
            bgroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bgroup.setTextColor(getResources().getColor(R.color.colorRed));
            bngroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bngroup.setTextColor(getResources().getColor(R.color.colorRed));
            ogroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ogroup.setTextColor(getResources().getColor(R.color.colorRed));
            ongroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ongroup.setTextColor(getResources().getColor(R.color.colorRed));

            bloodgroup = "A-";
        }

        if (v == bgroup) {
            bgroup.setBackground(getDrawable(R.drawable.bg_color));
            bgroup.setTextColor(getResources().getColor(R.color.white));
            agroup.setBackground(getDrawable(R.drawable.bg_button_back));
            agroup.setTextColor(getResources().getColor(R.color.colorRed));
            angroup.setBackground(getDrawable(R.drawable.bg_button_back));
            angroup.setTextColor(getResources().getColor(R.color.colorRed));
            bngroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bngroup.setTextColor(getResources().getColor(R.color.colorRed));
            ogroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ogroup.setTextColor(getResources().getColor(R.color.colorRed));
            ongroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ongroup.setTextColor(getResources().getColor(R.color.colorRed));

            bloodgroup = "B+";
        }

        if (v == bngroup) {
            bngroup.setBackground(getDrawable(R.drawable.bg_color));
            bngroup.setTextColor(getResources().getColor(R.color.white));
            agroup.setBackground(getDrawable(R.drawable.bg_button_back));
            agroup.setTextColor(getResources().getColor(R.color.colorRed));
            angroup.setBackground(getDrawable(R.drawable.bg_button_back));
            angroup.setTextColor(getResources().getColor(R.color.colorRed));
            bgroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bgroup.setTextColor(getResources().getColor(R.color.colorRed));
            ogroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ogroup.setTextColor(getResources().getColor(R.color.colorRed));
            ongroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ongroup.setTextColor(getResources().getColor(R.color.colorRed));

            bloodgroup = "B-";
        }

        if (v == ogroup) {
            ogroup.setBackground(getDrawable(R.drawable.bg_color));
            ogroup.setTextColor(getResources().getColor(R.color.white));
            agroup.setBackground(getDrawable(R.drawable.bg_button_back));
            agroup.setTextColor(getResources().getColor(R.color.colorRed));
            angroup.setBackground(getDrawable(R.drawable.bg_button_back));
            angroup.setTextColor(getResources().getColor(R.color.colorRed));
            bgroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bgroup.setTextColor(getResources().getColor(R.color.colorRed));
            bngroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bngroup.setTextColor(getResources().getColor(R.color.colorRed));
            ongroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ongroup.setTextColor(getResources().getColor(R.color.colorRed));

            bloodgroup = "O+";
        }

        if (v == ongroup) {
            ongroup.setBackground(getDrawable(R.drawable.bg_color));
            ongroup.setTextColor(getResources().getColor(R.color.white));
            agroup.setBackground(getDrawable(R.drawable.bg_button_back));
            agroup.setTextColor(getResources().getColor(R.color.colorRed));
            angroup.setBackground(getDrawable(R.drawable.bg_button_back));
            angroup.setTextColor(getResources().getColor(R.color.colorRed));
            bgroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bgroup.setTextColor(getResources().getColor(R.color.colorRed));
            bngroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bngroup.setTextColor(getResources().getColor(R.color.colorRed));
            ogroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ogroup.setTextColor(getResources().getColor(R.color.colorRed));

            bloodgroup = "O-";
        }

        if (v == male) {
            male.setBackground(getDrawable(R.drawable.bg_color));
            male.setTextColor(getResources().getColor(R.color.white));
            female.setBackground(getDrawable(R.drawable.bg_button_back));
            female.setTextColor(getResources().getColor(R.color.colorRed));

            gender = "Male";
        }

        if (v == female) {
            female.setBackground(getDrawable(R.drawable.bg_color));
            female.setTextColor(getResources().getColor(R.color.white));
            male.setBackground(getDrawable(R.drawable.bg_button_back));
            male.setTextColor(getResources().getColor(R.color.colorRed));

            gender = "Female";
        }

        if (v == bloodbutton) {
            Toast.makeText(Registeractivity.this, "In Blood Button", Toast.LENGTH_LONG).show();

            rlayout1.setVisibility(View.GONE);
            rlayout2.setVisibility(View.VISIBLE);
        }

        if (v == location) {
            Toast.makeText(Registeractivity.this, "In Location Button", Toast.LENGTH_LONG).show();
            //Intent intent = new Intent(Registeractivity.this, MapsActivity.class);
            //startActivity(intent);


            rlayout2.setVisibility(View.GONE);
            rmaps.setVisibility(View.VISIBLE);
        }

        if (v == signup) {
            Toast.makeText(Registeractivity.this, "In SignUp Button", Toast.LENGTH_LONG).show();

            //registerUser();
            rlayout2.setVisibility(View.GONE);
            rlayout3.setVisibility(View.VISIBLE);
        }

        if (v == btn_saveloc) {
            Toast.makeText(Registeractivity.this, "In Save Button", Toast.LENGTH_LONG).show();

            LatLng currentMarkerLocation = mMap.getCameraPosition().target;
             Toast.makeText(Registeractivity.this, "The place is:"+placeName+","+latitude+","+longitude, Toast.LENGTH_SHORT).show();
             rippleBg.startRippleAnimation();
             new Handler().postDelayed(new Runnable() {
            @Override public void run() {
            rippleBg.stopRippleAnimation();
            }
            }, 3000);

             //latitude = currentLocation.getLatitude();
             //longitude = currentLocation.getLongitude();

             Toast.makeText(Registeractivity.this, "Location Saved", Toast.LENGTH_LONG).show();
             rmaps.setVisibility(View.GONE);
             rlayout2.setVisibility(View.VISIBLE);

        }

        if (v == confirm) {
            Toast.makeText(Registeractivity.this, "In Confirm Button", Toast.LENGTH_LONG).show();

            String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
            String phonenumber = number.getText().toString().trim();

            if (phonenumber.isEmpty() || phonenumber.length() < 10) {
                number.setError("Valid number is required");
                number.requestFocus();
                return;
            }

            ph = "+" + code + phonenumber;
            //String ph = "+92" + phonenumber;

            rlayout3.setVisibility(View.GONE);
            rlayout4.setVisibility(View.VISIBLE);

            sendVerificationCode(ph);

            // rlayout1.setVisibility(View.GONE);
            //rlayout2.setVisibility(View.VISIBLE);
        }

        if (v == verify) {
            Toast.makeText(Registeractivity.this, "In Verify Button", Toast.LENGTH_LONG).show();

            String code = verifycode.getText().toString().trim();

            if (code.isEmpty() || code.length() < 6) {
                verifycode.setError("Enter valid code");
                verifycode.requestFocus();
                return;
            }

            verifycode(code);
            //rlayout.setVisibility(View.GONE);
            //rlayout1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
            finish();
            startActivity(new Intent(Registeractivity.this, HomeActivity.class));
        }
    }

    private void sendVerificationCode(String number) {
        Toast.makeText(Registeractivity.this, number.toString(), Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificarionId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

            if (code != null) {
                verifycode.setText(code);
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Registeractivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifycode(String code) {
        Toast.makeText(Registeractivity.this, "In VerifyCode", Toast.LENGTH_SHORT).show();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificarionId, code);

        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        Toast.makeText(Registeractivity.this, "In Phone Auth", Toast.LENGTH_SHORT).show();
        final String id=null;
        final String sname = name.getText().toString();
        final String susername = username.getText().toString();
        final String sbg = bloodgroup;
        final String num = ph;
        final String g = gender;
        final double lon = longitude;
        final double lat = latitude;
        final String pName = placeName;
        final String availability = "on";
        final String status="offline";

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    final User user = new User(
                            id,
                            sname,
                            susername,
                            num,
                            sbg,
                            g,
                            lon,
                            lat,
                            pName,
                            availability,
                            status
                    );

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");   //changed
                                FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();               //changed
                                final String id=firebaseUser.getUid();                                                 //changed
                                user.setId(id);//changed
                                ref.child(id).setValue(user);

                                Toast.makeText(Registeractivity.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Registeractivity.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Registeractivity.this, "No", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(Registeractivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        if(mapView != null && mapView.findViewById(Integer.parseInt("1")) != null){
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 40, 180);
        }

        //check if gps is enabled or not and then request user to enable it
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(Registeractivity.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(Registeractivity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
            }
        });

        task.addOnFailureListener(Registeractivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof ResolvableApiException){
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(Registeractivity.this, 51);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                if(materialSearchBar.isSuggestionsVisible())
                    materialSearchBar.clearSuggestions();
                if(materialSearchBar.isSearchEnabled())
                    materialSearchBar.disableSearch();
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 51) {
            if(resultCode == RESULT_OK) {
                getDeviceLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation(){
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if(task.isSuccessful()){
                            mLastKnownLocation = task.getResult();
                            if(mLastKnownLocation != null){
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            } else {
                                final LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(10000);
                                locationRequest.setFastestInterval(5000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                locationCallback = new LocationCallback(){
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        if(locationResult == null) {
                                            return;
                                        }
                                        mLastKnownLocation = locationResult.getLastLocation();
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                    }
                                };
                                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

                            }
                        } else {
                            Toast.makeText(Registeractivity.this, "unable to get last location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
