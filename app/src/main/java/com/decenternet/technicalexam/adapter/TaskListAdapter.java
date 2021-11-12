package com.decenternet.technicalexam.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.decenternet.technicalexam.OnItemClickInterface;
import com.decenternet.technicalexam.R;
import com.decenternet.technicalexam.model.Task;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private List<Task> dataList = new ArrayList<>();
    OnItemClickInterface mOnItemClickInterface;

    public TaskListAdapter(List<Task> dataList, OnItemClickInterface mOnItemClickInterface ) {
        this.dataList = dataList;
        this.mOnItemClickInterface = mOnItemClickInterface;
    }




    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.txtDescription.setText(dataList.get(position).getDescription());
        holder.txtId.setText(dataList.get(position).getId()+"");


        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickInterface.onUpdate(dataList.get(position).getId(),position);
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickInterface.onDelete(dataList.get(position).getId(),position);
            }
        });

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                recyclerItemClickListener.onItemClick(dataList.get(position));
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView txtDescription, txtId;
        Button deleteBtn,editBtn;

        TaskViewHolder(View itemView) {
            super(itemView);
            txtDescription =  itemView.findViewById(R.id.txt_description);
            txtId =  itemView.findViewById(R.id.txt_id);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
            editBtn   = itemView.findViewById(R.id.edit_btn);
        }
    }
}
