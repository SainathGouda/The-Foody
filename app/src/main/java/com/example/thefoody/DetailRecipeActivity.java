package com.example.thefoody;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thefoody.Adapters.IngredientsAdapter;
import com.example.thefoody.Adapters.InstructionsAdapter;
import com.example.thefoody.Adapters.SimilarRecipeAdapter;
import com.example.thefoody.Listeners.InstructionsListener;
import com.example.thefoody.Listeners.RecipeDetailsListener;
import com.example.thefoody.Listeners.RecipeOnClickListener;
import com.example.thefoody.Listeners.SimilarRecipeListener;
import com.example.thefoody.Manager.RequestManager;
import com.example.thefoody.Models.InstructionsResponse;
import com.example.thefoody.Models.RecipesDetailedResponse;
import com.example.thefoody.Models.SimilarRecipeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailRecipeActivity extends AppCompatActivity {
    int id;
    TextView meal_name_textView, meal_source_textView, meal_summary_textView;
    ImageView meal_image_imageView;
    RecyclerView meal_ingredients_recycler, meal_similar_recycler, meal_instructions_recyclerView;
    RequestManager manager;
    Dialog dialog;
    IngredientsAdapter ingredientsAdapter;
    SimilarRecipeAdapter similarRecipeAdapter;
    InstructionsAdapter instructionsAdapter;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);

        findViews();

        mToolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        manager.getSimilarRecipes(similarRecipeListener, id);
        manager.getInstructions(instructionsListener, id);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    private void findViews() {
        meal_name_textView = findViewById(R.id.meal_name_textView);
        meal_source_textView = findViewById(R.id.meal_source_textView);
        meal_summary_textView = findViewById(R.id.meal_summary_textView);
        meal_image_imageView = findViewById(R.id.meal_image_imageView);
        meal_ingredients_recycler = findViewById(R.id.meal_ingredients_recycler);
        meal_similar_recycler = findViewById(R.id.meal_similar_recycler);
        meal_instructions_recyclerView = findViewById(R.id.meal_instructions_recyclerView);
    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipesDetailedResponse response, String message) {
            dialog.dismiss();
            meal_name_textView.setText(response.title);
            meal_source_textView.setText(response.readyInMinutes + " minutes to get ready");
            //meal_summary_textView.setText(response.summary);
            //textView.setText(Html.fromHtml(Html.fromHtml(mHtmlString).toString()));
            meal_summary_textView.setText(Html.fromHtml(response.summary));
            Picasso.get().load(response.image).into(meal_image_imageView);

            meal_ingredients_recycler.setHasFixedSize(true);
            meal_ingredients_recycler.setLayoutManager(new LinearLayoutManager(DetailRecipeActivity.this, LinearLayoutManager.HORIZONTAL, false));
            ingredientsAdapter = new IngredientsAdapter(DetailRecipeActivity.this, response.extendedIngredients);
            meal_ingredients_recycler.setAdapter(ingredientsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(DetailRecipeActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final SimilarRecipeListener similarRecipeListener = new SimilarRecipeListener() {
        @Override
        public void didFetch(List<SimilarRecipeResponse> response, String message) {
            meal_similar_recycler.setHasFixedSize(true);
            meal_similar_recycler.setLayoutManager(new LinearLayoutManager(DetailRecipeActivity.this, LinearLayoutManager.HORIZONTAL, false));
            similarRecipeAdapter = new SimilarRecipeAdapter(DetailRecipeActivity.this, response, recipeOnClickListener);
            meal_similar_recycler.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(DetailRecipeActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeOnClickListener recipeOnClickListener = new RecipeOnClickListener() {
        @Override
        public void onRecipeClick(String id) {
            startActivity(new Intent(DetailRecipeActivity.this, DetailRecipeActivity.class).putExtra("id", id));
        }
    };

    private final InstructionsListener instructionsListener = new InstructionsListener() {
        @Override
        public void didFetch(List<InstructionsResponse> response, String message) {
            meal_instructions_recyclerView.setHasFixedSize(true);
            meal_instructions_recyclerView.setLayoutManager(new LinearLayoutManager(DetailRecipeActivity.this, LinearLayoutManager.VERTICAL, false));
            instructionsAdapter = new InstructionsAdapter(DetailRecipeActivity.this, response);
            meal_instructions_recyclerView.setAdapter(instructionsAdapter);
        }

        @Override
        public void didError(String message) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(DetailRecipeActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}