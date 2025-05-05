package com.example.studentsmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val context: Context,
    private val students: List<Student>,
    private val onAction: (Student, Int, Int) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvStudentId: TextView = itemView.findViewById(R.id.tvStudentId)
        val btnMenu: View = itemView.findViewById(R.id.btnMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.tvName.text = student.name
        holder.tvStudentId.text = student.studentId

        holder.btnMenu.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.btnMenu)
            popupMenu.inflate(R.menu.student_menu)
            popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
                when (menuItem.itemId) {
                    R.id.menu_update -> onAction(student, R.id.menu_update, position)
                    R.id.menu_delete -> onAction(student, R.id.menu_delete, position)
                    R.id.menu_call -> onAction(student, R.id.menu_call, position)
                    R.id.menu_email -> onAction(student, R.id.menu_email, position)
                }
                true
            }
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int = students.size
}