package com.example.thefoody.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thefoody.Models.InstructionsResponse;
import com.example.thefoody.R;

import java.util.List;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsViewHolder>{
    Context context;
    List<InstructionsResponse> list;

    public InstructionsAdapter(Context context, List<InstructionsResponse> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionsViewHolder(LayoutInflater.from(context).inflate(R.layout.instructions_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder holder, int position) {
        holder.name_instruction_textView.setText(list.get(position).name);
        holder.steps_instructions_recycler.setHasFixedSize(true);
        holder.steps_instructions_recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        InstructionsStepAdapter stepAdapter = new InstructionsStepAdapter(context, list.get(position).steps);
        holder.steps_instructions_recycler.setAdapter(stepAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class InstructionsViewHolder extends RecyclerView.ViewHolder{
    TextView name_instruction_textView;
    RecyclerView steps_instructions_recycler;

    public InstructionsViewHolder(@NonNull View itemView) {
        super(itemView);
        name_instruction_textView = itemView.findViewById(R.id.name_instruction_textView);
        steps_instructions_recycler = itemView.findViewById(R.id.steps_instructions_recycler);
    }
}
