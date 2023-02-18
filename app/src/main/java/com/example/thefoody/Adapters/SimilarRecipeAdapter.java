package com.example.thefoody.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thefoody.Listeners.RecipeOnClickListener;
import com.example.thefoody.Models.SimilarRecipeResponse;
import com.example.thefoody.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarRecipeAdapter extends RecyclerView.Adapter<SimilarRecipeViewHolder> {
    Context context;
    List<SimilarRecipeResponse> list;
    RecipeOnClickListener listener;

    public SimilarRecipeAdapter(Context context, List<SimilarRecipeResponse> list, RecipeOnClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SimilarRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_similar_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarRecipeViewHolder holder, int position) {
        holder.similar_title_textView.setText(list.get(position).title);
        holder.similar_title_textView.setSelected(true);
        holder.similar_serving_textView.setText(list.get(position).servings + " Persons");
        // https://spoonacular.com/recipeImages/{ID}-{SIZE}.{TYPE}
        Picasso.get().load("https://spoonacular.com/recipeImages/"+list.get(position).id+"-556x370."+list.get(position).imageType).into(holder.similar_imageView);

        holder.similar_recipe_holder.setOnClickListener(new View.OnClickListener() {
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

class SimilarRecipeViewHolder extends RecyclerView.ViewHolder{
    CardView similar_recipe_holder;
    TextView similar_title_textView, similar_serving_textView;
    ImageView similar_imageView;

    public SimilarRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        similar_recipe_holder = itemView.findViewById(R.id.similar_recipe_holder);
        similar_title_textView = itemView.findViewById(R.id.similar_title_textView);
        similar_serving_textView = itemView.findViewById(R.id.similar_serving_textView);
        similar_imageView = itemView.findViewById(R.id.similar_imageView);
    }
}
