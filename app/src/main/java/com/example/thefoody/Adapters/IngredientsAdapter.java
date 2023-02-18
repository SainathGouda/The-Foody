package com.example.thefoody.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thefoody.Models.ExtendedIngredient;
import com.example.thefoody.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder>{
    Context context;
    List<ExtendedIngredient> list;

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_meal_ingredients, parent, false));
    }

    public IngredientsAdapter(Context context, List<ExtendedIngredient> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.ingredients_name_textView.setText(list.get(position).name);
        holder.ingredients_name_textView.setSelected(true);
        holder.ingredients_quantity_textView.setText(list.get(position).original);
        holder.ingredients_quantity_textView.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/"+list.get(position).image).into(holder.ingredients_imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class IngredientsViewHolder extends RecyclerView.ViewHolder{

    TextView ingredients_quantity_textView, ingredients_name_textView;
    ImageView ingredients_imageView;

    public IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        ingredients_quantity_textView = itemView.findViewById(R.id.ingredients_quantity_textView);
        ingredients_name_textView = itemView.findViewById(R.id.ingredients_name_textView);
        ingredients_imageView = itemView.findViewById(R.id.ingredients_imageView);
    }
}
