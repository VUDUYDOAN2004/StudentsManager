package com.example.studentsmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val students: MutableList<Student>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtId: TextView = view.findViewById(R.id.txtId)
        val txtName: TextView = view.findViewById(R.id.txtName)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.txtId.text = student.id
        holder.txtName.text = student.name
        holder.btnDelete.setOnClickListener {
            onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int = students.size
}
