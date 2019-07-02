package com.example.sikandar.blooddonation;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    private RelativeLayout L1, L2;
    private String verificarionId;
    Button btn_login, btn_verify;
    TextView tvLogin, btn_register;
    EditText et_Phone, et_verify;
    ProgressBar progressBar;
    boolean a, b;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        L1 = findViewById(R.id.L1);
        L2 = findViewById(R.id.L2);

        et_Phone = (EditText) findViewById(R.id.et_Phone);
        et_verify = (EditText) findViewById(R.id.et_verify);

        tvLogin = (TextView) findViewById(R.id.tvLogin);
        btn_register = (TextView) findViewById(R.id.textViewSignUp);

        progressDialog = new ProgressDialog(LoginActivity.this);

        btn_login = (Button) findViewById(R.id.button_Login);
        btn_verify = (Button) findViewById(R.id.verify);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_verify.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            Intent intent=new Intent(this, HomeActivity.class);                    //changed
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if(v == btn_register){
            switch (v.getId()) {
                case R.id.textViewSignUp:
                    progressDialog.setMessage("Loading..");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();
                    new Thread() {

                        public void run() {
                            Intent intent = new Intent(LoginActivity.this, PermissionsActivity.class);
                           /* Pair[] pairs = new Pair[1];
                            pairs[0] = new Pair<View, String>(tvLogin, "tvLogin");
                            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);*/
                            startActivity(intent);
                            progressDialog.dismiss();
                        }

                    }.start();

                    break;
            }
        }
        if(v== btn_login){
            Toast.makeText(LoginActivity.this, "In Login Button", Toast.LENGTH_LONG).show();
            String number = et_Phone.getText().toString().trim();

            if (number.isEmpty() || number.length() < 10) {
                et_Phone.setError("Valid number is required");
                et_Phone.requestFocus();
                return;
            }


            final String ph = "+92" + number.substring(1);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            Query query = ref.orderByChild("phone").equalTo(ph);
            Toast.makeText(LoginActivity.this, ph.toString(), Toast.LENGTH_LONG).show();
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Toast.makeText(LoginActivity.this, "Please Wait", Toast.LENGTH_LONG).show();
                        sendVerificationCode(ph);
                        L1.setVisibility(View.GONE);
                        L2.setVisibility(View.VISIBLE);


                    } else {
                        et_Phone.setError("Please enter the number again or sign up");
                        Toast.makeText(LoginActivity.this, "Number dont exists please enter a valid number or sign up", Toast.LENGTH_SHORT).show();
                        et_Phone.setText("");
                        et_Phone.requestFocus();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if(v == btn_verify){
            Toast.makeText(LoginActivity.this, "In Verify Button", Toast.LENGTH_LONG).show();

            String code = et_verify.getText().toString().trim();

            if(code.isEmpty()|| code.length()< 6){
                et_verify.setError("Enter valid code");
                et_verify.requestFocus();
                return;
            }

            verifycode(code);
        }
    }

    private void sendVerificationCode(String number) {
        Toast.makeText(LoginActivity.this, number.toString(), Toast.LENGTH_SHORT).show();
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

            if(code != null){
                et_verify.setText(code);
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifycode(String code) {
        Toast.makeText(LoginActivity.this, "In VerifyCode", Toast.LENGTH_SHORT).show();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificarionId, code);

        signInWithCredential(credential);
    }



    private void signInWithCredential(PhoneAuthCredential credential) {
        Toast.makeText(LoginActivity.this, "In Phone Auth", Toast.LENGTH_SHORT).show();

        progressBar.setVisibility(View.GONE);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "No", Toast.LENGTH_LONG).show();
                            }
                        }
                else {
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
