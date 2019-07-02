package com.example.sikandar.blooddonation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class Profile extends Fragment{
    private static final int CHOOSE_IMAGE = 101;
    NestedScrollView nested1, nested2;
    TextView textView, username, address, gender, phone, bloodgroup, phone2;
    ImageView imageView, imageView2;
    EditText editText, et_username2,et_phone2,et_address2,et_gender2,name2;
    double Lat, Lon;
    String Address;
    Spinner bg_spinner;
    Uri uriProfileImage;
    ProgressBar progressBar;
    String profileImageUrl;
    FirebaseAuth mAuth;
    Toolbar toolbar;
    StorageReference mStorageReference;
    private String currentUserID;
    private Button locationprofile;
    private DatabaseReference mDatabase;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;

    public Profile() {

    }

    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nested1 = (NestedScrollView) view.findViewById(R.id.nested1);
        nested2 = (NestedScrollView) view.findViewById(R.id.nested2);
        textView = (TextView) view.findViewById(R.id.name);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView2 = (ImageView) view.findViewById(R.id.imageView2);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        username = (TextView) view.findViewById(R.id.tv_username);
        address = (TextView) view.findViewById(R.id.tv_address);
        gender = (TextView) view.findViewById(R.id.tv_gender);
        phone = (TextView) view.findViewById(R.id.tv_phone);
        bloodgroup = (TextView) view.findViewById(R.id.bgProfile);
        //locationprofile = (Button) view.findViewById(R.id.location_profile);
        name2 = view.findViewById(R.id.name2);
        bg_spinner = view.findViewById(R.id.bg_spinner);
        et_username2 = view.findViewById(R.id.tv_username2);
        phone2 = view.findViewById(R.id.tv_phone2);
        et_gender2 = view.findViewById(R.id.tv_gender2);
        et_address2 = view.findViewById(R.id.tv_address2);

        bg_spinner = view.findViewById(R.id.bg_spinner);
        bg_spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, BloodGroups.bloodgroups));



        //locationprofile.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
          //      Intent intent = new Intent(getActivity(), MapsActivity.class);
            //    startActivity(intent);
        //    }
        //});


        imageView2.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
           showImageChooser();
         }
        });

        loadUserInformation();
        loadFromDB();


        view.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nested1.setVisibility(View.GONE);
                nested2.setVisibility(View.VISIBLE);

                loadFromDBtoEdit();
            }
        });

        view.findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                saveUserInformation();
            }
        });

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            Objects.requireNonNull(getActivity()).finish();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    private void loadFromDB(){

       mDatabase.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String myName = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                        String myUsername = Objects.requireNonNull(dataSnapshot.child("username").getValue()).toString();
                        String myGender = Objects.requireNonNull(dataSnapshot.child("gender").getValue()).toString();
                        String myPhone = Objects.requireNonNull(dataSnapshot.child("phone").getValue()).toString();
                        String myBloodGroup = Objects.requireNonNull(dataSnapshot.child("bloodgroup").getValue()).toString();
                        Lat = (double) Objects.requireNonNull(dataSnapshot.child("lat").getValue());
                        Lon = (double) Objects.requireNonNull(dataSnapshot.child("lon").getValue());
                        String myAddress = Objects.requireNonNull(dataSnapshot.child("placeName").getValue()).toString();
                        Address = myAddress;

                        textView.setText(myName);
                        username.setText(myUsername);
                        gender.setText(myGender);
                        phone.setText(myPhone);
                        bloodgroup.setText(myBloodGroup);
                        address.setText(myAddress);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(),"Error Loading from db", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadFromDBtoEdit(){

        mDatabase.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String myName = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                String myUsername = Objects.requireNonNull(dataSnapshot.child("username").getValue()).toString();
                String myGender = Objects.requireNonNull(dataSnapshot.child("gender").getValue()).toString();
                String myPhone = Objects.requireNonNull(dataSnapshot.child("phone").getValue()).toString();
                CharSequence myAddress = Objects.requireNonNull(dataSnapshot.child("placeName").getValue()).toString();

                name2.setText(myName);
                et_username2.setText(myUsername);
                et_gender2.setText(myGender);
                phone2.setText(myPhone);
                et_address2.setText(myAddress);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Error Loading from db", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUserInformation() {
        final FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(imageView);
            }

            if (user.getDisplayName() != null) {
                textView.setText(user.getDisplayName());
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveUserInformation() {
        String displayName = textView.getText().toString();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null && profileImageUrl != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }

        String bg = BloodGroups.bloodgroups[bg_spinner.getSelectedItemPosition()];
        FirebaseUser firebaseUser=mAuth.getCurrentUser();               //changed
        String userid=firebaseUser.getUid();
        String name = name2.getText().toString().trim();
        String uname = et_username2.getText().toString().trim();
        String ph = phone2.getText().toString().trim();
        String gender = et_gender2.getText().toString().trim();
        double lat = Lat;
        double lon = Lon;
        String pName = Address;
        final String availability= "on";
        String status="offline";

        User users = new User(
                userid,
                name,
                uname,
                ph,
                bg,
                gender,
                lon,
                lat,
                pName,
                availability,
                status
        );

        FirebaseDatabase.getInstance().getReference("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(), "No", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(), uriProfileImage);
                imageView.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void uploadImageToFirebaseStorage() {
        if(uriProfileImage != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = mStorageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);


                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    profileImageUrl = uri.toString();
                                    Toast.makeText(getContext(), "Image Upload Successful", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }
}
