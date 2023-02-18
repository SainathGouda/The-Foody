package com.example.thefoody;

import android.app.AlertDialog;
import android.app.ApplicationExitInfo;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thefoody.Adapters.RandomRecipeAdapter;
import com.example.thefoody.Listeners.RandomRecipeResponseListener;
import com.example.thefoody.Listeners.RecipeOnClickListener;
import com.example.thefoody.Manager.RequestManager;
import com.example.thefoody.Models.RandomRecipeApiResponse;
import com.example.thefoody.signin.LoginMainActivity;
import com.example.thefoody.signin.RegisterMainActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
        Dialog dialog;
        RequestManager manager;
        RandomRecipeAdapter randomRecipeAdapter;
        RecyclerView recyclerView;
        Spinner spinner;
        List<String> tags = new ArrayList<>();
        SearchView searchView;

        DrawerLayout drawerLayout;
        NavigationView navigationView;
        Toolbar toolbar;

        FirebaseAuth mAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            dialog = new Dialog(this);
            dialog.setContentView(R.layout.custom_loading);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            searchView = findViewById(R.id.home_searchView);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    tags.clear();
                    tags.add(query);
                    manager.getRandomRecipes(randomRecipeResponseListener, tags);
                    dialog.show();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });


            spinner = findViewById(R.id.spinner_tags);
            ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.tags,
                    R.layout.spinner_text
            );
            arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(spinnerSelectedListener);

            manager =  new RequestManager(this);
            //manager.getRandomRecipes(randomRecipeResponseListener);
            //dialog.show();

            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);
            toolbar = findViewById(R.id.toolbar);

            setSupportActionBar(toolbar);

            navigationView.bringToFront();
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(this);

            navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
            if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            else {
                super.onBackPressed();
            }

    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            dialog.dismiss();
            recyclerView =  findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
            randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this, response.recipes, recipeOnClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            tags.clear();
            tags.add(adapterView.getSelectedItem().toString());
            manager.getRandomRecipes(randomRecipeResponseListener, tags);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private final RecipeOnClickListener recipeOnClickListener = new RecipeOnClickListener() {
        @Override
        public void onRecipeClick(String id) {
            startActivity(new Intent(MainActivity.this, DetailRecipeActivity.class).putExtra("id", id));
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_jokes:
                Intent intent = new Intent(MainActivity.this, JokesActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_share:
                /*Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.setType("text/plain");
                String Body = "Download this app";
                String Sub ="https://www.google.co.in";
                intent1.putExtra(Intent.EXTRA_TEXT, Body);
                intent1.putExtra(Intent.EXTRA_TEXT, Sub);
                startActivity(Intent.createChooser(intent1,"Share using"));*/
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
                RateUsDialog rateUsDialog = new RateUsDialog(MainActivity.this);
                rateUsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                rateUsDialog.setCancelable(false);
                rateUsDialog.show();
                break;
            case R.id.nav_register:
                startActivity(new Intent(MainActivity.this, RegisterMainActivity.class));
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
                                Intent intent = new Intent(MainActivity.this, LoginMainActivity.class);
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
