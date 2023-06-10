package com.kalidratorma.yssapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kalidratorma.yssapp.R
import com.kalidratorma.yssapp.adapter.TaskAdapter.TaskViewHolder
import com.kalidratorma.yssapp.model.Task

class TaskAdapter(private val taskList: List<Task>) : RecyclerView.Adapter<TaskViewHolder>() {
    var onItemClick: ((Task) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.taskItemTextView.text = taskList[position].name
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var taskItemTextView: TextView

        init {
            taskItemTextView = itemView.findViewById(R.id.taskItemTextView)

            itemView.setOnClickListener {
                onItemClick?.invoke(taskList[adapterPosition])
            }
        }
    }
}