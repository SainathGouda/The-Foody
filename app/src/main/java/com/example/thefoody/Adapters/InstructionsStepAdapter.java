package com.example.thefoody.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.thefoody.Models.Step;
import com.example.thefoody.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InstructionsStepAdapter extends RecyclerView.Adapter<InstructionStepViewHolder>{
    Context context;
    List<Step> list;

    public InstructionsStepAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionStepViewHolder(LayoutInflater.from(context).inflate(R.layout.steps_instructions_steps, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionStepViewHolder holder, int position) {
        holder.instruction_step_number_textView.setText(String.valueOf(list.get(position).number));
        holder.instruction_step_title_textView.setText(list.get(position).step);

        holder.instruction_ingredients_recycler.setHasFixedSize(true);
        holder.instruction_ingredients_recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        InstructionsIngredientsAdapter instructionsIngredientsAdapter = new InstructionsIngredientsAdapter(context, list.get(position).ingredients);
        holder.instruction_ingredients_recycler.setAdapter(instructionsIngredientsAdapter);

        holder.instruction_equipments_recycler.setHasFixedSize(true);
        holder.instruction_equipments_recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        InstructionsEquipmentsAdapter instructionsEquipmentsAdapter = new InstructionsEquipmentsAdapter(context, list.get(position).equipment);
        holder.instruction_equipments_recycler.setAdapter(instructionsEquipmentsAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class InstructionStepViewHolder extends RecyclerView.ViewHolder{
    TextView instruction_step_number_textView, instruction_step_title_textView;
    RecyclerView instruction_equipments_recycler, instruction_ingredients_recycler;

    public InstructionStepViewHolder(@NonNull View itemView) {
        super(itemView);

        instruction_step_number_textView = itemView.findViewById(R.id.instruction_step_number_textView);
        instruction_step_title_textView = itemView.findViewById(R.id.instruction_step_title_textView);
        instruction_equipments_recycler = itemView.findViewById(R.id.instruction_equipments_recycler);
        instruction_ingredients_recycler = itemView.findViewById(R.id.instruction_ingredients_recycler);
    }
}
