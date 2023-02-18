package com.example.thefoody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thefoody.Adapters.JokesAdapter;
import com.example.thefoody.Listeners.JokesListener;
import com.example.thefoody.Manager.RequestManager;
import com.example.thefoody.Models.Root;
import com.example.thefoody.signin.LoginMainActivity;
import com.example.thefoody.signin.RegisterMainActivity;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

public class JokesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Dialog dialog;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView food_joke_textView;
    RequestManager manager;
    JokesAdapter jokesAdapter;
    ImageView refresh_imageView;

    //FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);

        drawerLayout = findViewById(R.id.jokes_drawer_layout);
        navigationView = findViewById(R.id.jokes_nav_view);
        toolbar = findViewById(R.id.jokes_toolbar);
        refresh_imageView = findViewById(R.id.refresh_imageView);
        refresh_imageView.bringToFront();
        refresh_imageView.setClickable(true);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        manager = new RequestManager(this);
        manager.getJokes(jokesListener);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_jokes);

        refresh_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(JokesActivity.this);
                dialog.setContentView(R.layout.custom_loading);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                manager.getJokes(jokesListener);
            }
        });
    }

    private final JokesListener jokesListener = new JokesListener() {
        @Override
        public void didFetch(Root response, String message) {
            dialog.dismiss();
            food_joke_textView = findViewById(R.id.food_joke_textView);
            jokesAdapter =  new JokesAdapter(JokesActivity.this,response);
            food_joke_textView.setMovementMethod(new ScrollingMovementMethod());
            food_joke_textView.setText(response.text);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(JokesActivity.this, message /*"Still in development"*/, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(JokesActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_jokes:
                break;
            case R.id.nav_share:
                if(Build.VERSION.SDK_INT>=24){
                    try{
                        Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                        m.invoke(null);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                ApplicationInfo api = getApplicationContext().getApplicationInfo();
                String apkPath = api.sourceDir;

                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.setType("application/vnd.android.package-archive");

                intent1.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkPath)));
                startActivity(Intent.createChooser(intent1,"Share using"));
                break;
            case R.id.nav_rate_us:
                //Toast.makeText(this,"Rate Us", Toast.LENGTH_SHORT).show();
                RateUsDialog rateUsDialog = new RateUsDialog(JokesActivity.this);
                rateUsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                rateUsDialog.setCancelable(false);
                rateUsDialog.show();
                break;
            case R.id.nav_register:
                startActivity(new Intent(JokesActivity.this, RegisterMainActivity.class));
                break;
            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you want to logout?")
                        .setTitle("Logout")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sharedPreferences = getSharedPreferences(LoginMainActivity.PRES_NAME,0);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putBoolean("hasLoggedIn",false);
                                editor.commit();
                                Intent intent = new Intent(JokesActivity.this, LoginMainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setIcon(R.drawable.ic_launcher);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}