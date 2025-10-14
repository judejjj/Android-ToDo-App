package com.example.todo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageButton;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context context;
    private ArrayList<Task> taskList;
    private OnTaskActionListener listener;

    public interface OnTaskActionListener {
        void onDelete(Task task);
        void onMarkDone(Task task);
    }

    public TaskAdapter(Context context, ArrayList<Task> taskList, OnTaskActionListener listener) {
        this.context = context;
        this.taskList = taskList;
        this.listener = listener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.title.setText(task.geTitle());
        holder.desc.setText(task.getDescription());
        holder.priority.setText(task.getPriority());
        holder.status.setText(task.getStatus());
        holder.category.setText(task.getCategory());
        holder.deadline.setText(task.getDeadline());

        // Done/Undo button
        holder.doneBtn.setImageResource(task.getStatus().equals("Completed") ?
                android.R.drawable.ic_menu_revert : android.R.drawable.ic_menu_save);

        holder.doneBtn.setOnClickListener(v -> {
            task.setStatus(task.getStatus().equals("Completed") ? "Pending" : "Completed");
            listener.onMarkDone(task);
        });

        holder.deleteBtn.setOnClickListener(v -> listener.onDelete(task));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc, priority, status, category, deadline;
        ImageButton deleteBtn, doneBtn;

        TaskViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.taskTitle);
            desc = itemView.findViewById(R.id.taskDesc);
            priority = itemView.findViewById(R.id.taskPriority);
            status = itemView.findViewById(R.id.taskStatus);
            category = itemView.findViewById(R.id.taskCategory);
            deadline = itemView.findViewById(R.id.taskDeadline);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            doneBtn = itemView.findViewById(R.id.doneBtn);
        }
    }
}
