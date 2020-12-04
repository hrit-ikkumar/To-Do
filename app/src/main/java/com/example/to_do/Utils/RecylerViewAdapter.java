package com.example.to_do.Utils;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do.Model.ToDoTask;
import com.example.to_do.R;

import java.util.ArrayList;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.RecyclerViewHolder> {

    public ArrayList<ToDoTask> toDoTasks;
    public onPlayerListener mOnPlayerListener;

    public RecylerViewAdapter(ArrayList<ToDoTask> toDoTasks, onPlayerListener onPlayerListener) {
        this.toDoTasks = toDoTasks;
        this.mOnPlayerListener = onPlayerListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout,null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, mOnPlayerListener);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        holder.mName.setText(toDoTasks.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return toDoTasks.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mName;

        public Button mUpdateBtn;
        public Button mDeleteBtn;
        public onPlayerListener mListener;


        public RecyclerViewHolder(@NonNull View itemView, onPlayerListener onPlayerListener) {
            super(itemView);

            this.mListener = onPlayerListener;

            mName = (TextView)itemView.findViewById(R.id.tv_name);

            mUpdateBtn = (Button)itemView.findViewById(R.id.btn_update);
            mDeleteBtn = (Button)itemView.findViewById(R.id.btn_delete);

            mUpdateBtn.setOnClickListener(this);
            mDeleteBtn.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId())
            {
                case R.id.btn_update:
                    mListener.onPlayerUpdateClick(getAdapterPosition());
                    break;
                case R.id.btn_delete:
                    mListener.onPlayerDeleteClick(getAdapterPosition());
                    break;
            }

        }
    }

    public interface onPlayerListener{
        void onPlayerUpdateClick(int position);
        void onPlayerDeleteClick(int position);
    }



}
