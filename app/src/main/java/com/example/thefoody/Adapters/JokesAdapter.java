package com.example.thefoody.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thefoody.Models.Root;
import com.example.thefoody.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JokesAdapter extends RecyclerView.Adapter<JokesViewHolder>{
    Context context;
    Root list;

    public JokesAdapter(Context context, Root list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public JokesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JokesViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_jokes,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull JokesViewHolder holder, int position) {
        holder.food_joke_textView.setText(list.text);
        holder.food_joke_textView.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}

class JokesViewHolder extends RecyclerView.ViewHolder{

    TextView food_joke_textView;
    public JokesViewHolder(@NonNull View itemView) {
        super(itemView);
        food_joke_textView = itemView.findViewById(R.id.food_joke_textView);
    }
}
