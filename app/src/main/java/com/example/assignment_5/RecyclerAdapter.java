package com.example.assignment_5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.viewHolder> {
    Context context;
    List<ModelQuotes> modelQuotes;

    public RecyclerAdapter(Context context, List<ModelQuotes> modelQuotes) {
        this.context = context;
        this.modelQuotes = modelQuotes;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_quotes,parent,false);
        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.tv_Quote.setText(modelQuotes.get(position).quote.toString());
        holder.tv_Author.setText(modelQuotes.get(position).author.toString());
    }

    @Override
    public int getItemCount() {
        return modelQuotes.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_Quote;
        TextView tv_Author;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Author = itemView.findViewById(R.id.tv_Author);
            tv_Quote = itemView.findViewById(R.id.tv_Quote);
        }
    }
}
