package com.example.thefoody.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thefoody.Listeners.RecipeOnClickListener;
import com.example.thefoody.Models.Recipe;
import com.example.thefoody.R;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder> {
    Context context;
    List<Recipe> list;
    RecipeOnClickListener listener;

    public RandomRecipeAdapter(Context context, List<Recipe> list, RecipeOnClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_random_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        holder.title_textView.setText(list.get(position).title);
        holder.title_textView.setSelected(true);
        //holder.description_textView.setText(list.get(position).summary);
        holder.description_textView.setText(Html.fromHtml(list.get(position).summary));
        holder.heart_textView.setText(list.get(position).aggregateLikes + " Likes");
        holder.clock_textView.setText(list.get(position).readyInMinutes + " Mins");
        holder.serving_textView.setText(list.get(position).servings + " Serves");
        Picasso.get().load(list.get(position).image).into(holder.recipe_imageView);

        holder.row_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClick(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class RandomRecipeViewHolder extends RecyclerView.ViewHolder{

    CardView row_cardView;
    TextView title_textView, description_textView, heart_textView, clock_textView, serving_textView;
    ImageView recipe_imageView;


    public RandomRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        row_cardView = itemView.findViewById(R.id.row_cardView);
        title_textView = itemView.findViewById(R.id.title_textView);
        description_textView = itemView.findViewById(R.id.description_textView);
        heart_textView = itemView.findViewById(R.id.heart_textView);
        clock_textView = itemView.findViewById(R.id.clock_textView);
        serving_textView = itemView.findViewById(R.id.serving_textView);
        recipe_imageView = itemView.findViewById(R.id.recipe_imageView);
    }
}