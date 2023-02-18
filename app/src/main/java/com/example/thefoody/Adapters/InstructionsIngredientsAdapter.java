package com.example.thefoody.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.thefoody.Models.Ingredient;
import com.example.thefoody.R;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InstructionsIngredientsAdapter extends RecyclerView.Adapter<InstructionsIngredientsViewHolder>{
    Context context;
    List<Ingredient> list;

    public InstructionsIngredientsAdapter(Context context, List<Ingredient> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionsIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionsIngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_step_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsIngredientsViewHolder holder, int position) {
        holder.instructions_step_item_textView.setText(list.get(position).name);
        holder.instructions_step_item_textView.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/"+list.get(position).image).into(holder.instructions_step_item_imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class InstructionsIngredientsViewHolder extends RecyclerView.ViewHolder {
    ImageView instructions_step_item_imageView;
    TextView instructions_step_item_textView;

    public InstructionsIngredientsViewHolder(@NonNull View itemView) {
        super(itemView);

        instructions_step_item_imageView = itemView.findViewById(R.id.instructions_step_item_imageView);
        instructions_step_item_textView = itemView.findViewById(R.id.instructions_step_item_textView);
    }
}