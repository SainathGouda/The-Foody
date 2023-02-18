package com.example.thefoody.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thefoody.MainActivity;
import com.example.thefoody.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginMainActivity extends AppCompatActivity {

    Context context;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(context.getString(R.string.db_link));

    CheckBox remember_me_checkbx;
    public static String PRES_NAME = "MyPrefsFile";
    FirebaseAuth mAuth;
    float v =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.passwordl);
        final Button login_btn = findViewById(R.id.login_btn);
        final TextView register_now_btn = findViewById(R.id.registerNow);
        final ImageView icon_image = findViewById(R.id.shapeableImageViewl);
        final TextView welcome_text = findViewById(R.id.welcomeTextl);
        remember_me_checkbx = findViewById(R.id.remember_me_checkbx);
        mAuth = FirebaseAuth.getInstance();

        phone.setTranslationX(800);
        password.setTranslationX(800);
        login_btn.setTranslationX(800);
        register_now_btn.setTranslationX(800);
        icon_image.setTranslationY(-800);
        welcome_text.setTranslationY(-800);

        phone.setAlpha(v);
        password.setAlpha(v);
        login_btn.setAlpha(v);
        register_now_btn.setAlpha(v);
        icon_image.setAlpha(v);
        welcome_text.setAlpha(v);

        phone.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login_btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        register_now_btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        remember_me_checkbx.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1000).start();
        icon_image.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        welcome_text.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailTxt = phone.getText().toString();
                final String passwordTxt = password.getText().toString();

                if (emailTxt.isEmpty()){
                    Toast.makeText(LoginMainActivity.this,"Please enter your mobile number",Toast.LENGTH_SHORT).show();
                }
                else if (passwordTxt.isEmpty()){
                    Toast.makeText(LoginMainActivity.this,"Please enter your password",Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(emailTxt)){
                                final String getPassword = snapshot.child(emailTxt).child("password").getValue(String.class);

                                if (getPassword.equals(passwordTxt)){
                                    if(remember_me_checkbx.isChecked()){
                                        SharedPreferences sharedPreferences = getSharedPreferences(LoginMainActivity.PRES_NAME,0);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        editor.putBoolean("hasLoggedIn",true);
                                        editor.commit();
                                    }
                                    Toast.makeText(LoginMainActivity.this,"Successfully logged in",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginMainActivity.this, MainActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(LoginMainActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(LoginMainActivity.this,"User not registered",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        register_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginMainActivity.this,RegisterMainActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}