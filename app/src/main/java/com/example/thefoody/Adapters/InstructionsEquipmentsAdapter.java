package com.example.thefoody.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thefoody.Models.Equipment;
import com.example.thefoody.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstructionsEquipmentsAdapter extends RecyclerView.Adapter<InstructionsEquipmentsViewHolder>{
    Context context;
    List<Equipment> list;

    public InstructionsEquipmentsAdapter(Context context, List<Equipment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionsEquipmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionsEquipmentsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_step_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsEquipmentsViewHolder holder, int position) {
        holder.instructions_step_item_textView.setText(list.get(position).name);
        holder.instructions_step_item_textView.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/equipment_100x100/"+list.get(position).image).into(holder.instructions_step_item_imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class InstructionsEquipmentsViewHolder extends RecyclerView.ViewHolder {
    ImageView instructions_step_item_imageView;
    TextView instructions_step_item_textView;

    public InstructionsEquipmentsViewHolder(@NonNull View itemView) {
        super(itemView);

        instructions_step_item_imageView = itemView.findViewById(R.id.instructions_step_item_imageView);
        instructions_step_item_textView = itemView.findViewById(R.id.instructions_step_item_textView);
    }
}