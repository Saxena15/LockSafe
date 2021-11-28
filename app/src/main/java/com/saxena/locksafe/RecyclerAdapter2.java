package com.saxena.locksafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.ViewHolder> {

    ArrayList<String> heading = new ArrayList<>();
    ArrayList<String> subhead = new ArrayList<>();
    Context context;

    public RecyclerAdapter2(Context context , ArrayList<String> heading, ArrayList<String> sub) {
        this.heading = heading;
        this.subhead = sub;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_item,parent , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter2.ViewHolder holder, int position) {

        holder.text_head.setText(heading.get(position).trim().toUpperCase());
        holder.text_subhead.setText((subhead.get(position).trim()));
    }

    @Override
    public int getItemCount() {
        return subhead.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView text_head , text_subhead;
        ImageView socialdp , copy_ic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_head =   itemView.findViewById(R.id.text_head);
            text_subhead = itemView.findViewById(R.id.text_subhead);
            socialdp = itemView.findViewById(R.id.socialdp);
            copy_ic = itemView.findViewById(R.id.copy_ic);

        }
    }
}
