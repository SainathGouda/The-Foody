package com.example.thefoody.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import com.example.thefoody.signin.LoginMainActivity;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thefoody.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterMainActivity extends AppCompatActivity {

    Context context;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(context.getString(R.string.db_link));
    float v =0;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);

        final EditText fullName = findViewById(R.id.full_name);
        final EditText phone = findViewById(R.id.mobiler);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.passwordr);
        final EditText confirmPassword = findViewById(R.id.confirmpassword);
        final Button register_btn = findViewById(R.id.register_btn);
        final TextView login_now_btn = findViewById(R.id.loginNow);
        final ImageView icon_image = findViewById(R.id.shapeableImageViewr);
        final TextView welcome_text = findViewById(R.id.welcomeTextr);

        fullName.setTranslationX(800);
        phone.setTranslationX(800);
        email.setTranslationX(800);
        password.setTranslationX(800);
        confirmPassword.setTranslationX(800);
        register_btn.setTranslationX(800);
        login_now_btn.setTranslationX(800);
        icon_image.setTranslationY(-800);
        welcome_text.setTranslationY(-800);

        fullName.setAlpha(v);
        phone.setAlpha(v);
        email.setAlpha(v);
        password.setAlpha(v);
        confirmPassword.setAlpha(v);
        register_btn.setAlpha(v);
        login_now_btn.setAlpha(v);
        icon_image.setAlpha(v);
        welcome_text.setAlpha(v);

        fullName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        phone.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        confirmPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        register_btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        login_now_btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        icon_image.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        welcome_text.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullnameTxt = fullName.getText().toString();
                final String mobileTxt = phone.getText().toString();
                final String emailTxt = email.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String conPasswordTxt = confirmPassword.getText().toString();
                mAuth = FirebaseAuth.getInstance();

                if (emailTxt.isEmpty() || fullnameTxt.isEmpty() || mobileTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(RegisterMainActivity.this,"Please fill all fields",Toast.LENGTH_SHORT).show();
                }
                else if (!passwordTxt.equals(conPasswordTxt)){
                    Toast.makeText(RegisterMainActivity.this,"Passwords are not matching",Toast.LENGTH_SHORT).show();
                }
                else if(!validateMobile(mobileTxt)){
                    Toast.makeText(RegisterMainActivity.this,"Enter a valid mobile number",Toast.LENGTH_SHORT).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()){
                    Toast.makeText(RegisterMainActivity.this,"Enter a valid email address",Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(mobileTxt)){
                                Toast.makeText(RegisterMainActivity.this,"Phone number is already registered",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //Intent intent = new Intent(getApplicationContext(),VerifyPhoneNoActivity.class);
                                //intent.putExtra("phoneNo",mobileTxt);

                                databaseReference.child("users").child(mobileTxt).child("fullname").setValue(fullnameTxt);
                                databaseReference.child("users").child(mobileTxt).child("email").setValue(emailTxt);
                                databaseReference.child("users").child(mobileTxt).child("password").setValue(passwordTxt);

                                Toast.makeText(RegisterMainActivity.this,"User registered sucessfully.",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        login_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    boolean validateMobile(String input){
        Pattern p = Pattern.compile("[7-9][0-9]{9}");
        Matcher m = p.matcher(input);
        return m.matches();
    }
}